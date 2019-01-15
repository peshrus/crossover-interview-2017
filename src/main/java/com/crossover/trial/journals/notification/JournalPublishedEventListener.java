package com.crossover.trial.journals.notification;

import com.crossover.trial.journals.model.Journal;
import com.crossover.trial.journals.model.Subscription;
import com.crossover.trial.journals.repository.SubscriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Collections.singleton;

@Primary
@Component
public class JournalPublishedEventListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(JournalPublishedEventListener.class);

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@Autowired
	private EmailSender emailSender;

	@EventListener
	@Async
	public void handle(JournalPublishedEvent journalPublishedEvent) {
		final Journal journal = journalPublishedEvent.getJournal();
		final List<Subscription> subscriptions = subscriptionRepository.findByCategory(journal.getCategory());
		final Set<String> recipientEmails =
				subscriptions.stream().map(subscription -> subscription.getUser().getEmail()).collect(Collectors.toSet());

		LOGGER.info("Call email sender: recipientEmails={}, journal={}", recipientEmails, journal);

		emailSender.send(recipientEmails, singleton(journal));
	}
}
