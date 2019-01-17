package cardgame.services;

import cardgame.model.Room;
import cardgame.model.User;
import cardgame.repositories.RoomRepository;
import cardgame.repositories.UserRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService implements IRoomService {

	@Autowired
	RoomRepository roomRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public boolean checkReadiness(Room room) {
		
		return room.getReadiness() == getRoomUsers(room).size();
	}
	
	@Override
	public Room getEmptyRoom(String name) {
		
		Room room = roomRepository.findByActiveAndReadiness(false, (byte) -1);
		if(room == null) {
			room = new Room(name);
		}
		
		return room;
	}
	
	private List<User> getRoomUsers(Room room) {
		
		return roomRepository.findById(room.getRoomId()).get().getUsers();
	}

	@Override
	@Transactional
	public void joinRoom(User user, Room room) {
		
		if (room.getReadiness() == -1) {
			room.setReadiness((byte) 0);
			roomRepository.save(room);
		}
		
		user.setRoom(room);
		userRepository.save(user);
	}

	@Override
	@Transactional
	public void leaveRoom(User user) {
		
		Room room = user.getRoom();
		if (getRoomUsers(room).size() == 1) {
			room.setReadiness((byte) -1);
			room.setActive(false);
			roomRepository.save(room);
		}
		
		user.setRoom(null);
		userRepository.save(user);
	}	
}
