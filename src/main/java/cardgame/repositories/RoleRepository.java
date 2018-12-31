package cardgame.repositories;

import org.springframework.data.repository.CrudRepository;

import cardgame.model.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {
	
}
