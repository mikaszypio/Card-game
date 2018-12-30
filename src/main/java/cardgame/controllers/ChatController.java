package cardgame.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import cardgame.model.DummyMessage;
import cardgame.model.User;

@Controller
public class DummyController {
	
	@MessageMapping("/chatterbox")
	@SendTo("/chat/table")
	public DummyMessage message(User user) throws Exception {
		return new DummyMessage("Hello, " + HtmlUtils.htmlEscape(user.getUsername()) + "!");
	}
}
