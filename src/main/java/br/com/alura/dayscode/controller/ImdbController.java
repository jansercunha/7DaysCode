package br.com.alura.dayscode.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/imdb")
public class ImdbController {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${imdb.url}")
	private String url;
	
	@Value("${imdb.token}")
	private String token;
	
	@Value("${imdb.top250}")
	private String top250;

	@GetMapping
	public String listarTop250() {

		try {
			URI urlCompleta = new URI(url.concat("/").concat(top250).concat("/").concat(token));
			ResponseEntity<String> responseEntity = restTemplate.getForEntity(urlCompleta, String.class);
			
			System.out.println(responseEntity.getBody());
			return responseEntity.getBody();
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return null;
	}

}
