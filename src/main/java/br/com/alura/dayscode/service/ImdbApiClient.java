package br.com.alura.dayscode.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.alura.dayscode.controller.ImdbController.ListOfMovies;

@Service
public class ImdbApiClient {
	
	@Value("${imdb.url}")
	private String url;
	
	@Value("${imdb.token}")
	private String token;
	
	@Value("${imdb.top250}")
	private String top250;
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	public ListOfMovies getBody() {
		return restTemplate.getForEntity(url.concat("/").concat(top250).concat("/").concat(token), ListOfMovies.class).getBody();
	}

}
