package cardgame.game;

import cardgame.game.model.*;
import cardgame.game.model.cards.Card;
import cardgame.model.CardNotification;
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
			// e.printStackTrace();
		}
		
		objectMapper = new ObjectMapper();
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
	
	//TO DO: Move this method to handler?
	private Interaction getMessage(InteractionType type, long playerId) {
		if(!interactions.isEmpty()) {
			interactions.size();
			for(Interaction interaction : interactions) {
				//System.out.println(type + " | " + interaction.getType());
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
	
	public boolean sendNewCardsNotification(List<Card> cards, long id) {
		List<PartialCard> partialCards = new ArrayList<>();
		cards.forEach((c) -> { partialCards.add(new PartialCard(c)); });
		CardNotification notification = new CardNotification(id, partialCards);
		return sendMessage(notification, id);
	}
	
	public boolean getCardsAlternativeWay(long playerId, List<Player> players) {
		// System.out.println("\nGetting cards alternative way: (0-false)\n");
		Interaction interaction = new Interaction(playerId, InteractionType.ALTERNATIVEGET, -1);
		sendMessage(interaction, playerId);
		interaction = waitForInteraction(InteractionType.ALTERNATIVEGET, playerId);
		return interaction.getSelection() != 0;
	}
	
	public Card selectCard(List<Card> cardsInHand, long playerId) {
		//
		//System.out.println("\n");
		//System.out.println("!: " + playerId);
		//cardsInHand.forEach((card) -> {
		//	System.out.println(card.dajID() + card.dajNazwe());
		//});
		//System.out.println("!\n");
		Interaction interaction = new Interaction(playerId, InteractionType.CARDSELECTION, -1);
		sendMessage(interaction, playerId);
		interaction = waitForInteraction(InteractionType.CARDSELECTION, playerId);
		
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
	
	public String selectTargetCard(long playerId) {
		String response;
		Interaction interaction = new Interaction(playerId, InteractionType.DESTROYCARD, -1);
		sendMessage(interaction, playerId);
		interaction = waitForInteraction(InteractionType.DESTROYCARD, playerId);
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
//		players.forEach((player) -> {
//			if (activePlayer != player) {
//				String playerString = players.indexOf(player) + ". " + player.getId() + ". " + player.getNickname();
//				if(player.getHand().isEmpty()) {
//					playerString += ": no cards!";
//				}
//				if(player.getWeapon() != null) {
//					playerString += " 2. Weapon ";
//				}
//				if(player.getSupportItem()!= null) {
//					playerString += " 2. Support Item ";
//				}
//				
//				//System.out.println(playerString);
//			}
//		});
		
		Interaction interaction = new Interaction(playerId, InteractionType.TARGETSELECTION, -1);
		sendMessage(interaction, playerId);
		interaction = waitForInteraction(InteractionType.TARGETSELECTION, playerId);
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
		interaction = waitForInteraction(InteractionType.USECOUNTERCARD, playerId);
		if(interaction.getSelection() == 0) {
			response = false;
		}
		
		return response;
	}
	
	private Interaction waitForInteraction(InteractionType type, long playerId) {
		Interaction interaction = null;
		while(interaction == null) {
			try {
				TimeUnit.SECONDS.sleep(1);
				interaction = getMessage(type, playerId);
			} catch (InterruptedException ex) {
				Logger.getLogger(Interactions.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		
		return interaction;
	}
}
