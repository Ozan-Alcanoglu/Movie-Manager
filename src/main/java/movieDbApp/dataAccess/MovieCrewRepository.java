package movieDbApp.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import movieDbApp.entities.MovieCrew;

public interface MovieCrewRepository extends JpaRepository<MovieCrew, Integer>{

	
}
