package movieDbApp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Table(name="crew")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Crew {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="name",unique=true)
	private String name;
	
	
	@ManyToOne
	@JoinColumn(name="role_id")
	private Roles roles;

}
