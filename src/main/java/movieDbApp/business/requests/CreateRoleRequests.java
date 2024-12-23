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
public class CreateRoleRequests {

	@NotNull(message="role boş olamaz")
	@NotBlank(message="role boş olamaz")
	@Size(min=2, max=20)
	private String role;
}
