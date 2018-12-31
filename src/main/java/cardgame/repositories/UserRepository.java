package cardgame.repositories;

import org.springframework.data.repository.CrudRepository;

import cardgame.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
}
