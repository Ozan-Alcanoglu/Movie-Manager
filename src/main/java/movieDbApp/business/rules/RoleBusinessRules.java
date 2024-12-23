package movieDbApp.business.rules;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import movieDbApp.coreUtilites.exceptions.BusinessException;
import movieDbApp.dataAccess.RoleRepository;

@AllArgsConstructor
@Service  
public class RoleBusinessRules {

	private RoleRepository repository;
	
	public void chechIfRoleExists(String role) {
		
		if(this.repository.existsByRole(role)) {
			throw new BusinessException("role already exists");
		}
	}
}
