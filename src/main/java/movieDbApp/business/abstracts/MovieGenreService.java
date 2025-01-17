package movieDbApp.business.abstracts;

import movieDbApp.business.requests.CreateMovieGenreRequests;

public interface MovieGenreService {

	void addMovieGenre(CreateMovieGenreRequests createMovieGenreRequests);
}
