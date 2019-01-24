package cardgame.controllers;

import cardgame.model.User;
import cardgame.model.Role;
import cardgame.repositories.UserRepository;
import cardgame.services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userService;
	
	public User getUserDetails(Long id) {
		
		return userRepository.findById(id).get();
	}
	
	public List<User> getAllUsers() {
		
		return userService.findAllPlayers();
	}
	
	public List<User> getRanking() {
		
		return userService.getRanking();
	}
	
	public Long logIn(String username, String password) {
		List<User> allUsers = getAllUsers();
		
		for (User user : allUsers) {
			if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
				return user.getUserId(); 
			}
		}
		return 0L;
	}
	
	public Long registerUser(String username, String password) throws Exception {
		try {
			userService.createUser(username, password);
			
			List<User> allUsers = getAllUsers();
			
			for(User user : allUsers) {
				if (user.getUsername().equals(username)) {
					return user.getUserId(); 
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return 0L;
	}
	
	public User findTemporaryUserWithNoRoom() {
		List<User> temporaryList = userRepository.findTemporaryUsers(Sort.by("username").ascending());
		User temporaryUserWithNoRoom = new User();
		if (temporaryList.size() > 0) {
			temporaryUserWithNoRoom = temporaryList.get(0);
		}
		return temporaryUserWithNoRoom;
	}
	
	
}
