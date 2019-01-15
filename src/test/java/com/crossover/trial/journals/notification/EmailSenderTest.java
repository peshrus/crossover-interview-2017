package com.crossover.trial.journals.notification;

import com.crossover.trial.journals.Application;
import com.crossover.trial.journals.repository.JournalRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.mail.Message;
import javax.mail.internet.MimeMessage;

import static java.util.Collections.singleton;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class EmailSenderTest {
	@Mock
	private JavaMailSender javaMailSender;

	@InjectMocks
	@Autowired
	private EmailSender emailSender;

	@Autowired
	private JournalRepository journalRepository;

	@Before
	public void setUp() throws Exception {
		// Initialize mocks created above
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testSend() {
		doAnswer(invocationOnMock -> {
			final MimeMessagePreparator preparator = (MimeMessagePreparator) invocationOnMock.getArguments()[0];
			final MimeMessage mimeMessage = new JavaMailSenderImpl().createMimeMessage();
			preparator.prepare(mimeMessage);

			assertEquals("New publications", mimeMessage.getSubject());
			assertEquals("testSend@test.com", mimeMessage.getRecipients(Message.RecipientType.BCC)[0].toString());
			assertEquals("<html>\n" +
					             "  <body>\n" +
					             "    <h1>Hi there, new reading is available for you!</h1>\n" +
					             "\n" +
					             "    <ul>\n" +
					             "          <li>Medicine (Published by Test Publisher1  in category &quot;endocrinology&quot;)</li>\n" +
					             "        </ul>\n" +
					             "  </body>\n" +
					             "</html>", mimeMessage.getContent().toString().replaceAll("on .* in", "in"));

			return null;
		}).when(javaMailSender).send(any(MimeMessagePreparator.class));

		emailSender.send(singleton("testSend@test.com"), singleton(journalRepository.findOne(1L)));
	}
}
