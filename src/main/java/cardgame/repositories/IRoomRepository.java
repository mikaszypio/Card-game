package cardgame.repositories;

import cardgame.model.Room;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IRoomRepository extends CrudRepository<Room, Long> {
	
	@Query("select r from Room r where r.active = ?1")
	List<Room> findByActive(boolean active);
	
	Room findByActiveAndReadiness(boolean active, byte readiness);
	Room findByName(String name);
}
