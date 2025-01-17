package movieDbApp.business.requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMovieGenreRequests {

	@NotNull(message="Movie can not null")
	private String movie;
	
	@NotNull(message="Crew can not null")
	private String genre;
}
