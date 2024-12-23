package movieDbApp.business.abstracts;

import java.util.List;

import movieDbApp.business.requests.CreateRoleRequests;
import movieDbApp.business.requests.UpdateRoleRequests;
import movieDbApp.business.response.GetAllRoleResponse;
import movieDbApp.business.response.GetByIdRoleResponse;

public interface RoleService {

	List<GetAllRoleResponse> getAllRole();
	GetByIdRoleResponse getRoleById(int id);
	
	void addRole(CreateRoleRequests createRoleRequests);
	void updateRole(int id,UpdateRoleRequests updateRoleRequests);
	void deleteRole(int id);


}
