package cardgame.game;

import cardgame.controllers.GameController;
import cardgame.game.model.cards.bang;
import cardgame.game.model.cards.dylizans;
import cardgame.game.model.cards.dynamit;
import cardgame.game.model.cards.Equipment;
import cardgame.game.model.cards.gatling;
import cardgame.game.model.cards.indianie;
import cardgame.game.model.cards.Card;
import cardgame.game.model.cards.kasia;
import cardgame.game.model.cards.panika;
import cardgame.game.model.cards.piwko;
import cardgame.game.model.cards.pojedynek;
import cardgame.game.model.cards.Postac;
import cardgame.game.model.cards.pudlo;
import cardgame.game.model.cards.salon;
import cardgame.game.model.cards.sklep;
import cardgame.game.model.cards.welsfargo;
import cardgame.game.model.cards.wiezienie;
import cardgame.game.model.Gracz;
import cardgame.services.IRoomService;
import cardgame.viewmodel.GameboardViewModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;

public class Gra extends Thread {

	private List<Card> talia;
	private Card szczyt;
	private List<Card> cmentaz;
	private List<Gracz> gracze;
	private List<Gracz> martwi;
	private int ileGraczy;
	private int aktualny;
	private Long id;
	
	@Autowired
	IRoomService roomService;
	
	@Autowired
	GameController controller;
	
	public Gra(List<Gracz> gracze) {
		this.gracze = gracze;
		for(Gracz g : gracze) {
			g.ustawGre(this);
		}
	}
	
