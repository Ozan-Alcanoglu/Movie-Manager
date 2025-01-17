package movieDbApp.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import movieDbApp.entities.MovieGenre;

public interface MovieGenreRepository extends JpaRepository<MovieGenre, Integer>{

}
