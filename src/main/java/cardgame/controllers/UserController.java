package cardgame.controllers;

import cardgame.model.User;
import cardgame.model.Role;
import cardgame.repositories.UserRepository;
import cardgame.services.UserService;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
	
	@PostMapping("/login")
	public Long logIn(String login, String password, HttpSession session) {
		System.out.println("Nadeszło: " + login);
		User user = userRepository.findByUsername(login);

		if (user != null) {
			Long userId = user.getUserId();
			session.setAttribute("Id", userId);
			System.out.println(session.getAttribute("Id"));
			return user.getUserId();
		}
		return 0L;
	}
	
	@PostMapping("/register")
	public Long registerUser(String login, String password) throws Exception {
		Long id = 0L;
		try {
			userService.createUser(login, password);
			User user = userRepository.findByUsername(login);
			id = user.getUserId();
		} catch (Exception e) {
			System.out.println(e);
		}
		return id;
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
