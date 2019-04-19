package cardgame.game;

import cardgame.game.model.*;
import cardgame.game.model.cards.*;
import cardgame.services.IRoomService;
import cardgame.viewmodel.GameboardViewModel;
import cardgame.viewmodel.PartialCard;
import com.google.gson.Gson;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

public class Game extends Thread {

	@Autowired
	private IRoomService roomService;
	
	private Gson gson;
	private StompSession session;
	
	private long id;
	private Deck deck;
	private List<Player> players;
	private List<Player> deadPlayers;
	private int activePlayerIndex;
	
	public Game(List<Player> players, long id) {
		this.id = id;
		this.players = players;
		deck = new Deck();
		
		deadPlayers = new ArrayList<>();
		activePlayerIndex = 0;
		
		try {
			session = createStompSession(id);
		} catch(InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
		gson = new Gson();
		
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
	
	private void broadcastViewModel() {
		for(Player player : players) {
			PartialCard topRejectedCard = null;
			if (!deck.withoutRejectedCards()) {
				topRejectedCard = new PartialCard(deck.getRejectedCard(false));
			}
			
			GameboardViewModel viewModel = new GameboardViewModel(players, activePlayerIndex,
				(long) player.getId(), deck.getRejectedCardsSize(), topRejectedCard);
			
			try {
				byte[] msg = gson.toJson(viewModel).getBytes(StandardCharsets.UTF_8);
				session.send("/app/activegames/"+(long)id+"/"+(long)player.getId(), msg);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private StompSession createStompSession(long id) throws InterruptedException, ExecutionException {
		WebSocketClient simpleWebSocketClient = new StandardWebSocketClient();
		List<Transport> transports = new ArrayList<>(1);
		transports.add(new WebSocketTransport(simpleWebSocketClient));
		SockJsClient sockJsClient = new SockJsClient(transports);
		WebSocketStompClient client = new WebSocketStompClient(sockJsClient);
		String url = "ws://localhost:8080/game-socket";
		StompSessionHandler sessionHandler = new GameStompSessionHandler(id);
		StompSession stompSession = client.connect(url, sessionHandler).get();
		return stompSession;
	}
	
	private Player getNextPlayer() {
		int index = (activePlayerIndex + 1) % players.size();
		return players.get(index);
	}
	
	private void playTurn(Player player){
		broadcastViewModel();
		player.setShotStatus(false);
		
		if(player.gotDynamite()) {
			boolean boom = player.checkDynamite(deck);
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
			player.getCards(deck, players);
			boolean isPlaying = true;
			while(isPlaying) {
				Card k = kontakt.wybiezKarte(players.get(activePlayerIndex).dajReke());
				if(k==null) {
					//prymitywna wersja sygna�u zako�czenia tury
					isPlaying=false;
				} else {
					boolean result = k.zagraj(deck, players, player);
					if(result) {
						players.get(activePlayerIndex).removeFromHand(k);
						//odzuc(k);
						deck.rejectCard(k);
						checkDeaths();
					}
				}
			}
			
			player.rejectCards(deck);
		}
	}
	
	private void checkDeaths() {
		for(Player player : players) {
			if(player.getHitPoints() < 1) {
				makeDead(player);
			}
		}
	}
	
	private void makeDead(Player g) {
		Player akt = players.get(activePlayerIndex);
		players.remove(g);
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
			for(Card k : g.dajReke()) {
				//odzuc(k);
				deck.rejectCard(k);
				g.removeFromHand(k);
			}
		} else {
			for(Card k : g.dajReke()) {
				sep.addToHand(k);
				g.removeFromHand(k);
			}
		}
		
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
