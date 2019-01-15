package com.crossover.trial.journals.controller;

import com.crossover.trial.journals.AllMajorUseCasesFixture;
import com.crossover.trial.journals.repository.UserRepository;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class PublisherControllerTest extends AllMajorUseCasesFixture {
	@Autowired
	private UserRepository userRepository;

	// "To upload journals in PDF format"
	@Test
	public void testUploadPdfJournal_OK() throws Exception {
		getMockMvc().perform(fileUpload("/publisher/publish")
				                     .file("file", createPdf("testUploadPdfJournal_OK"))
				                     .param("name", "testUploadPdfJournal_OK")
				                     .param("category", "1")
				                     .principal(getPrincipal("publisher3")))
		            .andExpect(status().isFound())
		            .andExpect(redirectedUrl("/publisher/browse"));
	}

	public byte[] createPdf(String pdfContent) throws IOException {
		try (final ByteArrayOutputStream ous = new ByteArrayOutputStream()) {
			// Initialize PDF writer
			final PdfWriter writer = new PdfWriter(ous);
			// Initialize PDF document
			final PdfDocument pdf = new PdfDocument(writer);
			// Initialize document
			try (final Document document = new Document(pdf)) {
				// Add paragraph to the document
				document.add(new Paragraph(pdfContent));
			}

			return ous.toByteArray();
		}
	}
}