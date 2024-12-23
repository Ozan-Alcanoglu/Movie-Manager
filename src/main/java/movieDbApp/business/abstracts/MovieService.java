package movieDbApp.business.abstracts;

import java.util.List;


import movieDbApp.business.requests.CreateMovieRequests;
import movieDbApp.business.requests.UpdateMovieRequests;
import movieDbApp.business.response.*;

public interface MovieService {

	
	List<GetAllMovieResponse> getAllMovie();
	GetByIdMovieResponse getMovieById(int id);
	GetByNameMovieResponse getMovieByName(String name);
	
	void addMovie(CreateMovieRequests createMovieRequests);
	void updateMovie(int id, UpdateMovieRequests updateMovieRequests);
	void deleteMovie(int id);
	

	
}
