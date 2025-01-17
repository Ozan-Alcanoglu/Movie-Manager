package movieDbApp.webApiController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import movieDbApp.business.abstracts.MovieService;
import movieDbApp.business.requests.CreateMovieRequests;
import movieDbApp.business.requests.UpdateMovieRequests;
import movieDbApp.business.response.GetAllMovieResponse;
import movieDbApp.business.response.GetByIdMovieResponse;
import movieDbApp.business.response.GetByNameMovieResponse;
import movieDbApp.dataAccess.MovieCrewRepository;
import movieDbApp.dataAccess.MovieGenreRepository;

@RestController
@AllArgsConstructor
@RequestMapping("/moviedb/movies")
public class MovieController {

	private MovieService movieService;
	
	@GetMapping
	public List<GetAllMovieResponse> getAllMovie(){
		return this.movieService.getAllMovie();
	}
	
	@GetMapping("/getbyid/{id}")
	public GetByIdMovieResponse getByIdMovieResponse(@PathVariable int id) {
		return this.movieService.getMovieById(id);
	}
	
	@GetMapping("/getname/{name}")
	public GetByNameMovieResponse getByNameMovieResponse(@PathVariable String name) {
		return this.movieService.getMovieByName(name);
	}
	

	@PostMapping("/add")
	@ResponseStatus(code=HttpStatus.CREATED)
	public void add(@RequestBody @Valid CreateMovieRequests createMovieRequests) {
		this.movieService.addMovie(createMovieRequests);
	}
	
	@PutMapping("/update/{id}")
	public void update(@PathVariable("id") int id,@RequestBody @Valid UpdateMovieRequests updateMovieRequests) {
		this.movieService.updateMovie(id,updateMovieRequests);
	}
	
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable int id) {
		this.movieService.deleteMovie(id);
	}
	
	
}
