package com.crossover.trial.journals.rest;

import com.crossover.trial.journals.model.Category;
import com.crossover.trial.journals.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public/rest/category")
public class CategoryRestService {

    @Autowired
    private CategoryRepository repository;


    @RequestMapping(value = "")
    public List<Category> getCategories() {
        return repository.findAll();
    }

}
