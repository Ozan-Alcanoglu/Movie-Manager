package movieDbApp.webApiController;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import movieDbApp.business.abstracts.MovieGenreService;
import movieDbApp.business.requests.CreateMovieGenreRequests;

@RestController
@AllArgsConstructor
@RequestMapping("/moviedb/moviegenre")
public class MovieGenreController {
	
	private MovieGenreService movieGenreService;
	
	@PostMapping("/add")
	@ResponseStatus(code=HttpStatus.CREATED)
	public void add(@RequestBody @Valid CreateMovieGenreRequests movieGenreRequests) {
		
		this.movieGenreService.addMovieGenre(movieGenreRequests);
		
	}

}
