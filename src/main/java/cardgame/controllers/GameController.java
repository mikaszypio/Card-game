package cardgame.controllers;

import cardgame.game.Game;
import cardgame.game.model.Player;
import cardgame.model.ChatMessage;
import cardgame.model.Interaction;
import java.util.ArrayList;
import java.util.Scanner;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {
	
	Scanner scanner = new Scanner(System.in);
	
	// This channels should be used to game -> user forwarding
	@MessageMapping("/activegame/{gameId}/{playerId}")
	@SendTo("/game/{gameId}/{playerId}")
	public String directMessage(@DestinationVariable int gameId, 
		@DestinationVariable int playerId,
		String inGameMessage) throws Exception {

		return inGameMessage;
	}
	
	// This channels should be used to user -> game forwarding
	@MessageMapping("/activegame/{gameId}")
	@SendTo("/game/{gameId}")
	public Interaction interactionMessage(@DestinationVariable int gameId,
		Interaction interaction) throws Exception {
		int selection = -1;
//		try (Scanner scanner = new Scanner(System.in)) {
		String string = scanner.nextLine();
		selection = Integer.parseInt(string);
//		}
		
		interaction.setSelection(selection);
		System.out.println("Wybrano: " + interaction.getSelection());
		
		return interaction;
	}
	
	@MessageMapping("/game/start")
	public void start(ChatMessage message) {
		Player gracz1 = new Player("Ja", (long) 1);
		Player gracz2 = new Player("On", (long) 2);
		Player gracz3 = new Player("Ona", (long) 3);
		Player gracz4 = new Player("Ono", (long) 4);
		ArrayList<Player> list = new ArrayList<>();
		list.add(gracz1);
		list.add(gracz2);
		list.add(gracz3);
		list.add(gracz4);
		Game gra = new Game(list, 1);
		gra.start();
	}
}
