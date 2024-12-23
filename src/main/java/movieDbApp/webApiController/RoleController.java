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
import movieDbApp.business.abstracts.RoleService;
import movieDbApp.business.requests.CreateRoleRequests;
import movieDbApp.business.requests.UpdateRoleRequests;
import movieDbApp.business.response.GetAllRoleResponse;
import movieDbApp.business.response.GetByIdRoleResponse;

@RestController
@AllArgsConstructor
@RequestMapping("/moviedb/roles")
public class RoleController {

	private RoleService roleService;
	
	@GetMapping
	public List<GetAllRoleResponse> getAllRole(){
		return this.roleService.getAllRole();
	}
	
	@GetMapping("/{id}")
	public GetByIdRoleResponse getByIdRoleResponse(@PathVariable int id) {
		return this.roleService.getRoleById(id);
	}
	
	@PostMapping("/add")
	@ResponseStatus(code=HttpStatus.CREATED)
	public void add(@RequestBody @Valid CreateRoleRequests createRoleRequests) {
		this.roleService.addRole(createRoleRequests);
	}
	
	@PutMapping("/update/{id}")
	public void update(@PathVariable("id") int id,@RequestBody @Valid UpdateRoleRequests updateRoleRequests) {
		this.roleService.updateRole(id,updateRoleRequests);
	}
	
	@DeleteMapping("delete/{id}")
	public void delete(@PathVariable int id) {
		this.roleService.deleteRole(id);
	}
	
}
