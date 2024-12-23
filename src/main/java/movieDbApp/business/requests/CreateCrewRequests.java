package movieDbApp.business.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCrewRequests {

	@NotNull(message="crew boş olamaz")
	@NotBlank(message="crew boş olamaz")
	@Size(min=2, max=30)
	private String name;
	
	@NotNull(message="crew boş olamaz")
	private int id;
}
