package cardgame.game;

import cardgame.game.model.*;
import cardgame.game.model.cards.*;
import cardgame.services.IRoomService;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;

public class Game extends Thread {

	@Autowired
	private IRoomService roomService;
	
	private final Interactions interactions;
	
	private final long id;
	private final Deck deck;
	private final List<Player> players;
	private final List<Player> deadPlayers;
	private int activePlayerIndex;
	
	public Game(List<Player> players, long id) {
		this.id = id;
		this.players = players;
		deck = new Deck();
		
		deadPlayers = new ArrayList<>();
		activePlayerIndex = 0;
		
		interactions = new Interactions(this.id);
	}
	
	@Override
	public void run() {
		for(int x = 0; x < players.size(); x++) {
			Player player = players.get(0);
			
			//tu powinno wys�a� list� "gracze" do poszczeg�lnego klienta-tego będącego aktualnie graczem g
			
			players.remove(player);
			players.add(player);
		}
		
		List<Hero> heros = createHeros();
		for(Player player : players) {
			Random rand = new Random();
			Hero selected = heros.get(rand.nextInt(heros.size()));
			heros.remove(selected);
			player.setHero(selected);
		}
		
		List<String> roles = createRoles();
		boolean canStart = true;
		if(roles.isEmpty()) {
			canStart = false;
		}
		
		if(canStart) {
			for(Player player : players) {
				Random random = new Random();
				String role = roles.get(random.nextInt(roles.size()));
				roles.remove(role);
				switch(role) {
					case "szeryf":
						player.setRole(1);
						break;
					case "bandyta":
						player.setRole(4);
						break;
					case "renegat":
						player.setRole(3);
						break;
					case "zast":
						player.setRole(2);
						break;
				}
			}
		}
		
		while(canStart) {
			Player player = players.get(activePlayerIndex);
			playTurn(player);
			if(activePlayerIndex < (players.size() - 1)) {
				activePlayerIndex++;
			} else {
				activePlayerIndex = 0;
			}
		}
	}
	
	private Player getNextPlayer() {
		int index = (activePlayerIndex + 1) % players.size();
		return players.get(index);
	}
	
	private void playTurn(Player player){
		interactions.broadcastViewModel(players, activePlayerIndex, deck);
		player.setShotStatus(false);
		
		if(player.gotDynamite()) {
			boolean boom = player.checkDynamite(deck, interactions);
			if(!boom) {
				Player nextPlayer = getNextPlayer();
				nextPlayer.setDynamite();
			} else if(player.getHitPoints() < 1) {
				makeDead(player);
			}
		}
		
		boolean isFree = true;
		if(player.inPrison()) {
			isFree = player.checkPrison(deck);
		}
		
		//boolean czy = g.sprawdzWiezienie();
		//if(czy == true) {
		if(isFree) {
			player.getCards(deck, players, interactions);
			boolean isPlaying = true;
			while(isPlaying) {
				List<Card> cardsInHand = player.getHand();
				Card card = interactions.selectCard(cardsInHand, player.getId());
				if(card == null) {
					//prymitywna wersja sygna�u zako�czenia tury
					isPlaying=false;
				} else {
					boolean result = card.zagraj(deck, players, player, interactions);
					if(result) {
						players.get(activePlayerIndex).removeFromHand(card);
						//odzuc(k);
						deck.rejectCard(card);
						checkDeaths();
					}
				}
			}
			
			player.rejectCards(deck, interactions);
		}
	}
	
	private void checkDeaths() {
		List<Player> deadOnes = new ArrayList<>();
		for(Player player : players) {
			if(player.getHitPoints() < 1) {
				//makeDead(player);
				deadOnes.add(player);
			}
		}
		
		deadOnes.forEach((p) -> { makeDead(p); players.remove(p); });
	}
	
