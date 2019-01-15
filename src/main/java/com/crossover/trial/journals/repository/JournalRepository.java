package com.crossover.trial.journals.repository;

import com.crossover.trial.journals.model.Journal;
import com.crossover.trial.journals.model.Publisher;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface JournalRepository extends CrudRepository<Journal, Long> {

    Collection<Journal> findByPublisher(Publisher publisher);

    List<Journal> findByCategoryIdIn(List<Long> ids);

    List<Journal> findByPublishDateBetween(Date start, Date end);
}
