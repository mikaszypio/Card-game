package cardgame.controllers;

import cardgame.model.User;
import cardgame.repositories.UserRepository;
import cardgame.services.UserService;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
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
	public String logIn(String login, String password, HttpSession session) {
		User user = userRepository.findByUsername(login);

		if (user != null) {
			Long userId = user.getUserId();
			if(password.equals(user.getPassword())) {
				session.setAttribute("userId", Long.toString(userId));
				return "redirect:lobby.html";
			}
		}
		
		return "redirect:login.html";
	}
	
	@GetMapping("/session")
	@ResponseBody
	public String getSession(HttpSession session) {
		return session.getAttribute("userId").toString();
	}
	
	@PostMapping("/register")
	public Long registerUser(String login, String password) throws Exception {
		Long id = 0L;
		try {
			userService.createUser(login, password);
			User user = userRepository.findByUsername(login);
			id = user.getUserId();
		} catch (Exception e) {
			e.printStackTrace();
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
