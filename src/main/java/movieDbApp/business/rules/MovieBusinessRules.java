package movieDbApp.business.rules;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import movieDbApp.coreUtilites.exceptions.BusinessException;
import movieDbApp.dataAccess.MovieRepository;

@Service
@AllArgsConstructor
public class MovieBusinessRules {

	private MovieRepository movieRepository;
	
	public void checkifMovieNameExists(String name) {
		
		if(this.movieRepository.existsByName(name)) {
			throw new BusinessException("movie name already exists");
		}
	
	}
	
	public boolean validateDateFormat(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate.parse(dateStr, formatter); 
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
	
}
