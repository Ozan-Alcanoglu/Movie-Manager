package movieDbApp.business.requests;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMovieRequests {

	@NotNull(message = "Name can not null")
	@NotBlank(message = "Name can not null")
	@Size(min=2, max=30)
	private String name;
	
	@NotNull(message = "Date can not null")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	
	@NotNull(message = "Url can not null")
	@NotBlank(message = "Url can not null")
	private	String imageurl;
	
}
