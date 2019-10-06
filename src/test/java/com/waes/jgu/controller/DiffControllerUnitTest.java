package com.waes.jgu.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.waes.jgu.dto.DiffResponse;
import com.waes.jgu.enums.Side;
import com.waes.jgu.exception.InmutableDataException;
import com.waes.jgu.exception.InvalidDataException;
import com.waes.jgu.repository.DiffRepository;
import com.waes.jgu.service.DiffService;

/**
 * Unit test
 * 
 * @see <a href="https://www.baeldung.com/spring-boot-testing">Spring boot testing</a>
 * 
 * @author Jeison Gutierrez jdgutierrezj
 * */
@RunWith(SpringRunner.class)
@WebMvcTest(DiffController.class)
@TestPropertySource(locations = "classpath:application-dev.properties")
public class DiffControllerUnitTest {
 
    @Autowired
    private MockMvc mvc;
	
    @MockBean
    private DiffService service;
    
    @MockBean
    private DiffRepository repository;
	
    private String BASE_URL = "/v1/diff";
    
	private final String base64left_1 = "66QrQiv6oFByqbkVtxGVnHLyGj9ALSyy4i43gL9h";
	private final String base64right_1 = "66QrQiv6oFByqbkVtxGVnHLyGj9ALSyy4i43gL9h";
	
	private final String base64right_2 = "U1i6uxLR209g4fZyRG0rQ1RqKY0fR3x2PdYcdCAuu";
	
	private final String base64right_3 = "U1i6uxLR209g4fZyRG0rQ1RqKY0fR3x2PdYcdCAu";
	
	private final String base64invalid = "U1i6uxLR209g4fZyR G0rQ1RqKY0fR3x2PdYcdCAu";
    
    @Test
    public void when_sequence_is_invalid() throws Exception {
    	
		String id = String.valueOf(ThreadLocalRandom.current().nextInt(1, 1000));
		
		when(service.saveData(id, Side.LEFT, base64invalid))
		.thenThrow(new InvalidDataException("Invalid Base64 Data",null));
		
		mvc.perform(post(String.format("%s/%s/left", BASE_URL, id))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"base64Data\":\""+base64invalid+"\"}"))
				.andDo(print())
				.andExpect(status().isBadRequest());
		
    }	
	
    @Test
    public void when_left_exists() throws Exception {
    	
		String id = String.valueOf(ThreadLocalRandom.current().nextInt(1, 1000));
		
		when(service.saveData(id, Side.LEFT, base64left_1))
		.thenThrow(new InmutableDataException(String.format("The %s side of the comparison was already received", Side.LEFT)));
		
		mvc.perform(post(String.format("%s/%s/left", BASE_URL, id))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"base64Data\":\""+base64left_1+"\"}"))
				.andDo(print())
				.andExpect(status().isBadRequest());
		
    }
    
    @Test
    public void when_right_exists() throws Exception {
    	
		String id = String.valueOf(ThreadLocalRandom.current().nextInt(1, 1000));
		
		when(service.saveData(id, Side.RIGHT, base64left_1))
		.thenThrow(new InmutableDataException(String.format("The %s side of the comparison was already received", Side.RIGHT)));
		
		mvc.perform(post(String.format("%s/%s/right", BASE_URL, id))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"base64Data\":\""+base64right_1+"\"}"))
				.andDo(print())
				.andExpect(status().isBadRequest());
		
    }
    
    @Test
    public void when_sequence_are_equals_then_result_EQUAL() throws Exception {
    	
		String id = String.valueOf(ThreadLocalRandom.current().nextInt(1, 1000));
		
		DiffResponse response = new DiffResponse();
		response.setResult("EQUAL");
		
		when(service.getDiff(String.valueOf(id)))
		.thenReturn(response);
		
		mvc.perform(post(String.format("%s/%s/left", BASE_URL, id))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"base64Data\":\""+base64left_1+"\"}"))
				.andDo(print())
				.andExpect(status().isCreated());
		
		mvc.perform(post(String.format("%s/%s/right", BASE_URL, id))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"base64Data\":\""+base64right_1+"\"}"))
				.andDo(print())
				.andExpect(status().isCreated());
		
		mvc.perform(get(String.format("%s/%s", BASE_URL, id))
		      .contentType(MediaType.APPLICATION_JSON))
		      .andExpect(status().isOk())
		      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		      .andExpect(jsonPath("$.result", is("EQUAL")));
		
    }
    
    @Test
    public void when_sequence_are_length_equals_then_result_LENGTH_EQUAL() throws Exception {
    	
		String id = String.valueOf(ThreadLocalRandom.current().nextInt(1, 1000));
		
		DiffResponse response = new DiffResponse();
		response.setResult("LENGTH EQUAL");
		
		when(service.getDiff(String.valueOf(id)))
		.thenReturn(response);
		
		mvc.perform(post(String.format("%s/%s/left", BASE_URL, id))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"base64Data\":\""+base64left_1+"\"}"))
				.andDo(print())
				.andExpect(status().isCreated());
		
		mvc.perform(post(String.format("%s/%s/right", BASE_URL, id))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"base64Data\":\""+base64right_3+"\"}"))
				.andDo(print())
				.andExpect(status().isCreated());
		
		mvc.perform(get(String.format("%s/%s", BASE_URL, id))
		      .contentType(MediaType.APPLICATION_JSON))
		      .andExpect(status().isOk())
		      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		      .andExpect(jsonPath("$.result", is("LENGTH EQUAL")));
		
    }
    
    @Test
    public void when_sequence_are_not_equals_then_result_NOT_EQUAL() throws Exception {
    	
		String id = String.valueOf(ThreadLocalRandom.current().nextInt(1, 1000));
		
		DiffResponse response = new DiffResponse();
		response.setResult("NOT EQUAL");
		
		when(service.getDiff(String.valueOf(id)))
		.thenReturn(response);
		
		mvc.perform(post(String.format("%s/%s/left", BASE_URL, id))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"base64Data\":\""+base64left_1+"\"}"))
				.andDo(print())
				.andExpect(status().isCreated());
		
		mvc.perform(post(String.format("%s/%s/right", BASE_URL, id))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"base64Data\":\""+base64right_2+"\"}"))
				.andDo(print())
				.andExpect(status().isCreated());
		
		mvc.perform(get(String.format("%s/%s", BASE_URL, id))
		      .contentType(MediaType.APPLICATION_JSON))
		      .andExpect(status().isOk())
		      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		      .andExpect(jsonPath("$.result", is("NOT EQUAL")));
		
    }
    
}
