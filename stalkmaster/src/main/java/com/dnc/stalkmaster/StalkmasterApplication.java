package com.dnc.stalkmaster;

import com.dnc.stalkmaster.infra.RestTemplateBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class StalkmasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(StalkmasterApplication.class, args);
	}

	@Bean
	public RestTemplate getRestTemplate(){
		return RestTemplateBuilder.buildRestTemplate(100, 100,  null);
	}
}
