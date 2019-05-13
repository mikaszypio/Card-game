package cardgame.repositories;

import cardgame.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface IRoleRepository extends CrudRepository<Role, Integer> {
	
}
