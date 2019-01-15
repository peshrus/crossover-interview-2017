package com.crossover.trial.journals.notification;

import com.crossover.trial.journals.model.Journal;
import org.springframework.context.ApplicationEvent;

public class JournalPublishedEvent extends ApplicationEvent {
	private final transient Journal journal;

	public JournalPublishedEvent(Journal journal) {
		super(journal);
		this.journal = journal;
	}

	public Journal getJournal() {
		return journal;
	}
}