	public void run() {
		szczyt=null;
		martwi = new ArrayList<Gracz>();
		cmentaz = new ArrayList<Card>();
		ileGraczy = gracze.size();
		aktualny=0;	

		for(int x=0; x<ileGraczy; x++) {
			Gracz g=gracze.get(0);
			//tu powinno wys�a� list� "gracze" do poszczeg�lnego klienta-tego będącego aktualnie graczem g
			gracze.remove(g);
			gracze.add(g);
		}
		
		stworzTalie();
		List<Postac> postacie = listaPostaci();
		for(Gracz g : gracze) {
			Random rand = new Random();
			Postac wybrana = postacie.get(rand.nextInt(postacie.size()));
			postacie.remove(wybrana);
			g.ustawPostac(wybrana);
		}
		
		List<String> role = new ArrayList<String>();
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
				System.out.print("Z�a ilo�� graczy");
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
	
	public void naSzczyt(Card k) {
		szczyt=k;
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
			GameboardViewModel viewModel = new GameboardViewModel(gracze, aktualny, gracze.indexOf(g));
			try {
				controller.sendViewModel(ileGraczy, ileGraczy, viewModel);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		g.ustawStrzelanie(false);
		g.sprawdzDynamit();
		boolean czy = g.sprawdzWiezienie();
		if(czy==true) {
			g.wezKarty();
			boolean dalej = true;
			while(dalej==true) {
				Card k = kontakt.wybiezKarte(gracze.get(aktualny).dajReke());
				if(k==null) {
					//prymitywna wersja sygna�u zako�czenia tury
					dalej=false;
				}else {
					boolean czyWyszlo = k.zagraj();
					if(czyWyszlo==true) {
						gracze.get(aktualny).zReki(k);
						odzuc(k);
					}
				}
			}
			g.odzucKarty();
		}
	}
	
	//wyci�ga i zwraca kart� z talii
	public Card dobiez() {
		Card wynik;
		if(szczyt==null) {
			if(talia.size()==0) {
				talia=cmentaz;
				cmentaz=new ArrayList<Card>();
			}
			Random rand = new Random();
			wynik = talia.get(rand.nextInt(talia.size()));
			talia.remove(wynik);
		}else {
			wynik = szczyt;
			szczyt=null;
		}
		return wynik;
	}
	
	public Card dobiezCmentaz() {
		if(cmentaz.size()==0) {
			return null;
		}else {
			int numer=cmentaz.size();
			numer--;
			Card wynik = cmentaz.get(numer);
			cmentaz.remove(wynik);
			return wynik;
		}
	}
	
	//ciepie kart� na cmentarz
	public void odzuc(Card k) {
		cmentaz.add(k);
	}
	
	//liczy odleg�o�� mi�dzy graczami
	public int policzDystans(Gracz jeden, Gracz dwa) {
		int pierwszy = dajNumerGracza(jeden);
		int drugi = dajNumerGracza(dwa);
		if(pierwszy ==-1 || drugi==-1) {
			System.out.print("liczenie dystansu-przysz�y z�e dane");
			return 0;
		}else {
			if(pierwszy>drugi) {
				int tmp = pierwszy;
				pierwszy=drugi;
				drugi=tmp;
			}
			int prosty = drugi-pierwszy;
			int skomplikowany = pierwszy + (ileGraczy-drugi);
			if(prosty>skomplikowany) {
				return skomplikowany; 
			}else {
				return prosty;
			}
		}		
	}
	
	//te dwie funckje sprawdzaj� pokera (efekt bary�ki, dynamitu itd). w��cznie z doci�gni�ciem karty i wyrzuceniem jej
	public boolean poker(String kolor) {
		Card kart = dobiez();
		boolean wynik;
		if(kolor==kart.dajKolor()) {
			wynik=true;
		}else {
			wynik=false;
		}
		odzuc(kart);
		return wynik;
	}
	
	public boolean poker(String kolor, int min, int max) {
		Card kart = dobiez();
		boolean wynik;
		if(kolor==kart.dajKolor() && min>=kart.dajNumer() && max<=kart.dajNumer()) {
			wynik=true;
		}else {
			wynik=false;
		}
		odzuc(kart);
		return wynik;
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
			if(zywy.dajPostac().dajNazwe()=="Sam Sep") {
				sep=zywy;
			}
		}
		if(sep==null) {
			for(Card k : g.dajReke()) {
				odzuc(k);
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
		List<Gracz> wygrali = new ArrayList<Gracz>();
		List<Gracz> przegrali = new ArrayList<Gracz>();
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
	
	public void stworzTalie() {
		talia = new ArrayList<Card>();
		Card tmp;
				
		tmp = new Equipment(1, "Volcanic", 10, "pik", true, 1, 0, false, true, this);
		talia.add(tmp);
		tmp = new Equipment(2, "Volcanic", 10, "trefl", true, 1, 0, false, true, this);
		talia.add(tmp);
		tmp = new Equipment(3, "Schofield", 13, "pik", true, 2, 0, false, false, this);
		talia.add(tmp);
		tmp = new Equipment(4, "Schofield", 11, "trefl", true, 2, 0, false, false, this);
		talia.add(tmp);
		tmp = new Equipment(5, "Schofield", 12, "trefl", true, 2, 0, false, false, this);
		talia.add(tmp);
		tmp = new Equipment(6, "Remington", 13, "trefl", true, 3, 0, false, false, this);
		talia.add(tmp);
		tmp = new Equipment(7, "Rev Carabine", 14, "trefl", true, 4, 0, false, false, this);
		talia.add(tmp);
		tmp = new Equipment(8, "Winchester", 8, "pik", true, 5, 0, false, false, this);
		talia.add(tmp);
		tmp = new Equipment(9, "Luneta", 14, "pik", false, 1, 0, false, false, this);
		talia.add(tmp);
		tmp = new Equipment(10, "Barylka", 14, "pik", false, 0, 0, true, false, this);
		talia.add(tmp);
		tmp = new Equipment(11, "Barylka", 12, "pik", false, 0, 0, true, false, this);
		talia.add(tmp);
		tmp = new Equipment(12, "Mustang", 8, "kier", false, 0, 1, false, false, this);
		talia.add(tmp);
		tmp = new Equipment(13, "Mustang", 9, "kier", false, 0, 1, false, false, this);
		talia.add(tmp);
		tmp = new piwko(14, "Piwko", 6, "kier", this);
		talia.add(tmp);
		tmp = new piwko(15, "Piwko", 7, "kier", this);
		talia.add(tmp);
		tmp = new piwko(16, "Piwko", 8, "kier", this);
		talia.add(tmp);
		tmp = new piwko(17, "Piwko", 9, "kier", this);
		talia.add(tmp);
		tmp = new piwko(18, "Piwko", 10, "kier", this);
		talia.add(tmp);
		tmp = new piwko(19, "Piwko", 11, "kier", this);
		talia.add(tmp);
		tmp = new dynamit(20, "Dynamit", 2, "kier", this);
		talia.add(tmp);
		tmp = new bang(21, "Bang", 12, "kier", this);
		talia.add(tmp);
		tmp = new bang(22, "Bang", 13, "kier", this);
		talia.add(tmp);
		tmp = new bang(23, "Bang", 14, "kier", this);
		talia.add(tmp);
		tmp = new bang(24, "Bang", 2, "karo", this);
		talia.add(tmp);
		tmp = new bang(25, "Bang", 3, "karo", this);
		talia.add(tmp);
		tmp = new bang(26, "Bang", 4, "karo", this);
		talia.add(tmp);
		tmp = new bang(27, "Bang", 5, "karo", this);
		talia.add(tmp);
		tmp = new bang(28, "Bang", 6, "karo", this);
		talia.add(tmp);
		tmp = new bang(29, "Bang", 7, "karo", this);
		talia.add(tmp);
		tmp = new bang(30, "Bang", 8, "karo", this);
		talia.add(tmp);
		tmp = new bang(31, "Bang", 9, "karo", this);
		talia.add(tmp);
		tmp = new bang(32, "Bang", 10, "karo", this);
		talia.add(tmp);
		tmp = new bang(33, "Bang", 11, "karo", this);
		talia.add(tmp);
		tmp = new bang(34, "Bang", 12, "karo", this);
		talia.add(tmp);
		tmp = new bang(35, "Bang", 13, "karo", this);
		talia.add(tmp);
		tmp = new bang(36, "Bang", 14, "karo", this);
		talia.add(tmp);
		tmp = new bang(37, "Bang", 14, "pik", this);
		talia.add(tmp);
		tmp = new bang(38, "Bang", 2, "trefl", this);
		talia.add(tmp);
		tmp = new bang(39, "Bang", 3, "trefl", this);
		talia.add(tmp);
		tmp = new bang(40, "Bang", 4, "trefl", this);
		talia.add(tmp);
		tmp = new bang(41, "Bang", 5, "trefl", this);
		talia.add(tmp);
		tmp = new bang(42, "Bang", 6, "trefl", this);
		talia.add(tmp);
		tmp = new bang(43, "Bang", 7, "trefl", this);
		talia.add(tmp);
		tmp = new bang(44, "Bang", 8, "trefl", this);
		talia.add(tmp);
		tmp = new bang(45, "Bang", 9, "trefl", this);
		talia.add(tmp);
		tmp = new welsfargo(46, "Wells Fargo", 3, "kier", this);
		talia.add(tmp);
		tmp = new dylizans(47, "Dylizans", 9, "pik", this);
		talia.add(tmp);
		tmp = new dylizans(48, "Dylizans", 9, "pik", this);
		talia.add(tmp);
		tmp = new wiezienie(49, "Wiezienie", 4, "kier", this);
		talia.add(tmp);
		tmp = new wiezienie(50, "Wiezienie", 11, "pik", this);
		talia.add(tmp);
		tmp = new wiezienie(51, "Wiezienie", 10, "pik", this);
		talia.add(tmp);
		tmp = new salon(52, "Saloon", 5, "kier", this);
		talia.add(tmp);
		tmp = new gatling(53, "Gatling", 10, "kier", this);
		talia.add(tmp);
		tmp = new pudlo(54, "Pudlo", 2, "pik", this);
		talia.add(tmp);
		tmp = new pudlo(55, "Pudlo", 3, "pik", this);
		talia.add(tmp);
		tmp = new pudlo(56, "Pudlo", 4, "pik", this);
		talia.add(tmp);
		tmp = new pudlo(57, "Pudlo", 5, "pik", this);
		talia.add(tmp);
		tmp = new pudlo(58, "Pudlo", 6, "pik", this);
		talia.add(tmp);
		tmp = new pudlo(59, "Pudlo", 7, "pik", this);
		talia.add(tmp);
		tmp = new pudlo(60, "Pudlo", 8, "pik", this);
		talia.add(tmp);
		tmp = new pudlo(61, "Pudlo", 10, "trefl", this);
		talia.add(tmp);
		tmp = new pudlo(62, "Pudlo", 11, "trefl", this);
		talia.add(tmp);
		tmp = new pudlo(63, "Pudlo", 12, "trefl", this);
		talia.add(tmp);
		tmp = new pudlo(64, "Pudlo", 13, "trefl", this);
		talia.add(tmp);
		tmp = new pudlo(65, "Pudlo", 14, "trefl", this);
		talia.add(tmp);
		tmp = new kasia(66, "Kasia Balou", 13, "kier", this);
		talia.add(tmp);
		tmp = new kasia(67, "Kasia Balou", 9, "karo", this);
		talia.add(tmp);
		tmp = new kasia(68, "Kasia Balou", 10, "karo", this);
		talia.add(tmp);
		tmp = new kasia(69, "Kasia Balou", 11, "karo", this);
		talia.add(tmp);
		tmp = new panika(70, "Panika", 8, "karo", this);
		talia.add(tmp);
		tmp = new panika(71, "Panika", 11, "kier", this);
		talia.add(tmp);
		tmp = new panika(72, "Panika", 12, "kier", this);
		talia.add(tmp);	
		tmp = new panika(73, "Panika", 14, "kier", this);
		talia.add(tmp);
		tmp = new sklep(74, "Sklep", 12, "pik", this);
		talia.add(tmp);	
		tmp = new sklep(75, "Sklep", 9, "trefl", this);
		talia.add(tmp);
		tmp = new pojedynek(76, "Pojedynek", 12, "karo", this);
		talia.add(tmp);
		tmp = new pojedynek(77, "Pojedynek", 11, "pik", this);
		talia.add(tmp);	
		tmp = new pojedynek(78, "Pojedynek", 8, "trefl", this);
		talia.add(tmp);
		tmp = new indianie(79, "Indianie", 13, "karo", this);
		talia.add(tmp);
		tmp = new indianie(80, "Indianie", 14, "karo", this);
		talia.add(tmp);
	}
	
	public List<Postac> listaPostaci(){
		List<Postac> lista = new ArrayList<Postac>();
		Postac tmp;
		
		tmp = new Postac(1, 4, "Bart Cassady", this);  //done
		lista.add(tmp);
		tmp = new Postac(2, 4, "Black Jack", this);  //done
		lista.add(tmp);
		tmp = new Postac(3, 4, "Calamity Janet", this);  //done
		lista.add(tmp);
		tmp = new Postac(4, 3, "El Gringo", this);  //gdy oberwie, zabiera kart� z �apy tego, co go zrani� (nie dzia�a przy wybuchu dynamitu)
		//lista.add(tmp);
		tmp = new Postac(5, 4, "Jesse Jones", this);  //done
		lista.add(tmp);
		tmp = new Postac(6, 4, "Jourdonnais", this);  //done
		lista.add(tmp);
		tmp = new Postac(7, 4, "Kit Carlson", this);  //done
		lista.add(tmp);
		tmp = new Postac(8, 4, "Lusky Duke", this);  //sprawdza pokera dwa razy
		//lista.add(tmp);
		tmp = new Postac(9, 3, "Paul Regret", this);  //done
		lista.add(tmp);
		tmp = new Postac(10, 4, "Pedro Ramirez", this);  //done
		lista.add(tmp);
		tmp = new Postac(11, 4, "Rose Doolan", this);  //done
		lista.add(tmp);
		tmp = new Postac(12, 4, "Sid Ketchum", this);  //W DOWOLNYM MOMENCIE mo�e spali� dwie karty z �apy by wylezy� 1hp.
		//lista.add(tmp);
		tmp = new Postac(13, 4, "Slab Zabojca", this);  //done
		lista.add(tmp);
		tmp = new Postac(14, 4, "Suzy Lafayette", this);  //gdy ma pust� r�k�, dobieta kart� z talii
		//lista.add(tmp);
		tmp = new Postac(15, 4, "Sam Sep", this);  //done
		lista.add(tmp);
		tmp = new Postac(16, 4, "Willy the Kid", this);  //done
		lista.add(tmp);	
		
		return lista;
	}
}
