package movieDbApp.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import movieDbApp.entities.MovieName;


public interface MovieRepository extends JpaRepository<MovieName, Integer>{

	boolean existsByName(String name);
	
	MovieName findByName(String name);
	
	int findIdByName(String name);
	
}
