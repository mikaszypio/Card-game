package cardgame.controllers;

import cardgame.model.Role;
import cardgame.model.SessionData;
import cardgame.model.User;
import cardgame.repositories.IUserRepository;
import cardgame.services.IUserService;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
	
	@Autowired
	IUserRepository userRepository;
	
	@Autowired
	IUserService userService;
	
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
	@ResponseBody
	public SessionData logIn(String login, String password, HttpSession session) {
		
		User user = userRepository.findByUsername(login);
		if (user != null) {
			Long userId = user.getUserId();
			String username = user.getUsername();
			Role role = user.getRole();
			
			// Will be used to disable login as annon
			int roleId = role.getRoleId();
			
			if(password.equals(user.getPassword())) {
				SessionData sessionData = new SessionData(userId, username);
				session.setAttribute("userId", Long.toString(userId));
				session.setAttribute("username", username);
				//return "redirect:lobby.html";
				return sessionData;
			}
		}
		
		//return "redirect:login.html";
		return null;
	}
	
	@PostMapping("/logout")
	public String logOut(HttpSession session) {
		
		session.invalidate();
		return Integer.toString(-1);
	}
	
	@GetMapping("/session")
	@ResponseBody
	public SessionData getSession(HttpSession session) {
		
		SessionData sessionData;
		String userId = (String) session.getAttribute("userId");
		if(userId != null && !userId.isEmpty()) {
			String username = (String) session.getAttribute("username");
			sessionData = new SessionData(Long.getLong(userId), username);
			return sessionData;
		}
		
		User anon = userService.findFreeAnon();
		Long anonId = anon.getUserId();
		String username = anon.getUsername();
		sessionData = new SessionData(anonId, username);
		session.setAttribute("userId", Long.toString(anonId));
		session.setAttribute("username", username);
		return sessionData;
	}
	
	@PostMapping("/register")
	@ResponseBody
	public SessionData registerUser(String login, String password, HttpSession session) 
		throws Exception {
		
		Long userId;
		String username;
		SessionData sessionData;
		try {
			userService.createUser(login, password);
			User user = userRepository.findByUsername(login);
			userId = user.getUserId();
			username = user.getUsername();
			sessionData = new SessionData(userId, username);
			session.setAttribute("userId", Long.toString(userId));
			session.setAttribute("username", username);
		} catch (Exception e) {
			// e.printStackTrace();
			//return "redirect:register.html";
			return null;
		}
		
		if (userId != 0){
			//return "redirect:lobby.html";
			return sessionData;
		}
		
		//return "redirect:register.html";
		return null;
	}
}
