package cardgame.repositories;

import cardgame.model.Room;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, Long> {
	
	Room findByActiveAndReadiness(boolean active, byte readiness);
	Room findByName(String name);
}
