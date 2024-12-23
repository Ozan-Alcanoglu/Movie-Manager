package movieDbApp.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="movie")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MovieName {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="name",unique=true)
	private String name;
	
	@Temporal(TemporalType.DATE)
	@Column(name="date")
	private LocalDate date;
	
	@Column(name="image_url", length=500)
	private String imageUrl;

	
}
