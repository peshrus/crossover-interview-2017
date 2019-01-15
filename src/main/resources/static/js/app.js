var app = angular.module("JournalApp", []);

app.controller("CategoryController", function ($scope, $http) {

    $scope.isAnySubscription = function() {
        var result = false;
        for(var i=0; i< $scope.subscriptions.length; i++) {
            if($scope.subscriptions[i].active) {
                return true;
            }
        }
        return result;
    }

    $scope.subscribe = function (categoryId) {
        $http.post('/rest/journals/subscribe/' + categoryId).success(function (data, status, headers, config) {
            for (var i = 0; i < $scope.subscriptions.length; i++) {
                var s = $scope.subscriptions[i];
                if (s.id == categoryId) {
                    $scope.subscriptions[i].active = true;
                    break;
                }
            }
        }).error(function (data, status, headers, config) {
            if (data.message == 'Time is out') {
                console.log("Timeout")
            }
        });
    }

    $scope.getCategories = function () {
        $http.get('/public/rest/category').success(function (data, status, headers, config) {
            $scope.categories = data;
        }).error(function (data, status, headers, config) {
            if (data.message == 'Time is out') {
                console.log("Timeout")
            }
        });
    }

    $scope.getSubscriptions = function () {
        $http.get('/rest/journals/subscriptions').success(function (data, status, headers, config) {
            $scope.subscriptions = data;
        }).error(function (data, status, headers, config) {
            if (data.message == 'Time is out') {
                console.log("Timeout")
            }
        });
    }
});

app.controller("UserSubscriptionController", function ($scope, $http) {
    $scope.getCategories = function () {
        $http.get('/public/rest/category').success(function (data, status, headers, config) {
            $scope.categories = data;
        }).error(function (data, status, headers, config) {
            if (data.message == 'Time is out') {
                console.log("Timeout")
            }
        });
    }
});

app.controller("JournalController", function ($scope, $http, $filter) {
    $scope.getJournals = function () {
        $http.get('/rest/journals').success(function (data, status, headers, config) {
            var journals = data;
            for (var i = 0; i < journals.length; i++) {
                var journal = journals[i];
                journal.readLink = "/view/" + journal.id;
                journal.publishDate = $filter('date')(journal.publishDate);
            }
            $scope.journalList = journals;
        }).error(function (data, status, headers, config) {
            if (data.message == 'Time is out') {
                $scope.finishByTimeout();
            }
        });
    }
});

app.controller("BrowseController", function ($scope, $http, $filter, $window) {
    $scope.getJournals = function () {
        $http.get('/rest/journals/published').success(function (data, status, headers, config) {
            var journals = data;
            for (var i = 0; i < journals.length; i++) {
                var journal = journals[i];
                journal.publishDate = $filter('date')(journal.publishDate);
                journal.readLink = "/view/" + journal.id;
            }
            $scope.journalList = journals;
        }).error(function (data, status, headers, config) {
            console.error(status, data, headers);
        });
    }

    $scope.delete = function (id) {
        $http.delete('/rest/journals/unPublish/' + id).success(function (data, status, headers, config) {
            for (var i = 0; i < $scope.journalList.length; i++) {
                var j = $scope.journalList[i];
                if (j.id == id) {
                    $scope.journalList.splice(i, 1);
                    break;
                }
            }
        }).error(function (data, status, headers, config) {
            console.error(status, data, headers);
        });
    }

    $scope.view = function (id) {
        for (var i = 0; i < $scope.journalList.length; i++) {
            var j = $scope.journalList[i];
            if (j.id == id) {
                $window.location.href = $scope.journalList[i].readLink;
            }
        }
    }
});
