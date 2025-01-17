package movieDbApp.business.concretes;

import java.util.List;
import java.util.stream.Collectors;


import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import movieDbApp.business.abstracts.CrewService;
import movieDbApp.business.requests.CreateCrewRequests;
import movieDbApp.business.requests.UpdateCrewRequests;
import movieDbApp.business.response.GetAllCrewResponse;
import movieDbApp.business.response.GetByIdCrewResponse;
import movieDbApp.business.response.GetByNameCrewResponse;
import movieDbApp.business.rules.CrewBusinessRules;
import movieDbApp.coreUtilites.exceptions.BusinessException;
import movieDbApp.coreUtilites.mappers.ModelMapperService;
import movieDbApp.dataAccess.CrewRepository;
import movieDbApp.dataAccess.RoleRepository;
import movieDbApp.entities.Crew;
import movieDbApp.entities.Roles;

@Service
@AllArgsConstructor
public class CrewManager implements CrewService{
	
	private CrewRepository crewRepository;
	private RoleRepository roleRepository;
	private ModelMapperService mapperService;
	private CrewBusinessRules businessRules;
	
	@Override
	public List<GetAllCrewResponse> getAllCrew() {
		
		List<Crew> crews=this.crewRepository.findAll();
		
		List<GetAllCrewResponse> crewResponse= crews.stream().map(crew->this.mapperService.forResponse()
				.map(crew, GetAllCrewResponse.class)).collect(Collectors.toList());
		
		return crewResponse;
	}

	@Override
	public GetByIdCrewResponse getCrewById(int id) {
		Crew crew=this.crewRepository.findById(id).orElseThrow();
		
		GetByIdCrewResponse crewResponse=this.mapperService.forResponse().map(crew, GetByIdCrewResponse.class);
		
		return crewResponse;
	}
	
	@Override
	public GetByNameCrewResponse getCrewByName(String name) {
		Crew crew=this.crewRepository.findByName(name);
		
		if(crew!=null) {
			GetByNameCrewResponse crewResponse=this.mapperService.forResponse().map(crew, GetByNameCrewResponse.class);
			
			return crewResponse;
		}
		else {
			throw new BusinessException("genre not found");
		}
		
	}


	@Override
	public void addCrew(CreateCrewRequests createCrewRequests) {
		
		this.businessRules.chechIfNameExısts(createCrewRequests.getName());
		Roles role = roleRepository.findById(createCrewRequests.getId())
                .orElseThrow(() -> new BusinessException("Role not found"));

		
		Crew crew = new Crew();
		crew.setName(createCrewRequests.getName());
		crew.setRoles(role);
		
		this.crewRepository.save(crew);
		
	}

	@Override
	public void updateCrew(int id,UpdateCrewRequests updateCrewRequests) {
		
		this.businessRules.chechIfNameExısts(updateCrewRequests.getName());
		Crew crew=this.crewRepository.findById(id).orElseThrow();
		
		crew.setName(updateCrewRequests.getName());
		
		this.crewRepository.save(crew);
		
	}

	@Override
	public void deleteCrew(int id) {
		
		this.crewRepository.deleteById(id);
	}

}
