package movieDbApp.business.concretes;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import movieDbApp.business.abstracts.MovieCrewService;
import movieDbApp.business.requests.CreateMovieCrewRequests;
import movieDbApp.dataAccess.CrewRepository;
import movieDbApp.dataAccess.GenreRepository;
import movieDbApp.dataAccess.MovieCrewRepository;
import movieDbApp.dataAccess.MovieRepository;
import movieDbApp.entities.Crew;
import movieDbApp.entities.MovieCrew;
import movieDbApp.entities.MovieName;

@Service
@AllArgsConstructor
public class MovieCrewManager implements MovieCrewService{
	
	
	private MovieCrewRepository movieCrewRepository;
	private MovieRepository movieRepository;
	private CrewRepository crewRepository;	
	
	@Override
	public void addMovieCrew(CreateMovieCrewRequests createMovieCrewRequests) {
		
		MovieCrew movieCrew= new MovieCrew();
		
		MovieName movieName=this.movieRepository.findByName(createMovieCrewRequests.getMovie());
		Crew crew=this.crewRepository.findByName(createMovieCrewRequests.getCrew());
		
		movieCrew.setMovie_id(movieName.getId());
		movieCrew.setCrew_id(crew.getId());

		this.movieCrewRepository.save(movieCrew);
		
	}

}
