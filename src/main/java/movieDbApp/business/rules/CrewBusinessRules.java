package movieDbApp.business.rules;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import movieDbApp.coreUtilites.exceptions.BusinessException;
import movieDbApp.dataAccess.CrewRepository;

@AllArgsConstructor
@Service 
public class CrewBusinessRules {

	private CrewRepository crewRepository;
	
	public void chechIfNameExısts(String name) {
		if(this.crewRepository.existsByName(name)) {
			throw new BusinessException("actor or director name already exists");
		}
	}
}
