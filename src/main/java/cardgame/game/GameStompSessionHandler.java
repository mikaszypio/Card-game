package cardgame.game;

import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

public class GameStompSessionHandler extends StompSessionHandlerAdapter {
	
	private long roomId;
	
	public GameStompSessionHandler(long roomId) {
		this.roomId = roomId;
	}
	
	@Override
	public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
		
		System.out.println("Game socket connected: id" + roomId);
	}
	
	@Override
	public void handleFrame(StompHeaders headers, Object payload) {
		
	}
}
