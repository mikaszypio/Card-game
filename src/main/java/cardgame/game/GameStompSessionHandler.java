package cardgame.game;

import cardgame.model.Interaction;
import java.lang.reflect.Type;
import java.util.List;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

public class GameStompSessionHandler extends StompSessionHandlerAdapter {
	
	private long roomId;
	private List<Interaction> interactions;
	
	public GameStompSessionHandler(long roomId, List<Interaction> interactions) {
		this.roomId = roomId;
		this.interactions = interactions;
	}
	
	@Override
	public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
		String url = "/game/" + roomId;
		session.subscribe(url, this);
		System.out.println("Game socket connected: id = " + roomId);
		System.out.println("Subscribed to " + url);
	}
	
	@Override
	public Type getPayloadType(StompHeaders headers) {
		return Interaction.class;
	}
	
	@Override
	public void handleFrame(StompHeaders headers, Object payload) {
		Interaction interaction = (Interaction) payload;
		interactions.add(interaction);	
	}
	
	@Override
	public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
		exception.printStackTrace();
	}
}
