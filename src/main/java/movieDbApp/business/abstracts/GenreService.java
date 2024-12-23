package movieDbApp.business.abstracts;

import java.util.List;

import movieDbApp.business.requests.CreateGenreRequests;
import movieDbApp.business.requests.UpdateGenreRequests;
import movieDbApp.business.response.GetAllGenreResponse;
import movieDbApp.business.response.GetByIdGenreResponse;
import movieDbApp.business.response.GetByNameGenreResponse;


public interface GenreService {

	List<GetAllGenreResponse> getAllGenre();
	GetByIdGenreResponse getGenreById(int id);
	GetByNameGenreResponse getGenreByName(String name);
	
	void addGenre(CreateGenreRequests createGenreRequests);
	void updateGenre(int id,UpdateGenreRequests updateGenreRequests);
	void deleteGenre(int id);
}
