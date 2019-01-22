package cardgame.controllers;

import cardgame.model.User;
import cardgame.repositories.UserRepository;
import cardgame.services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userService;
	
	public User getUserDetails(Long id) {
		
		userRepository.findById(id);
	}
	
	public List<User> getAllUsers() {
		
		return userService.findAllPlayers();
	}
	
	public List<User> getRanking() {
		
		return userService.getRanking();
	}
}
