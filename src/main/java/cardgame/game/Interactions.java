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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
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
	private final List<Interaction> interactions;
	private final ObjectMapper objectMapper;
	private String gameUrl;
	private StompSession stompSession;
	
	public Interactions(long sessionId) {
		this.sessionId = sessionId;
		interactions = new ArrayList<>();
		gameUrl = "/app/activegame/" + (long) sessionId;
		try {
			stompSession = createStompSession();
		} catch(InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
		objectMapper= new ObjectMapper();
	}
	
	public boolean broadcastViewModel(List<Player> players, int activePlayerIndex, Deck deck) {
		boolean success = false;
		for(Player player : players) {
			PartialCard topPartialCard = null;
			Card topRejectedCard = deck.getRejectedCard(false);
			if(topRejectedCard != null) {
				topPartialCard = new PartialCard(topRejectedCard);
			}
			
			GameboardViewModel viewModel = new GameboardViewModel(players, activePlayerIndex,
				(long) player.getId(), deck.getRejectedCardsSize(), topPartialCard);
			sendMessage(viewModel, player.getId());
			success = true;
		}
		
		return success;
	}
	
	private Interaction getMessage(InteractionType type, long playerId) {
		if(!interactions.isEmpty()) {
			interactions.size();
			for(Interaction interaction : interactions) {
				System.out.println(type + " | " + interaction.getType());
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
	
	public boolean getCardsAlternativeWay(long playerId, List<Player> players) {
		System.out.println("Getting cards alternative way: (0-whatever)");
		
		
		sendMessage(InteractionType.ALTERNATIVEGET, playerId);
		
		//Interaction test = new Interaction(playerId, InteractionType.ALTERNATIVEGET, -1);
		//sendMessage(test, null);
		
		Interaction interaction = waitForInteraction(InteractionType.ALTERNATIVEGET, playerId);
		return interaction.getSelection() != 0;
	}
	
	public Card selectCard(List<Card> cardsInHand, long playerId) {
		//
		//cardsInHand.forEach((card) -> {
		//	System.out.println(card.dajID() + card.dajNazwe());
		//});
		//
		
		sendMessage(InteractionType.CARDSELECTION, playerId);

		//
		//Interaction testInteraction = new Interaction(playerId, InteractionType.CARDSELECTION, -1);
		//sendMessage(testInteraction, null);
		//

		Interaction interaction = waitForInteraction(InteractionType.CARDSELECTION, playerId);
		
		if (interaction.getSelection() == -1) {
			return null;
		}
		
		for(Card card : cardsInHand) {
			if(card.dajID() == interaction.getSelection()) {
				return card;
			}
		}
		
		System.out.println("Zagrano");
		
		return null;
	}
	
	public String selectTargetCard(long playerId) {
		String response;
		sendMessage(InteractionType.DESTROYCARD, playerId);
		
		//Interaction testInteraction = new Interaction(playerId, InteractionType.DESTROYCARD, -1);
		//sendMessage(testInteraction, null);
		
		Interaction interaction = waitForInteraction(InteractionType.DESTROYCARD, playerId);
		
		// if(wynik=="R") { return "reka"; }		
		// if(wynik=="B") { return "bron"; }	
		// if(wynik=="D") { return "dodatek"; }	
		switch(interaction.getSelection()) {
			case 1: response = "reka"; break;
			case 2: response = "bron"; break;
			case 3: response = "dodatek"; break;
			default: response = selectTargetCard(playerId); break;
		}
		
		return response;
	}
	
	public Player selectTargetPlayer(Player activePlayer, List<Player> players) {
		long playerId = activePlayer.getId();
		//
		players.forEach((player) -> {
			if (activePlayer != player) {
				String playerString = player.getId() + " " + player.getNickname();
				if(player.getHand().isEmpty()) {
					playerString += " no cards!";
				}
				
				System.out.println(playerString);
			}
		});
		//
		
		sendMessage(InteractionType.TARGETSELECTION, playerId);
		
		//
		//Interaction testInteraction = new Interaction(playerId, InteractionType.TARGETSELECTION, -1);
		//sendMessage(testInteraction, null);
		//
		
		Interaction interaction = waitForInteraction(InteractionType.TARGETSELECTION, playerId);
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
	
	private boolean sendMessage(Object object, Long playerId) {
		byte[] msg;
		String url;
		if (playerId != null) {
			url = gameUrl + "/" + playerId;
		} else{
			url = gameUrl;
		}
		
		try {
			msg = objectMapper.writeValueAsBytes(object);
			stompSession.send(url, msg);
			//System.out.println("Wysłano: " + url);
		} catch(JsonProcessingException ex) {
			Logger.getLogger(Interactions.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
		
		return true;
	}
	
	public boolean useCounterCard(Player activePlayer, String cardName, String source) {
		boolean response = true;
		long playerId = activePlayer.getId();
		Interaction interaction = new Interaction(playerId, cardName, source, InteractionType.USECOUNTERCARD);
		sendMessage(interaction, playerId);
		//sendMessage(interaction, null);
		System.out.println(interaction.getCounterCard() + " " + interaction.getSource());
		interaction = waitForInteraction(InteractionType.USECOUNTERCARD, playerId);
		if(interaction.getSelection() == 0) {
			response = false;
		}
		
		return response;
	}
	
	private Interaction waitForInteraction(InteractionType type, long playerId) {
		Interaction interaction = null;
		
		System.out.println("Awaiting!");
		while(interaction == null) {
			try {
				TimeUnit.SECONDS.sleep(1);
				//if (interactions.isEmpty()) System.out.println("Size: " + interactions.size());
				interaction = getMessage(type, playerId);
			} catch (InterruptedException ex) {
				Logger.getLogger(Interactions.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		
		//System.out.println("Out of the loop");
		return interaction;
	}
}
