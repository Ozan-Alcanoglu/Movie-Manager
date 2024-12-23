package movieDbApp.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import movieDbApp.business.abstracts.RoleService;
import movieDbApp.business.requests.CreateRoleRequests;
import movieDbApp.business.requests.UpdateRoleRequests;
import movieDbApp.business.response.GetAllRoleResponse;
import movieDbApp.business.response.GetByIdRoleResponse;
import movieDbApp.business.rules.RoleBusinessRules;
import movieDbApp.coreUtilites.mappers.ModelMapperService;
import movieDbApp.dataAccess.RoleRepository;
import movieDbApp.entities.Roles;

@Service
@AllArgsConstructor
public class RoleManager implements RoleService{
	
	private RoleRepository roleRepository;
	private ModelMapperService mapperService;
	private RoleBusinessRules businessRules;
	
	@Override
	public List<GetAllRoleResponse> getAllRole() {
		
		List<Roles> roles= this.roleRepository.findAll();
		
		List<GetAllRoleResponse> roleResponse=roles.stream().map(role->this.mapperService.forResponse()
				.map(role, GetAllRoleResponse.class)).collect(Collectors.toList());
		
		return roleResponse;
	}

	@Override
	public GetByIdRoleResponse getRoleById(int id) {
		
		Roles roles=this.roleRepository.findById(id).orElseThrow();
		
		GetByIdRoleResponse roleResponse=this.mapperService.forResponse().map(roles, GetByIdRoleResponse.class);
		
		return roleResponse;
	}

	@Override
	public void addRole(CreateRoleRequests createRoleRequests) {
		
		this.businessRules.chechIfRoleExists(createRoleRequests.getRole());
		
		Roles roles=this.mapperService.forRequest().map(createRoleRequests, Roles.class);
		this.roleRepository.save(roles);
	}

	@Override
	public void updateRole(int id,UpdateRoleRequests updateRoleRequests) {
		
		Roles roles=this.roleRepository.findById(id).orElseThrow();
		this.businessRules.chechIfRoleExists(updateRoleRequests.getRole());
		
		roles.setRole(updateRoleRequests.getRole());
		
		this.roleRepository.save(roles);
		
	}

	@Override
	public void deleteRole(int id) {
		
		this.roleRepository.deleteById(id);
		
	}

}
