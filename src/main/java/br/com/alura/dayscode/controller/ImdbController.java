package br.com.alura.dayscode.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.dayscode.HTMLGenerator;
import br.com.alura.dayscode.model.Movie;
import br.com.alura.dayscode.service.ImdbApiClient;

@RestController
public class ImdbController {

	@Autowired
	private ImdbApiClient imdbApiClient;
	
//	record Movie(String title, String image, String year, String imDbRating) {}
	public record ListOfMovies(List<Movie> items) {}

	@GetMapping("/top250")
	public ListOfMovies getTop250Filmes() throws FileNotFoundException {
		ListOfMovies movies = imdbApiClient.getBody();
		return movies;
	}

	@GetMapping("/top250html")
	public String getTop250FilmesHtml() throws IOException {
		String endereco = "src/main/resources/top250.html";
		
		PrintWriter printWriter = new PrintWriter(endereco);
		new HTMLGenerator(printWriter).generate(getTop250Filmes());
		printWriter.close();
		
		String readString = Files.readString(Path.of(endereco));
		return readString;
		
	}

}
