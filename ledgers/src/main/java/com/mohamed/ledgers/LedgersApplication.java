package com.mohamed.ledgers;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class LedgersApplication {

	public static void main(String[] args) {
		SpringApplication.run(LedgersApplication.class, args);
	}

	@Bean
	public ObjectMapper getObjectMapper() {
		return new ObjectMapper();
	}

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}
}
