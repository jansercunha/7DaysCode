package br.com.alura.dayscode.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.dayscode.HTMLGenerator;
import br.com.alura.dayscode.model.Movie;
import br.com.alura.dayscode.service.ImdbApiClient;

@RestController
public class ImdbController {

	@Autowired
	private ImdbApiClient imdbApiClient;
	private ListOfMovies movies;
	
//	record Movie(String title, String image, String year, String imDbRating) {}
	public record ListOfMovies(List<Movie> items) {}

	@GetMapping("/top250")
	public ListOfMovies getTop250Filmes(@RequestParam(required = false) String title) throws FileNotFoundException {
		this.movies = new ListOfMovies(new ArrayList<>());

		ListOfMovies listOfMovies = imdbApiClient.getBody();
		
		if (title != null) {
			this.movies.items().addAll(listOfMovies.items().stream().filter(item -> item.getTitle().contains(title)).collect(Collectors.toList()));
		} else {
			this.movies.items().addAll(listOfMovies.items());
		}
		
		return this.movies;
	}

	@GetMapping("/top250html")
	public String getTop250FilmesHtml(@RequestParam(required = false) String title) throws IOException {
		String endereco = "src/main/resources/top250.html";
		
		PrintWriter printWriter = new PrintWriter(endereco);
		new HTMLGenerator(printWriter).generate(getTop250Filmes(title));
		printWriter.close();
		
		String readString = Files.readString(Path.of(endereco));
		return readString;
		
	}

}
