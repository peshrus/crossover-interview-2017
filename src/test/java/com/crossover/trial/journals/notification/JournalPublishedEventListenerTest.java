package com.crossover.trial.journals.notification;

import com.crossover.trial.journals.Application;
import com.crossover.trial.journals.model.Journal;
import com.crossover.trial.journals.repository.JournalRepository;
import com.crossover.trial.journals.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static java.util.Collections.singleton;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class JournalPublishedEventListenerTest extends NotificationTestFixture {
	@InjectMocks
	@Autowired
	@Qualifier("TestJournalPublishedEventListener")
	private JournalPublishedEventListener journalPublishedEventListener;

	@Autowired
	private JournalRepository journalRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	public void testSend() throws InterruptedException {
		final Journal journal = journalRepository.findOne(1L);
		journalPublishedEventListener.handle(new JournalPublishedEvent(journal));
		((TestJournalPublishedEventListener) journalPublishedEventListener).countDownLatch.await();
		verify(getEmailSender()).send(singleton(userRepository.findOne(3L).getEmail()), singleton(journal));
	}
}
