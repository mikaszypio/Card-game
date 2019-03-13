package cardgame.controllers;

import cardgame.model.Room;
import cardgame.model.User;
import cardgame.services.RoomService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class RoomController {
	
	@Autowired
	RoomService roomService;
	
	public void createRoom(String name, User user) {
		
		Room room = roomService.getEmptyRoom(name);
		roomService.joinRoom(user, room);
	}
	
	public List<Room> getRooms() {
		return roomService.getActiveRooms();
	}
}
