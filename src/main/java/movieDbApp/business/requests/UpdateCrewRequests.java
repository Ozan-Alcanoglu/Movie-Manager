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
public class UpdateCrewRequests {

	@NotNull(message="Crew can not null")
	@NotBlank(message="Crew can not null")
	@Size(min=2, max=30)
	private String name;


}
