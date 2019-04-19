package cardgame.controllers;

import cardgame.game.Game;
import cardgame.game.model.Player;
import cardgame.model.ChatMessage;
import cardgame.viewmodel.GameboardViewModel;
import java.util.ArrayList;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {
	
	@MessageMapping("/activegames/{gameId}/{playerId}")
	@SendTo("/game/{gameId}/{playerId}")
	public String message(@DestinationVariable int gameId, 
		@DestinationVariable int playerId,
		String inGameMessage) throws Exception {

		return inGameMessage;
	}
	
	@MessageMapping("/activegames/start")
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
	
	@SendTo("/game/{gameId}/{playerId}")
	public GameboardViewModel sendViewModel(@DestinationVariable int gameId, 
		@DestinationVariable int playerId,
		GameboardViewModel viewModel) throws Exception {

		return viewModel;
	}
}