	private void makeDead(Player g) {
		Player akt = players.get(activePlayerIndex);
		//players.remove(g);
		activePlayerIndex = players.indexOf(akt);
		if(g.getWeapon()!=null) {
			g.addToHand(g.getWeapon());
			g.setWeapon(null);
		}
		
		if(g.getSupportItem()!=null) {
			g.addToHand(g.getSupportItem());
			g.setSupportItem(null);
		}
		
		Player sep = null;
		for(Player zywy : players) {
			if(zywy.getHero().dajNazwe().equals("Sam Sep")) {
				sep=zywy;
			}
		}
		
		if(sep == null) {
			for(Card k : g.getHand()) {
				deck.rejectCard(k);
			}
		} else {
			for(Card k : g.getHand()) {
				sep.addToHand(k);
			}
		}
		
		g.getHand().clear();
		deadPlayers.add(g);
		sprawdzKoniec();
	}	
	
	private void sprawdzKoniec() {
		int zlo = 0;
		boolean szeryf = false;
		for(Player g : players) {
			switch(g.getRole()) {
				case 1:
					szeryf = true;
					break;
				case 3:
					zlo++;
					break;
				case 4:
					zlo++;
					break;
				default:
					break;
			}
		}
		
		if(szeryf == false) {
			if(players.size() == 1 && players.get(0).getRole() == 3) {
				finalizeGame(3);
			} else {
				finalizeGame(4);
			}		
		}
		
		if(zlo == 0) {
			finalizeGame(1);
		}
		
	}	
	
	private void finalizeGame(int winningRoles) {
		for(Player player : deadPlayers) {
			players.add(player);
		}
		
		List<Player> winners = new ArrayList<>();
		List<Player> losers = new ArrayList<>();
		if(winningRoles == 1) {
			for(Player player : players) {
				if(player.getRole() < 3) {
					winners.add(player);
				} else {
					losers.add(player);
				}
			}
		} else {
			for(Player player : players) {
				if(player.getRole() == winningRoles) {
					winners.add(player);
				} else {
					losers.add(player);
				}
			}
		}
		//tutaj musi by� jaki� eksport list wygrali i przegrali do systemu rankingowego
		
		//System.exit(0);
	}
	
	private List<Hero> createHeros() {
		List<Hero> heros = new ArrayList<>();
		
		heros.add(new Hero(1, 4, "Bart Cassady"));  //done
		heros.add(new Hero(2, 4, "Black Jack"));  //done
		heros.add(new Hero(3, 4, "Calamity Janet"));  //done
		//heros.add(new Hero(4, 3, "El Gringo"));  //gdy oberwie, zabiera kart� z �apy tego, co go zrani� (nie dzia�a przy wybuchu dynamitu)
		heros.add(new Hero(5, 4, "Jesse Jones"));  //done
		heros.add(new Hero(6, 4, "Jourdonnais"));  //done
		heros.add(new Hero(7, 4, "Kit Carlson"));  //done
		//heros.add(new Hero(8, 4, "Lusky Duke"));  //sprawdza pokera dwa razy
		heros.add(new Hero(9, 3, "Paul Regret"));  //done
		heros.add(new Hero(10, 4, "Pedro Ramirez"));  //done
		heros.add(new Hero(11, 4, "Rose Doolan"));  //done
		//heros.add(new Hero(12, 4, "Sid Ketchum"));  //W DOWOLNYM MOMENCIE mo�e spali� dwie karty z �apy by wylezy� 1hp.
		heros.add(new Hero(13, 4, "Slab Zabojca"));  //done
		//heros.add(new Hero(14, 4, "Suzy Lafayette"));  //gdy ma pust� r�k�, dobieta kart� z talii
		heros.add(new Hero(15, 4, "Sam Sep"));  //done
		heros.add(new Hero(16, 4, "Willy the Kid"));  //done
		
		return heros;
	}
	
	private List<String> createRoles() {
		List<String> roles = new ArrayList<>();
		roles.add("szeryf");
		roles.add("renegat");
		roles.add("bandyta");
		switch(players.size()) {
			case 4:
				roles.add("bandyta");
				break;
			case 5:
				roles.add("bandyta");
				roles.add("zast");
				break;
			case 6:
				roles.add("bandyta");
				roles.add("bandyta");
				roles.add("zast");
				break;
			case 7:
				roles.add("bandyta");
				roles.add("bandyta");
				roles.add("zast");
				roles.add("zast");
				break;
			default:
				roles.clear();
				System.out.print("Zła ilość graczy");
				break;
		}
		
		return roles;
	}
}
