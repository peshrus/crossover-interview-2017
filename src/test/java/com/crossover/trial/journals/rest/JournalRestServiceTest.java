package com.crossover.trial.journals.rest;

import com.crossover.trial.journals.AllMajorUseCasesFixture;
import com.crossover.trial.journals.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.test.annotation.DirtiesContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class JournalRestServiceTest extends AllMajorUseCasesFixture {
	@Autowired
	private UserRepository userRepository;

	// "Once subscribed the users are able to browse"
	@Test
	public void testBrowseSubscribedJournals_OK() throws Exception {
		getMockMvc().perform(get("/rest/journals").principal(getPrincipal("user1")))
		       .andExpect(status().isOk())
		       .andExpect(content().contentType(getContentType()))
		       .andExpect(jsonPath("$[0].id", is(1)))
		       .andExpect(jsonPath("$[0].name", is("Medicine")));
	}

	// "and subscribe to journals of their interest"
	@Test
	public void testSubscribe_OK() throws Exception {
		final Authentication principal = getPrincipal("user2");

		getMockMvc().perform(post("/rest/journals/subscribe/4").principal(principal))
		            .andExpect(status().isOk());
		getMockMvc().perform(get("/rest/journals").principal(principal))
		            .andExpect(status().isOk())
		            .andExpect(content().contentType(getContentType()))
		            .andExpect(jsonPath("$[0].id", is(2)))
		            .andExpect(jsonPath("$[0].name", is("Test Journal")));
	}

	// "and subscribe to journals of their interest"
	@Test
	public void testGetUserSubscriptions_OK() throws Exception {
		getMockMvc().perform(get("/rest/journals/subscriptions").principal(getPrincipal("user1")))
		            .andExpect(status().isOk())
		            .andExpect(content().contentType(getContentType()))
		            .andExpect(jsonPath("$[0].id", is(1)))
		            .andExpect(jsonPath("$[0].name", is("immunology")))
		            .andExpect(jsonPath("$[0].active", is(false)))

		            .andExpect(jsonPath("$[1].id", is(2)))
		            .andExpect(jsonPath("$[1].name", is("pathology")))
		            .andExpect(jsonPath("$[1].active", is(false)))

		            .andExpect(jsonPath("$[2].id", is(3)))
		            .andExpect(jsonPath("$[2].name", is("endocrinology")))
		            .andExpect(jsonPath("$[2].active", is(true)))

		            .andExpect(jsonPath("$[3].id", is(4)))
		            .andExpect(jsonPath("$[3].name", is("microbiology")))
		            .andExpect(jsonPath("$[3].active", is(false)))

		            .andExpect(jsonPath("$[4].id", is(5)))
		            .andExpect(jsonPath("$[4].name", is("neurology")))
		            .andExpect(jsonPath("$[4].active", is(false)));
	}

	// "and manage a list of medical journals"
	@Test
	public void testPublishedList_OK() throws Exception {
		getMockMvc().perform(get("/rest/journals/published").principal(getPrincipal("publisher1")))
		            .andExpect(status().isOk())
		            .andExpect(content().contentType(getContentType()))
		            .andExpect(jsonPath("$[0].id", is(1)))
		            .andExpect(jsonPath("$[0].name", is("Medicine")))
		            .andExpect(jsonPath("$[1].id", is(2)))
		            .andExpect(jsonPath("$[1].name", is("Test Journal")));
	}

	// "and manage a list of medical journals"
	@Test
	public void testUnPublish_OK() throws Exception {
		getMockMvc().perform(delete("/rest/journals/unPublish/3").principal(getPrincipal("publisher2")))
		            .andExpect(status().isOk());
	}
}
