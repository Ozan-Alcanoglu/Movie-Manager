package movieDbApp.business.response;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllMovieResponse {

	private int id;
	private String name;
	private Date date;
	private String url;
}
