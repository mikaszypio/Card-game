package cardgame.controllers;

import cardgame.model.Role;
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
			Role role = user.getRole();
			
			// Will be used to disable login as annon
			int roleId = role.getRoleId();
			
			if(password.equals(user.getPassword())) {
				session.setAttribute("userId", Long.toString(userId));
				//return "redirect:lobby.html";
				return userId.toString();
			}
		}
		
		//return "redirect:login.html";
		return "0";
	}
	
	@GetMapping("/session")
	@ResponseBody
	public String getSession(HttpSession session) {
		String userId = (String) session.getAttribute("userId");
		if(userId != null && !userId.isEmpty()) {
			return userId;
		}
		
		User anon = userService.findFreeAnon();
		Long anonId = anon.getUserId();
		session.setAttribute("userId", Long.toString(anonId));
		return anonId.toString();
	}
	
	@PostMapping("/register")
	public String registerUser(String login, String password, HttpSession session) 
		throws Exception {
		Long userId = 0L;
		try {
			userService.createUser(login, password);
			User user = userRepository.findByUsername(login);
			userId = user.getUserId();
			session.setAttribute("userId", Long.toString(userId));
		} catch (Exception e) {
			e.printStackTrace();
			//return "redirect:register.html";
			return "0";
		}
		
		if (userId != 0){
			//return "redirect:lobby.html";
			return userId.toString();
		}
		
		//return "redirect:register.html";
		return "0";
	}
}
