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
import movieDbApp.business.abstracts.CrewService;
import movieDbApp.business.requests.CreateCrewRequests;
import movieDbApp.business.requests.UpdateCrewRequests;
import movieDbApp.business.response.GetAllCrewResponse;
import movieDbApp.business.response.GetByIdCrewResponse;

@RestController
@AllArgsConstructor
@RequestMapping("/moviedb/crews")
public class CrewController {

	private CrewService crewService;
	
	@GetMapping
	public List<GetAllCrewResponse> getAllCrew(){
		return this.crewService.getAllCrew();
		
	}
	
	@GetMapping("/getbyid/{id}")
	public GetByIdCrewResponse getByIdCrewResponse(@PathVariable int id) {
		return this.crewService.getCrewById(id);
	}
	
	@PostMapping("/add")
	@ResponseStatus(code=HttpStatus.CREATED)
	public void add(@RequestBody @Valid CreateCrewRequests createCrewRequests) {
		this.crewService.addCrew(createCrewRequests);
	}
	
	@PutMapping("/update/{id}")
	public void update(@PathVariable("id") int id,@RequestBody @Valid UpdateCrewRequests updateCrewRequests) {
		this.crewService.updateCrew(id,updateCrewRequests);
	}
	
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable int id) {
		this.crewService.deleteCrew(id);
	}
	
}
