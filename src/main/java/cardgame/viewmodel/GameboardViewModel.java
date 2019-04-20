package cardgame.viewmodel;

import cardgame.game.model.Player;
import cardgame.game.model.cards.Card;
import java.util.ArrayList;
import java.util.List;

public class GameboardViewModel {
	
	private List<PartialPlayer> players;
	private List<PartialCard> hand;
	private long turnId;
	private int stackSize;
	private PartialCard lastOfStack;
	
	public GameboardViewModel(List<Player> players, int active, long playerId, int stackSize, PartialCard lastOfStack) {
		
		this.stackSize = stackSize;
		this.lastOfStack = lastOfStack;
		
		Player receiver = null;
		for(Player player : players) {
			if ((long)player.getId() == playerId) {
				receiver = player;
				break;
			}
		}
		
		List<Player> playersBeforeReceiver = null;
		if (players.indexOf(receiver) > 0) {
			playersBeforeReceiver = players.subList(0, players.indexOf(receiver));
		}
		
		List<Player> playersAfterReceiver = players.subList(players.indexOf(receiver), players.size());
		this.players = new ArrayList<>();
		
		for(Player player : playersAfterReceiver) {
			
			PartialPlayer partial = new PartialPlayer(player);
			this.players.add(partial);
		}
		
		if (playersBeforeReceiver != null) {
			for(Player player : playersBeforeReceiver) {
			
				PartialPlayer partial = new PartialPlayer(player);
				this.players.add(partial);
			}
		}
		
		this.turnId = players.get(active).getId();
		hand = new ArrayList<>();
		for(Card card : players.get(0).getHand()) {
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
