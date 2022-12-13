package br.com.alura.dayscode.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import br.com.alura.dayscode.model.Movie;

@RestController
public class ImdbController {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${imdb.url}")
	private String url;
	
	@Value("${imdb.token}")
	private String token;
	
	@Value("${imdb.top250}")
	private String top250;
	
//	record Movie(String title, String image, String year, String imDbRating) {}
	private record ListOfMovies(List<Movie> items) {}

	@GetMapping("/top250")
	public List<Movie> getTop250Filmes() {

		try {
			URI urlCompleta = new URI(url.concat("/").concat(top250).concat("/").concat(token));
			ResponseEntity<ListOfMovies> responseEntity = restTemplate.getForEntity(urlCompleta, ListOfMovies.class);
			
			System.out.println(responseEntity.getBody());
			return responseEntity.getBody().items();
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return null;
	}

}
