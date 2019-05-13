package cardgame.controllers;

import cardgame.model.Room;
import cardgame.model.User;
import cardgame.repositories.IRoomRepository;
import cardgame.repositories.IUserRepository;
import cardgame.services.IRoomService;
import cardgame.services.IUserService;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class RoomController {
	
	@Autowired
	IRoomRepository roomRepository;
	
	@Autowired
	IRoomService roomService;
	
	@Autowired
	IUserRepository userRepository;
	
	@Autowired
	IUserService userService;
	
	public void createRoom(String name, User user) {
		
		Room room = roomService.getEmptyRoom(name);
		roomService.joinRoom(user, room);
	}
	
	public List<Room> getRooms() {
		return roomService.getActiveRooms();
	}
	
	public void joinRoom(String roomName, HttpSession session) {
		
		User user;
		Long userId = (Long) session.getAttribute("userId");
		if(userId == null) {
			user = userService.getUnregistered();
			session.setAttribute("userId", Long.toString(user.getUserId()));
		} else {
			user = userRepository.findById(userId).get();
		}
		
		Room room = roomRepository.findByName(roomName);
		roomService.joinRoom(user, room);
	}
	
	public void leaveRoom(HttpSession session) {
		
		Long userId = (Long) session.getAttribute("userId");
		User user = userRepository.findById(userId).get();
		roomService.leaveRoom(user);
	}
}
