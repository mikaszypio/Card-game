package cardgame.controllers;

import cardgame.game.Gra;
import cardgame.game.model.Gracz;
import cardgame.game.model.cards.Equipment;
import cardgame.game.model.cards.Postac;
import cardgame.game.model.cards.bang;
import cardgame.game.model.cards.salon;
import cardgame.model.ChatMessage;
import cardgame.viewmodel.GameboardViewModel;
import cardgame.viewmodel.PartialCard;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
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
		ChatMessage message) throws Exception {

		System.out.println("Triggered");
		Gracz player1 = new Gracz("Ja", (long) 1);
		player1.dajReke().add(new bang(21, "Bang", 12, "kier", null));
		player1.ustawBron(new Equipment(1, "Volcanic", 10, "pik", true, 1, 0, false, true, null));
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
		GameboardViewModel viewModel = new GameboardViewModel(players, active, playerId, 2, new PartialCard(new salon(52, "Saloon", 5, "kier", null)));
		String response = gson.toJson(viewModel);
		return response;
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
