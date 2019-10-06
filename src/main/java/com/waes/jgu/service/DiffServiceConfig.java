package com.waes.jgu.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Spring Configuration Class that handles bean creation to resolve {@code DiffService} dependency injection based on what profile is running
 * 
 * @author Jeison Gutierrez jdgutierrezj
 * */
@Configuration
public class DiffServiceConfig {
	
	/**
	 * Instantiation of dummy service with Java Collection 
	 * 
	 * @return DiffDummyServiceImpl instance
	 * */
	@Bean
	@Profile("dev")
	public DiffService dummyService() {
		return new DiffDummyServiceImpl();
	}
	
	/**
	 * Instantiation of service with in memory H2 DataBase 
	 * 
	 * @return DiffH2ServiceImpl instance
	 * */
	@Bean
	@Profile("prod")
	public DiffService h2Service() {
		return new DiffH2ServiceImpl();
	}
}
