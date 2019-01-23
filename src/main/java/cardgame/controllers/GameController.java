package cardgame.controllers;

import cardgame.model.ChatMessage;
import cardgame.model.User;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GameController {
	
	@MessageMapping("/activegames/{gameId}")
	@SendTo("/game/{gameId}")
	public ChatMessage message(@DestinationVariable int gameId, ChatMessage message) throws Exception {

		return new ChatMessage(HtmlUtils.htmlEscape(message.getAuthor()), message.getContent(), "ee");
	}
}
