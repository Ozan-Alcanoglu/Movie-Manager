package movieDbApp.business.response;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetByIdMovieResponse {

	private int id;
	private String name;
	private Date date;
}
