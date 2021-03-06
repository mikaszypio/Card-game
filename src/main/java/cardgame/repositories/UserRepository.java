package cardgame.repositories;

import cardgame.model.User;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
	
	@Query("select u from User u where u.role = 2")
	public List<User> findAllPlayers(Sort sort);
	
	@Query("select u from User u where u.role = 3")
	public List<User> findAllTemporaryUsers(Sort sort);
	
	@Query("select u from User u where u.role = 3 and u.room = null")
	public List<User> findTemporaryUsers(Sort sort);
	
	public User findByUsername(String username);
}
