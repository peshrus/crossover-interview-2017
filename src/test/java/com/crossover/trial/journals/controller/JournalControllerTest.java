package com.crossover.trial.journals.controller;

import com.crossover.trial.journals.AllMajorUseCasesFixture;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.http.MediaType;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class JournalControllerTest extends AllMajorUseCasesFixture {
	// "and read journals online"
	@Test
	public void testReadJournalsOnline_OK() throws Exception {
		final String expectedFileName = PublisherController.getFileName(1, "8305d848-88d2-4cbd-a33b-5c3dcc548056");
		try (InputStream expectedFileStream = new BufferedInputStream(new FileInputStream(expectedFileName))) {
			final byte[] expectedContent = IOUtils.toByteArray(expectedFileStream);

			getMockMvc().perform(get("/view/1").principal(getPrincipal("user1")))
			            .andExpect(status().isOk())
			            .andExpect(content().contentType(new MediaType("application", "pdf")))
			            .andExpect(content().bytes(expectedContent));
		}
	}
}
