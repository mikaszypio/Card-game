package cardgame.controllers;

import cardgame.helpers.IChatHelper;
import cardgame.model.ChatMessage;
import cardgame.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class ChatController {
	
	@Autowired
	private IChatHelper chatHelper;
	
	@MessageMapping("/chatterbox/{roomId}")
	@SendTo("/chat/{roomId}")
	public ChatMessage message(@DestinationVariable int roomId, ChatMessage message) throws Exception {
		
		System.out.println("Jadę");
		return new ChatMessage(HtmlUtils.htmlEscape(message.getAuthor()), message.getContent(), chatHelper.getTime());
	}
}
