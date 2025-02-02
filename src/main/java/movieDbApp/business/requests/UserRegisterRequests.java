package movieDbApp.business.requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequests {

	@NotNull(message = "name can not empty")
	private String name;
	
	@NotNull(message = "password can not empty")
	private String password;
}
