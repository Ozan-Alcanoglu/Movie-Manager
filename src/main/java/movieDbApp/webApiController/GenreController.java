package movieDbApp.webApiController;

import java.util.List;

import movieDbApp.business.response.GetByNameGenreResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import movieDbApp.business.abstracts.GenreService;
import movieDbApp.business.requests.CreateGenreRequests;
import movieDbApp.business.requests.UpdateGenreRequests;
import movieDbApp.business.response.GetAllGenreResponse;
import movieDbApp.business.response.GetByIdGenreResponse;

@RestController
@AllArgsConstructor
@RequestMapping("/api/moviedb/genres")
public class GenreController {

	private GenreService genreService;
	
	@GetMapping
	@ResponseBody
	public List<GetAllGenreResponse> getAllGenre(){
		return this.genreService.getAllGenre();
		
	}
	
	@GetMapping("/{id}")
	public GetByIdGenreResponse getByIdGenreResponse(@PathVariable int id) {
		return this.genreService.getGenreById(id);
	}

	@GetMapping("/getname/{name}")
	public GetByNameGenreResponse getByNameGenreResponse(@PathVariable String name) {
		return this.genreService.getGenreByName(name);
	}

	@PostMapping("/add")
	@ResponseStatus(code=HttpStatus.CREATED)
	public void add(@RequestBody @Valid CreateGenreRequests createGenreRequests) {
		this.genreService.addGenre(createGenreRequests);
	}
	
	@PutMapping("/update/{id}")
	public void update(@PathVariable("id") int id,@RequestBody @Valid UpdateGenreRequests updateGenreRequests) {
		this.genreService.updateGenre(id,updateGenreRequests);
	}
	
	@DeleteMapping("delete/{id}")
	public void delete(@PathVariable int id) {
		this.genreService.deleteGenre(id);
	}
	
}
