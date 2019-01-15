package com.crossover.trial.journals.notification;

import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component("TestJournalPublishedEventListener")
class TestJournalPublishedEventListener extends JournalPublishedEventListener {
	CountDownLatch countDownLatch = new CountDownLatch(1);

	@Override
	public void handle(JournalPublishedEvent journalPublishedEvent) {
		super.handle(journalPublishedEvent);
		countDownLatch.countDown();
	}
}
