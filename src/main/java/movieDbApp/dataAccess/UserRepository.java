package movieDbApp.dataAccess;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import movieDbApp.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	User findByName(String name);
	boolean existsByPassword(String password);
	User findById(Long id);
	
}
