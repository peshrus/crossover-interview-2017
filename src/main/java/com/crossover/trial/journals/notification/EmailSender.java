package com.crossover.trial.journals.notification;

import com.crossover.trial.journals.model.Journal;

import java.util.Set;

public interface EmailSender {
	void send(Set<String> recipientEmails, Set<Journal> journals);
}
