package br.com.alura.dayscode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application {

	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Configuration
	@PropertySource("classpath:application.properties")
	public class PropertiesWith7DaysCodeConfig {
		
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
