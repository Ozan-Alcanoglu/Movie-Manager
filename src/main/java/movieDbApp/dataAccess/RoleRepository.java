package movieDbApp.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import movieDbApp.entities.Roles;

public interface RoleRepository extends JpaRepository<Roles,Integer>{

	boolean existsByRole(String roles);
}
