package cardgame.viewmodel;

import cardgame.game.model.Gracz;
import cardgame.game.model.cards.Card;
import java.util.ArrayList;
import java.util.List;

public class GameboardViewModel {
	
	private List<PartialPlayer> players;
	private List<PartialCard> hand;
	private long turnId;
	private int stackSize;
	private PartialCard lastOfStack;
	
	public GameboardViewModel(List<Gracz> players, int active, long playerId, int stackSize, PartialCard lastOfStack) {
		
		this.stackSize = stackSize;
		this.lastOfStack = lastOfStack;
		
		Gracz receiver = null;
		for(Gracz player : players) {
			if ((long)player.dajId() == playerId) {
				receiver = player;
				break;
			}
		}
		
		List<Gracz> playersBeforeReceiver = null;
		if (players.indexOf(receiver) > 0) {
			playersBeforeReceiver = players.subList(0, players.indexOf(receiver));
		}
		
		List<Gracz> playersAfterReceiver = players.subList(players.indexOf(receiver), players.size());
		this.players = new ArrayList<>();
		
		for(Gracz player : playersAfterReceiver) {
			
			PartialPlayer partial = new PartialPlayer(player);
			this.players.add(partial);
		}
		
		if (playersBeforeReceiver != null) {
			for(Gracz player : playersBeforeReceiver) {
			
				PartialPlayer partial = new PartialPlayer(player);
				this.players.add(partial);
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
	
	public int getStackSize() {
		return stackSize;
	}
	
	public PartialCard getLastOfStack() {
		return lastOfStack;
	}
}
