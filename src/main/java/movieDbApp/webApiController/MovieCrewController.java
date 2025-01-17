package movieDbApp.webApiController;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import movieDbApp.business.abstracts.MovieCrewService;
import movieDbApp.business.requests.CreateMovieCrewRequests;

@RestController
@AllArgsConstructor
@RequestMapping("/moviedb/moviecrew")
public class MovieCrewController {

	private MovieCrewService movieCrewService;
	
	
	@PostMapping("/add")
	@ResponseStatus(code=HttpStatus.CREATED)
	public void add(@RequestBody @Valid CreateMovieCrewRequests movieCrewRequests) {
		
		this.movieCrewService.addMovieCrew(movieCrewRequests);
		
	}
	
}
