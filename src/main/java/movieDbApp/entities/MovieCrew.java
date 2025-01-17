package movieDbApp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="movie_crew")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MovieCrew {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="movie_id")
	private int movie_id;
	
	@Column(name="crew_id")
	private int crew_id;
	
}
