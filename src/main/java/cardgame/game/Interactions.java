package cardgame.game;

import cardgame.game.model.Deck;
import cardgame.game.model.Player;
import cardgame.game.model.cards.Card;
import cardgame.model.Interaction;
import cardgame.model.InteractionType;
import cardgame.viewmodel.GameboardViewModel;
import cardgame.viewmodel.PartialCard;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

public class Interactions {
	
	private long sessionId;
	private List<Interaction> interactions;
	private String gameUrl;
	private StompSession stompSession;
	private final Gson gson;
	
	public Interactions(long sessionId) {
		this.sessionId = sessionId;
		interactions = new ArrayList<>();
		gameUrl = "/app/activegame/" + (long) sessionId;
		try {
			stompSession = createStompSession();
		} catch(InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
		
		gson = new Gson();
	}
	
	public boolean selectAlternativeCardGetting() {
		return false;
	}
	
	public boolean broadcastViewModel(List<Player> players, int activePlayerIndex, Deck deck) {
		boolean success = false;
		for(Player player : players) {
			PartialCard topRejectedCard = null;
			if (!deck.withoutRejectedCards()) {
				topRejectedCard = new PartialCard(deck.getRejectedCard(false));
			}
			
			GameboardViewModel viewModel = new GameboardViewModel(players, activePlayerIndex,
				(long) player.getId(), deck.getRejectedCardsSize(), topRejectedCard);
			
			try {
				byte[] msg = gson.toJson(viewModel).getBytes(StandardCharsets.UTF_8);
				stompSession.send(gameUrl + "/" + (long)player.getId(), msg);
				success = true;
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return success;
	}
	
	private Interaction getMessage(InteractionType type, long playerId) {
		if(!interactions.isEmpty()) {
			for(Interaction interaction : interactions) {
				if(interaction.getPlayerId() == playerId
					&& type == interaction.getType()) {
					interactions.remove(interaction);
					return interaction;
				}
			}
		}
		
		return null;
	}
	
	private StompSession createStompSession() throws InterruptedException, ExecutionException {
		WebSocketClient simpleWebSocketClient = new StandardWebSocketClient();
		List<Transport> transports = new ArrayList<>(1);
		transports.add(new WebSocketTransport(simpleWebSocketClient));
		SockJsClient sockJsClient = new SockJsClient(transports);
		WebSocketStompClient client = new WebSocketStompClient(sockJsClient);
		client.setMessageConverter(new MappingJackson2MessageConverter());
		String url = "ws://localhost:8080/game-socket";
		StompSessionHandler sessionHandler = new GameStompSessionHandler(sessionId, interactions);
		StompSession session = client.connect(url, sessionHandler).get();
		return session;
	}
	
	public Card selectCard(List<Card> cardsInHand, long playerId) {
		//
		cardsInHand.forEach((card) -> {
			System.out.println(card.dajID() + card.dajNazwe());
		});
		//
		
		Interaction interaction = null;
		byte[] msg = gson.toJson(InteractionType.CARDSELECTION).getBytes(StandardCharsets.UTF_8);
		stompSession.send(gameUrl + "/" + playerId, msg);

		//
		Interaction testInteraction = new Interaction(playerId, InteractionType.CARDSELECTION, -1);
		msg = gson.toJson(testInteraction).getBytes(StandardCharsets.UTF_8);
		System.out.println("Wysyłam na: " + gameUrl);
		stompSession.send(gameUrl, msg);
		//

		while(interaction == null) {
			interaction = getMessage(InteractionType.CARDSELECTION, playerId);
		}
		
		if (interaction.getSelection() == -1) {
			return null;
		}
		
		for(Card card : cardsInHand) {
			if(card.dajID() == interaction.getSelection()) {
				return card;
			}
		}
		
		return null;
	}
	
	public Player selectTargetPlayer(Player activePlayer, List<Player> players) {
		long playerId = activePlayer.getId();
		//
		players.forEach((player) -> {
			if (activePlayer != player) {
				System.out.println(player.getId() + " " + player.getNickname());
			}
		});
		//
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		Interaction interaction = null;
		byte[] msg = null;
		try {
			msg = objectMapper.writeValueAsBytes(InteractionType.TARGETSELECTION);
		} catch (JsonProcessingException ex) {
			Logger.getLogger(Interactions.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		stompSession.send(gameUrl + "/" + playerId, msg);
		
		//
		Interaction testInteraction = new Interaction(playerId, InteractionType.TARGETSELECTION, -1);
		msg = gson.toJson(testInteraction).getBytes(StandardCharsets.UTF_8);
		stompSession.send(gameUrl, msg);
		//
		
		while(interaction == null) {
			interaction = getMessage(InteractionType.TARGETSELECTION, playerId);
		}
		
		if (interaction.getSelection() == -1) {
			return null;
		}
		
		for(Player player : players) {
			if(player.getId() == interaction.getSelection()) {
				return player;
			}
		}
		
		return null;
	}
}
