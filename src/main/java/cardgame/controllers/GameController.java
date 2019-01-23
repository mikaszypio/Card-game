package cardgame.controllers;

import cardgame.model.ChatMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GameController {
	
	@MessageMapping("/activegames/{gameId}/{playerId}")
	@SendTo("/game/{gameId}/{playerId}")
	public ChatMessage message(@DestinationVariable int gameId, 
		@DestinationVariable int playerId,
		ChatMessage message) throws Exception {

		return new ChatMessage(HtmlUtils.htmlEscape(""),
			HtmlUtils.htmlEscape(message.getContent()),
			HtmlUtils.htmlEscape(""));
	}
}
