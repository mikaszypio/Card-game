package cardgame.helpers;

import cardgame.model.Room;
import cardgame.model.User;
import cardgame.repositories.RoomRepository;
import cardgame.repositories.UserRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomHelper implements IRoomHelper {

	@Autowired
	RoomRepository roomRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public Room getEmptyRoom(String name) {
		
		Room room = roomRepository.findByActiveAndReadiness(false, (byte) -1);
		if(room == null) {
			room = new Room(name);
		}
		
		return room;
	}

	@Override
	@Transactional
	public void joinRoom(User user, Room room) {
		
		user.setRoom(room);
		if (room.getReadiness() == -1) {
			room.setReadiness((byte) 0);
		}
		
		userRepository.save(user);
		roomRepository.save(room);
	}

	@Override
	public void leaveRoom(User user) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
}
