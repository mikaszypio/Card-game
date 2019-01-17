package cardgame.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;

import cardgame.model.User;
import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
	
	@Query("select u from User u where u.room.roomId = ?1")
	List<User> findByRoomId(Long roomId);
}
