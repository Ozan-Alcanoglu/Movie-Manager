package movieDbApp.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import movieDbApp.business.response.*;
import movieDbApp.dataAccess.*;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import movieDbApp.business.abstracts.MovieService;

import movieDbApp.business.requests.CreateMovieRequests;
import movieDbApp.business.requests.UpdateMovieRequests;
import movieDbApp.business.rules.MovieBusinessRules;
import movieDbApp.coreUtilites.exceptions.BusinessException;
import movieDbApp.coreUtilites.mappers.ModelMapperService;

import movieDbApp.entities.MovieName;

@Service
@AllArgsConstructor
public class MovieManager implements MovieService{


	private MovieRepository movieRepository;
	private ModelMapperService mapperService;
	private MovieBusinessRules businessRules;

	
	

	
	@Override
	public List<GetAllMovieResponse> getAllMovie() {
		
		List<MovieName> movieName= movieRepository.findAll();
		
		List<GetAllMovieResponse> movieResponses = movieName.stream().map(movie->this.mapperService.forResponse()
				.map(movie,GetAllMovieResponse.class)).collect(Collectors.toList());
		
		return movieResponses;
	}

	@Override
	public GetByIdMovieResponse getMovieById(int id) {
		
		MovieName movieName= this.movieRepository.findById(id).orElseThrow();
		
		if(movieName!=null) {
			
			GetByIdMovieResponse movieResponse=this.mapperService.forResponse().map(movieName, GetByIdMovieResponse.class);
			
			return movieResponse;			
		}
		else {
			throw new BusinessException("The movie with this ID is not available.");
		}
		
	}
	
	@Override
	public GetByNameMovieResponse getMovieByName(String name) {
		
		MovieName movieName=this.movieRepository.findByName(name);
		
		if(movieName!=null) {
			
			GetByNameMovieResponse movieResponse=this.mapperService.forResponse().map(movieName, GetByNameMovieResponse.class);
		
			return movieResponse;
		}
		else {
			throw new BusinessException("movie not found");
		}
	}
	
	
	

	@Override
	public void addMovie(CreateMovieRequests createMovieRequests) {
		
		this.businessRules.checkifMovieNameExists(createMovieRequests.getName());
		boolean isValidDate = businessRules.validateDateFormat(createMovieRequests.getDate().toString());
	    if (!isValidDate) {
	        throw new BusinessException("Invalid date format. Please use the yyyy-MM-dd format.");
	    }
		
	    MovieName movieName=new MovieName();
	    
		movieName.setDate(createMovieRequests.getDate());
		movieName.setName(createMovieRequests.getName());
		movieName.setImageUrl(createMovieRequests.getImageurl());
		
		this.movieRepository.save(movieName);

		
	}

	@Override
	public void updateMovie(int id, UpdateMovieRequests updateMovieRequests) {
		

		MovieName movieName=this.movieRepository.findById(id).orElseThrow();
		
		
		movieName.setName(updateMovieRequests.getName());
		movieName.setDate(updateMovieRequests.getDate());
		movieName.setImageUrl(updateMovieRequests.getImageurl());
		
		this.movieRepository.save(movieName);
		
	}

	@Override
	public void deleteMovie(int id) {
		
		this.movieRepository.deleteById(id);
		
	}


}
