package com.waes.jgu.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.waes.jgu.ScalableWebApplication;

/**
 * Integration test
 * 
 * @see <a href="https://www.baeldung.com/spring-boot-testing">Spring boot testing</a>
 * 
 * @author Jeison Gutierrez jdgutierrezj
 * */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
classes = ScalableWebApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-dev.properties")
public class DiffControllerDummyIntegrationTest {
	
    @Autowired
    private MockMvc mvc;
    
	private final String baseURL = "/v1/diff";
	
	/**
	 * Test method to validate an invalid base64 String
	 * */
	@Test
	public void givenValidBase64_right_thenStatus201() throws Exception {

		String id = String.valueOf(ThreadLocalRandom.current().nextInt(1, 1000));
		mvc.perform(post(String.format("%s/%s/right", baseURL, id))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"base64Data\":\"asdf\"}"))
				.andDo(print())
				.andExpect(status().isCreated());

	}

	@Test
	public void givenValidBase64_left_thenStatus201() throws Exception {

		String id = String.valueOf(ThreadLocalRandom.current().nextInt(1, 1000));
		mvc.perform(post(String.format("%s/%s/left", baseURL, id))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"base64Data\":\"asdf\"}"))
				.andDo(print())		
				.andExpect(status().isCreated());

	}

}
