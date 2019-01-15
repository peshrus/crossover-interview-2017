package com.crossover.trial.journals;

import com.crossover.trial.journals.model.User;
import com.crossover.trial.journals.service.CurrentUser;
import com.crossover.trial.journals.service.UserService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.junit.Assert.fail;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * The system implements the following specifications:
 * <ol>
 *   <li>For publishers:</li>
 *   <ol>
 *     <li>A web portal to upload and manage a list of medical journals</li>
 *     <li>To upload journals in PDF format</li>
 *   </ol>
 *
 *   <li>For public users:</li>
 *   <ol>
 *     <li>A web portal to find (covered in CategoryRestServiceTest) and subscribe to journals of their interest</li>
 *     <li>Once subscribed the users are able to browse and read journals online</li>
 *   </ol>
 * </ol>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public abstract class AllMajorUseCasesFixture {
	private MockMvc mockMvc;
	private MediaType contentType = APPLICATION_JSON_UTF8;
	@Autowired
	private WebApplicationContext webApplicationContext;
	@Autowired
	private UserService userService;

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	public MockMvc getMockMvc() {
		return mockMvc;
	}

	public MediaType getContentType() {
		return contentType;
	}

	public Authentication getPrincipal(String userName) {
		return new TestingAuthenticationToken(new CurrentUser(getUser(userName)), null);
	}

	public User getUser(String name) {
		final Optional<User> user = userService.getUserByLoginName(name);
		if (!user.isPresent()) {
			fail("User doesn't exist: " + name);
		}
		return user.get();
	}
}
