package cardgame.controllers;

import cardgame.model.User;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import cardgame.repositories.IUserRepository;

@Controller
public class ExperimentalController {
	
	@Autowired
	IUserRepository userRepository;
	
	public boolean CheckCredentials(User user) throws Exception {
		
		User potentialUser = userRepository.findByUsername(user.getUsername());
		return (potentialUser.getPassword().equals(user.getPassword()));
	}
	
	@RequestMapping("/welcome")
	public String welcome(Map<String, Object> model) {
		return "welcome";
	}
	
}
