package cardgame.controllers;

import cardgame.game.Gra;
import cardgame.game.model.Gracz;
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
		Gracz gracz1 = new Gracz("Ja", (long) 1);
		Gracz gracz2 = new Gracz("On", (long) 2);
		Gracz gracz3 = new Gracz("Ona", (long) 3);
		Gracz gracz4 = new Gracz("Ono", (long) 4);
		ArrayList<Gracz> list = new ArrayList<>();
		list.add(gracz1);
		list.add(gracz2);
		list.add(gracz3);
		list.add(gracz4);
		Gra gra = new Gra(list, 1);
		gra.start();
	}
	
	@SendTo("/game/{gameId}/{playerId}")
	public GameboardViewModel sendViewModel(@DestinationVariable int gameId, 
		@DestinationVariable int playerId,
		GameboardViewModel viewModel) throws Exception {

		return viewModel;
	}
}
