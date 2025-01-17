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

	@NotNull(message="Crew cannot be empty.")
	@NotBlank(message="Crew cannot be empty.")
	@Size(min=2, max=30)
	private String name;
	
	@NotNull(message="Crew cannot be empty.")
	private int id;
}
