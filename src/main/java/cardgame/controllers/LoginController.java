package cardgame.controllers;

import cardgame.model.User;
import cardgame.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class LoginController {
	
	@Autowired
	UserRepository userRepository;
	
	public boolean CheckCredentials(User user) throws Exception {
		
		User potentialUser = userRepository.findByUsername(user.getUsername());
		return (potentialUser.getPassword().equals(user.getPassword()));
	}
}
