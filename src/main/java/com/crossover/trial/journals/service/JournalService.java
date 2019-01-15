package com.crossover.trial.journals.service;

import com.crossover.trial.journals.model.Journal;
import com.crossover.trial.journals.model.Publisher;
import com.crossover.trial.journals.model.User;

import java.util.List;

public interface JournalService {

	List<Journal> listAll(User user);

	List<Journal> publisherList(Publisher publisher);

	Journal publish(Publisher publisher, Journal journal, Long categoryId);

	void unPublish(Publisher publisher, Long journalId);
}
