package cardgame.viewmodel;

import cardgame.game.model.Gracz;
import cardgame.game.model.cards.Card;
import java.util.ArrayList;
import java.util.List;

public class GameboardViewModel {
	
	private List<PartialPlayer> players;
	private List<PartialCard> hand;
	private long turnId;
	
	public GameboardViewModel(List<Gracz> players, int active, int playerIndex) {
		
		Gracz receiver = null;
		for(Gracz player : players) {
			if ((long)player.dajId() == playerIndex) {
				receiver = player;
				break;
			}
		}

		this.players = new ArrayList<>();
		this.players.add(new PartialPlayer(receiver));
		
		for(Gracz player : players) {
			
			short lastIndex = 1;
			int size = this.players.size();
			if (players.indexOf(player) < players.indexOf(receiver)) {
				int newIndex = this.players.size() - 1;
				this.players.add(newIndex, new PartialPlayer(player));
			} else if (players.indexOf(player) == players.indexOf(receiver)) {
				continue;
			} else {
				this.players.add(lastIndex, new PartialPlayer(player));
				lastIndex++;
			}
		}
		
		this.turnId = players.get(active).dajId();
		hand = new ArrayList<>();
		for(Card card : players.get(0).dajReke()) {
			hand.add(new PartialCard(card));
		}
	}
	
	public List<PartialPlayer> getPlayers() {
		return players;
	}
	
	public List<PartialCard> getHand() {
		return hand;
	}
	
	public long getTurnId() {
		return turnId;
	}
}
