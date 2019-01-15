package com.crossover.trial.journals.notification;

import com.crossover.trial.journals.model.Journal;
import com.crossover.trial.journals.model.User;
import com.crossover.trial.journals.repository.JournalRepository;
import com.crossover.trial.journals.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DailyDigester {
	private static final Logger LOGGER = LoggerFactory.getLogger(DailyDigester.class);

	@Autowired
	private JournalRepository journalRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmailSender emailSender;

	@Scheduled(cron = "0 */3 * * * *")
	public void sendDailyDigest() {
		final Calendar calendar = Calendar.getInstance();
		final Date end = calendar.getTime();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		final Date start = calendar.getTime();

		LOGGER.info("Form daily digest: start={}, end={}", start, end);

		final List<Journal> prevDayJournals = journalRepository.findByPublishDateBetween(start, end);
		final Set<String> recipientEmails =
				userRepository.findAll().stream().map(User::getEmail).collect(Collectors.toSet());

		LOGGER.info("Call email sender: recipientEmails={}; prevDayJournals={}", recipientEmails, prevDayJournals);

		emailSender.send(recipientEmails, new HashSet<>(prevDayJournals));
	}
}
