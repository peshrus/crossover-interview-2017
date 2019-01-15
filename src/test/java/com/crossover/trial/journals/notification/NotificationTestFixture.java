package com.crossover.trial.journals.notification;

import com.crossover.trial.journals.Application;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public abstract class NotificationTestFixture {
	@Mock
	private EmailSender emailSender;

	@Before
	public void setUp() throws Exception {
		// Initialize mocks created above
		MockitoAnnotations.initMocks(this);
	}

	public EmailSender getEmailSender() {
		return emailSender;
	}
}
