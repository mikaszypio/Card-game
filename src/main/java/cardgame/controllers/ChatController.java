package cardgame.controllers;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import cardgame.model.ChatMessage;
import cardgame.model.User;

@Controller
public class ChatController {
	
	@MessageMapping("/chatterbox/{roomId}")
	@SendTo("/chat/{roomId}")
	public ChatMessage message(@DestinationVariable int roomId, User user) throws Exception {
		return new ChatMessage(roomId, 0, "Hello, " +
                        HtmlUtils.htmlEscape(user.getUsername()) + "!");
	}
}
