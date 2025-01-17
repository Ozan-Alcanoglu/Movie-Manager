package movieDbApp.business.concretes;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import movieDbApp.business.abstracts.MovieGenreService;
import movieDbApp.business.requests.CreateMovieGenreRequests;
import movieDbApp.dataAccess.GenreRepository;
import movieDbApp.dataAccess.MovieGenreRepository;
import movieDbApp.dataAccess.MovieRepository;
import movieDbApp.entities.Genre;
import movieDbApp.entities.MovieGenre;
import movieDbApp.entities.MovieName;

@Service
@AllArgsConstructor
public class MovieGenreManager implements MovieGenreService{
	
	private MovieGenreRepository movieGenreRepository;
	private MovieRepository movieRepository;
	private GenreRepository genreRepository;
	
	@Override
	public void addMovieGenre(CreateMovieGenreRequests createMovieGenreRequests) {
	
		MovieGenre movieGenre= new MovieGenre();
		
		MovieName movie_id=movieRepository.findByName(createMovieGenreRequests.getMovie());
		Genre genre_id=genreRepository.findByGenre(createMovieGenreRequests.getGenre());
		
		
		movieGenre.setMovie_id(movie_id.getId());
		movieGenre.setGenre_id(genre_id.getId());
		
		this.movieGenreRepository.save(movieGenre);
		
	}

}
