package movieDbApp.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import movieDbApp.entities.Crew;

public interface CrewRepository  extends JpaRepository<Crew,Integer>{

	boolean existsByName(String name);
	
	int findIdByName(String name);
}
