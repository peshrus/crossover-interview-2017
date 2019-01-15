package com.crossover.trial.journals.notification;

import com.crossover.trial.journals.Application;
import com.crossover.trial.journals.model.User;
import com.crossover.trial.journals.repository.JournalRepository;
import com.crossover.trial.journals.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class DailyDigesterTest extends NotificationTestFixture {
	@InjectMocks
	@Autowired
	private DailyDigester dailyDigester;

	@Autowired
	private JournalRepository journalRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	public void testSendDailyDigest() {
		dailyDigester.sendDailyDigest();
		final Set<String> recipientEmails =
				userRepository.findAll().stream().map(User::getEmail).collect(Collectors.toSet());
		verify(getEmailSender()).send(recipientEmails,
		                              StreamSupport.stream(journalRepository.findAll().spliterator(), false)
		                                           .collect(Collectors.toSet()));
	}
}
