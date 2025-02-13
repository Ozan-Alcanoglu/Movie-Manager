package movieDbApp.business.abstracts;

import java.util.List;

import movieDbApp.business.response.GetByNameCrewResponse;
import movieDbApp.business.requests.CreateCrewRequests;
import movieDbApp.business.requests.UpdateCrewRequests;
import movieDbApp.business.response.GetAllCrewResponse;
import movieDbApp.business.response.GetByIdCrewResponse;


public interface CrewService {

	List<GetAllCrewResponse> getAllCrew();
	GetByIdCrewResponse getCrewById(int id);
	GetByNameCrewResponse getCrewByName(String name);

	void addCrew(CreateCrewRequests createCrewRequests);
	void updateCrew(int id,UpdateCrewRequests updateCrewRequests);
	void deleteCrew(int id);

}
