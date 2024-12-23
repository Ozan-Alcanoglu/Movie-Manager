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
public class CreateMovieRequests {

	@NotNull(message = "isim boş olamaz")
	@NotBlank(message = "isim boş olamaz")
	@Size(min=2, max=30)
	private String name;
	
	@NotNull(message = "tarih kısmı boş bırakılamaz")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	
	@NotNull(message = "url boş olamaz")
	@NotBlank(message = "url boş olamaz")
	private	String imageurl;
}
