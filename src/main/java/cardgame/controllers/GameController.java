package cardgame.controllers;

import cardgame.game.model.Gracz;
import cardgame.game.model.cards.Postac;
import cardgame.game.model.cards.bang;
import cardgame.model.ChatMessage;
import cardgame.viewmodel.GameboardViewModel;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GameController {
	
	@MessageMapping("/activegames/{gameId}/{playerId}")
	@SendTo("/game/{gameId}/{playerId}")
	public String message(@DestinationVariable int gameId, 
		@DestinationVariable int playerId,
		ChatMessage message) throws Exception {

		Gracz player1 = new Gracz("Ja", (long) 1);
		player1.dajReke().add(new bang(21, "Bang", 12, "kier", null));
		Gracz player2 = new Gracz("On", (long) 2);
		Postac character1 = new Postac(1, 4, "p1", null);
		Postac character2 = new Postac(2, 4, "p2", null);
		player1.ustawPostac(character1);
		player2.ustawPostac(character2);
		List<Gracz> players = new ArrayList<>();
		players.add(player1);
		players.add(player2);
		int active = 0;
		Gson gson = new Gson();
		System.out.println("Tworzę viewmodel " + playerId);
		GameboardViewModel viewModel = new GameboardViewModel(players, active, playerId);
		System.out.println("Stworzony");
		String response = gson.toJson(viewModel);
		return response;
	}
	
	@SendTo("/game/{gameId}/{playerId}")
	public GameboardViewModel sendViewModel(@DestinationVariable int gameId, 
		@DestinationVariable int playerId,
		GameboardViewModel viewModel) throws Exception {

		return viewModel;
	}
}
