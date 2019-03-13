package cardgame.services;

import cardgame.game.Gra;
import cardgame.game.model.Gracz;
import cardgame.model.Room;
import cardgame.model.User;
import cardgame.repositories.RoomRepository;
import cardgame.repositories.UserRepository;

import java.util.ArrayList;
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
	
	public List<Room> getActiveRooms() {
		return roomRepository.findByActive(true);
		
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

	@Override
	public Room save(Room room) {
		
		return roomRepository.save(room);
	}
	
	@Override
	public void startGame() {
		Gra game = new Gra(new ArrayList<> (), 1);
		game.start();
	}
	
	@Override
	public Gracz userToPlayer(User user) {
		Gracz player = new Gracz(user.getUsername(), user.getUserId());
		return player;
	}
	
	@Override
	public List<Gracz> listOfPlayers(Room room){
		List<Gracz> playersList = new ArrayList<>();
		List<User> usersList = getRoomUsers(room);
		for(User user : usersList) {
			playersList.add(userToPlayer(user));
		}
		return playersList;
	}
}
