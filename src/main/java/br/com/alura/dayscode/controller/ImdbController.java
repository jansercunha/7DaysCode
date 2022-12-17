package br.com.alura.dayscode.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.dayscode.HTMLGenerator;
import br.com.alura.dayscode.model.Movie;
import br.com.alura.dayscode.service.ImdbApiClient;

@RestController
@RequestMapping("/top250")
public class ImdbController {

	@Autowired
	private ImdbApiClient imdbApiClient;
	
	private static final String POST_SUCESS = "Filme adicionado a lista de favoritos!";
	private static final String POST_FAIL = "Erro ao adicionar Filme a lista de favoritos!";
	
	private ListOfMovies movies;
	private ListOfMovies favoritos = new ListOfMovies(new ArrayList<>());
	
//	record Movie(String title, String image, String year, String imDbRating) {}
	public record ListOfMovies(List<Movie> items) {}

	@GetMapping()
	public ListOfMovies getTop250Filmes(@RequestParam(required = false) String title) throws FileNotFoundException {
		this.movies = new ListOfMovies(new ArrayList<>());

		ListOfMovies listOfMovies = imdbApiClient.getBody();
		
		if (title != null) {
			this.movies.items().addAll(listOfMovies.items().stream().filter(item -> item.getTitle().toUpperCase().contains(title.toUpperCase())).collect(Collectors.toList()));
		} else {
			this.movies.items().addAll(listOfMovies.items());
		}
		
		return this.movies;
	}

	@GetMapping("/html")
	public String getTop250FilmesHtml(@RequestParam(required = false) String title) throws IOException {
		String endereco = "src/main/resources/top250.html";
		
		PrintWriter printWriter = new PrintWriter(endereco);
		new HTMLGenerator(printWriter).generate(getTop250Filmes(title));
		printWriter.close();
		
		String readString = Files.readString(Path.of(endereco));
		return readString;
		
	}
	
	@PostMapping("/favorito/{id}")
	public ResponseEntity<?> postAdicionarFavoritos(@PathVariable String id) throws FileNotFoundException {
		if (this.movies == null || this.movies.items().isEmpty()) getTop250Filmes(null);
		
		Optional<Movie> movie = this.movies.items().stream().filter(item -> item.getId().equals(id)).findFirst();
		
		if (movie.isPresent()) {
			this.favoritos.items().add(movie.get());
			return new ResponseEntity<>(POST_SUCESS, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(POST_FAIL, HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/favorito")
	public ListOfMovies getFavoritos() {
		return this.favoritos;
	}

}
