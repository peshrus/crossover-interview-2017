package com.crossover.trial.journals.notification;

import com.crossover.trial.journals.model.Journal;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static org.springframework.ui.velocity.VelocityEngineUtils.mergeTemplateIntoString;

@Service
public class EmailSenderImpl implements EmailSender {
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailSenderImpl.class);

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private VelocityEngine velocityEngine;

	@Value("${mail.subject}")
	private String subject;

	@Value("${mail.from}")
	private String from;

	@Override
	public void send(Set<String> recipientEmails, Set<Journal> journals) {
		if (!recipientEmails.isEmpty() && !journals.isEmpty()) {
			final MimeMessagePreparator preparator = mimeMessage -> {
				final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setSubject(subject);
				message.setBcc(recipientEmails.toArray(new String[recipientEmails.size()]));
				message.setFrom(from);

				final Map<String, Object> model = new HashMap<>();
				model.put("date", new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.US));
				model.put("journals", journals);
				final String text =
						mergeTemplateIntoString(velocityEngine,
						                        "com/crossover/trial/journals/notification/notification-template.vm",
						                        "UTF-8",
						                        model);
				message.setText(text, true);
			};

			emailSender.send(preparator);
		} else {
			LOGGER.warn("Email has not been sent: recipientEmails={}, journals={}", recipientEmails, journals);
		}
	}
}
