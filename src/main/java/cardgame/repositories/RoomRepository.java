package cardgame.repositories;

import org.springframework.data.repository.CrudRepository;

import cardgame.model.Room;

public interface RoomRepository extends CrudRepository<Room, Long> {
	
}
