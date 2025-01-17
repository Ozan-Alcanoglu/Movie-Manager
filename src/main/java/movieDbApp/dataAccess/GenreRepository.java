package movieDbApp.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import movieDbApp.entities.Genre;

public interface GenreRepository extends JpaRepository<Genre,Integer>{

	boolean existsByGenre(String genre);

	Genre findByGenre(String genre);

	int findIdByGenre(String genre);
	
}
