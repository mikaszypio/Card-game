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

	private Deck deck;
	private List<Gracz> gracze;
	private List<Gracz> martwi;
	private int ileGraczy;
	private int aktualny;
	private long id;
	
	@Autowired
	private IRoomService roomService;

	private StompSession session;
	private Gson gson;
	
	public Game(List<Gracz> gracze, long id) {
		this.id = id;
		this.gracze = gracze;
		deck = new Deck();
		
		try {
			session = createStompSession(id);
		} catch(InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
		gson = new Gson();
		
	}
	
	@Override
	public void run() {
		martwi = new ArrayList<>();
		ileGraczy = gracze.size();
		System.out.print(ileGraczy);
		aktualny=0;	

		for(int x=0; x<ileGraczy; x++) {
			Gracz g=gracze.get(0);
			//tu powinno wys�a� list� "gracze" do poszczeg�lnego klienta-tego będącego aktualnie graczem g
			gracze.remove(g);
			gracze.add(g);
		}
		
		List<Postac> postacie = listaPostaci();
		for(Gracz g : gracze) {
			Random rand = new Random();
			Postac wybrana = postacie.get(rand.nextInt(postacie.size()));
			postacie.remove(wybrana);
			g.ustawPostac(wybrana);
		}
		
		List<String> role = new ArrayList<>();
		boolean gramy=true;
		role.add("szeryf");
		role.add("renegat");
		role.add("bandyta");
		switch(ileGraczy) {
			case 4:
				role.add("bandyta");
				break;
			case 5:
				role.add("bandyta");
				role.add("zast");
				break;
			case 6:
				role.add("bandyta");
				role.add("bandyta");
				role.add("zast");
				break;
			case 7:
				role.add("bandyta");
				role.add("bandyta");
				role.add("zast");
				role.add("zast");
				break;
			default:
				gramy=false;
				System.out.print("Zła ilość graczy");
				break;
		}
		if(gramy==true) {
			for(Gracz g : gracze) {
				Random rand = new Random();
				String s = role.get(rand.nextInt(role.size()));
				role.remove(s);
				switch(s) {
					case "szeryf":
						g.ustawRole(1);
						break;
					case "bandyta":
						g.ustawRole(4);
						break;
					case "renegat":
						g.ustawRole(3);
						break;
					case "zast":
						g.ustawRole(2);
						break;
				}
			}
		}
		
		while(gramy==true) {
			Gracz tmp = gracze.get(aktualny);
			tura(tmp);
			if(aktualny<(ileGraczy-1)) {
				aktualny++;
			}else {
				aktualny=0;
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
	
	public List<Gracz> dajGraczy() {
		return gracze;
	}
	
	public Gracz dajAktualnegoGracza() {
		return gracze.get(aktualny);
	}
	
	public Gracz dajNastepnegoGracza() {
		int x = aktualny+1;
		if(x==ileGraczy) {x=0;}
		return gracze.get(x);
	}
	
	public int dajNumerGracza(Gracz g) {
		for(int x=0; x<gracze.size(); x++) {
			if(gracze.get(x)==g) {
				//gracze.indexOf(g);
				return x;
			}
		}
		return -1;
	}
	
	public int dajIleGraczy() {
		return ileGraczy;
	}
	public int dajNumerAktualnegoGracza() {
		return aktualny; 
	}
	
	public void tura(Gracz g){
		for(Gracz gracz : gracze) {
			PartialCard topRejectedCard = null;
			if (!deck.withoutRejectedCards()) {
				topRejectedCard = new PartialCard(deck.getRejectedCard(false));
			}
			
			GameboardViewModel viewModel = new GameboardViewModel(gracze, aktualny,
				(long) gracz.dajId(), deck.getRejectedCardsSize(), topRejectedCard);
			
			try {
				byte[] msg = gson.toJson(viewModel).getBytes(StandardCharsets.UTF_8);
				session.send("/app/activegames/"+(long)id+"/"+(long)gracz.dajId(), msg);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		g.ustawStrzelanie(false);
		
		if(g.gotDynamite()) {
			boolean boom = g.sprawdzDynamit(deck);
			if(!boom) {
				Gracz next = dajNastepnegoGracza();
				next.dostanDynamit();
			} else {
				if (g.dajHp() < 1) {
					zgon(g);
				}
			}
		}
		
		boolean isFree = true;
		if(g.inPrison()) {
			isFree = g.sprawdzWiezienie(deck);
		}
		
		//boolean czy = g.sprawdzWiezienie();
		//if(czy == true) {
		if(isFree) {
			g.wezKarty(deck, gracze);
			boolean dalej = true;
			while(dalej==true) {
				Card k = kontakt.wybiezKarte(gracze.get(aktualny).dajReke());
				if(k==null) {
					//prymitywna wersja sygna�u zako�czenia tury
					dalej=false;
				} else {
					boolean czyWyszlo = k.zagraj(deck, gracze, g);
					// Sprawdź zgony
					if(czyWyszlo==true) {
						gracze.get(aktualny).zReki(k);
						//odzuc(k);
						deck.rejectCard(k);
						checkDeaths();
					}
				}
			}
			
			g.odzucKarty(deck);
		}
	}
	
	private void checkDeaths() {
		for(Gracz player : gracze) {
			if(player.dajHp() < 1) {
				zgon(player);
			}
		}
	}
	
	public void zgon(Gracz g) {
		Gracz akt = dajAktualnegoGracza();
		gracze.remove(g);
		ileGraczy--;
		aktualny = dajNumerGracza(akt);
		if(g.dajBron()!=null) {
			g.doReki(g.dajBron());
			g.ustawBron(null);
		}
		if(g.dajDodatek()!=null) {
			g.doReki(g.dajDodatek());
			g.ustawDodatek(null);
		}
		Gracz sep = null;
		for(Gracz zywy : gracze) {
			if(zywy.dajPostac().dajNazwe().equals("Sam Sep")) {
				sep=zywy;
			}
		}
		if(sep==null) {
			for(Card k : g.dajReke()) {
				//odzuc(k);
				deck.rejectCard(k);
				g.zReki(k);
			}
		}else {
			for(Card k : g.dajReke()) {
				sep.doReki(k);
				g.zReki(k);
			}
		}
		martwi.add(g);
		sprawdzKoniec();
	}	
	
	public void sprawdzKoniec() {
		int zlo = 0;
		boolean szeryf = false;
		for(Gracz g : gracze) {
			switch(g.dajRole()) {
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
		if(szeryf==false) {
			if(gracze.size()==1 && gracze.get(0).dajRole()==3) {
				zakoncz(3);
			}else {
				zakoncz(4);
			}		
		}
		if(zlo==0) {
			zakoncz(1);
		}
		
	}	
	
	public void zakoncz(int kto) {
		for(Gracz g : martwi) {
			gracze.add(g);
		}
		List<Gracz> wygrali = new ArrayList<>();
		List<Gracz> przegrali = new ArrayList<>();
		if(kto==1) {
			for(Gracz g : gracze) {
				if(g.dajRole()<3) {
					wygrali.add(g);
				}else {
					przegrali.add(g);
				}
			}
		}else {
			for(Gracz g : gracze) {
				if(g.dajRole()==kto) {
					wygrali.add(g);
				}else {
					przegrali.add(g);
				}
			}
		}
		//tutaj musi by� jaki� eksport list wygrali i przegrali do systemu rankingowego
		
		//System.exit(0);
	}
	
	public List<Postac> listaPostaci(){
		List<Postac> lista = new ArrayList<>();
		
		lista.add(new Postac(1, 4, "Bart Cassady"));  //done
		lista.add(new Postac(2, 4, "Black Jack"));  //done
		lista.add(new Postac(3, 4, "Calamity Janet"));  //done
		//lista.add(new Postac(4, 3, "El Gringo", this));  //gdy oberwie, zabiera kart� z �apy tego, co go zrani� (nie dzia�a przy wybuchu dynamitu)
		lista.add(new Postac(5, 4, "Jesse Jones"));  //done
		lista.add(new Postac(6, 4, "Jourdonnais"));  //done
		lista.add(new Postac(7, 4, "Kit Carlson"));  //done
		//lista.add(new Postac(8, 4, "Lusky Duke", this));  //sprawdza pokera dwa razy
		lista.add(new Postac(9, 3, "Paul Regret"));  //done
		lista.add(new Postac(10, 4, "Pedro Ramirez"));  //done
		lista.add(new Postac(11, 4, "Rose Doolan"));  //done
		//lista.add(new Postac(12, 4, "Sid Ketchum", this));  //W DOWOLNYM MOMENCIE mo�e spali� dwie karty z �apy by wylezy� 1hp.
		lista.add(new Postac(13, 4, "Slab Zabojca"));  //done
		//tmp = new Postac(14, 4, "Suzy Lafayette", this));  //gdy ma pust� r�k�, dobieta kart� z talii
		lista.add(new Postac(15, 4, "Sam Sep"));  //done
		lista.add(new Postac(16, 4, "Willy the Kid"));  //done
		
		return lista;
	}
}
