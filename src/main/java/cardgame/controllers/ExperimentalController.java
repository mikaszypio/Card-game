package cardgame.controllers;

import cardgame.model.User;
import cardgame.repositories.UserRepository;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExperimentalController {
	
	@Autowired
	UserRepository userRepository;
	
	public boolean CheckCredentials(User user) throws Exception {
		
		User potentialUser = userRepository.findByUsername(user.getUsername());
		return (potentialUser.getPassword().equals(user.getPassword()));
	}
	
	@RequestMapping("/welcome")
	public String welcome(Map<String, Object> model) {
		return "welcome";
	}
	
}
