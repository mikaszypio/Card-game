package testy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//pomys³ siê zjawi³, by ka¿da gra by³a osobnym procesem. w takim przypadku ta klasa sta³a by siê klas¹ g³ówn¹ (zawiera main). tylko trza by by³o do niej dodaæ np 
//tworzenie decku i postaci. jako argument program by przyjmowa³ listê graczy. 
public class gra {

	private static List<karta> talia;
	private static karta szczyt;
	private static List<karta> cmentaz;
	private static List<gracz> gracze;
	private static List<gracz> martwi;
	private static int ileGraczy;
	private static int aktualny;
	
	public gra(List<gracz> g) {		
		gracze=g;
		szczyt=null;
		martwi = new ArrayList<gracz>();
		cmentaz = new ArrayList<karta>();
		ileGraczy = gracze.size();
		aktualny=0;
	}
	
	public static void naSzczyt(karta k) {
		szczyt=k;
	}
	
	public static List<gracz> dajGraczy() {
		return gracze;
	}
	
	public static gracz dajAktualnegoGracza() {
		return gracze.get(aktualny);
	}
	
	public static gracz dajNastepnegoGracza() {
		int x = aktualny+1;
		if(x==ileGraczy) {x=0;}
		return gracze.get(x);
	}
	
	public static int dajNumerGracza(gracz g) {
		for(int x=0; x<gracze.size(); x++) {
			if(gracze.get(x)==g) {
				return x;
			}
		}
		return -1;
	}
	
	public static int dajIleGraczy() {
		return ileGraczy;
	}
	public static int dajNumerAktualnegoGracza() {
		return aktualny; 
	}
	
	//jeœli ka¿da gra bêdzie osobnym procesem, ta funckja stanie siê mainem. tylko trza bêdzie dodaæ do niej to, co jest w konstruktorze
	public static void graj() {	
		stworzTalie();
		List<postac> postacie = listaPostaci();
		for(gracz g : gracze) {
			Random rand = new Random();
			postac wybrana = postacie.get(rand.nextInt(postacie.size()));
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
				System.out.print("Z³a iloœæ graczy");
				break;
		}
		if(gramy==true) {
			for(gracz g : gracze) {
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
			gracz tmp = gracze.get(aktualny);
			tura(tmp);
			if(aktualny<(ileGraczy-1)) {
				aktualny++;
			}else {
				aktualny=0;
			}
		}
	}
	
	public static void tura(gracz g){
		g.ustawStrzelanie(false);
		g.sprawdzDynamit();
		boolean czy = g.sprawdzWiezienie();
		if(czy==true) {
			g.wezKarty();
			boolean dalej = true;
			while(dalej==true) {
				karta k = kontakt.wybiezKarte(gracze.get(aktualny).dajReke());
				if(k==null) {
					//prymitywna wersja sygna³u zakoñczenia tury
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
	
	//wyci¹ga i zwraca kartê z talii
	public static karta dobiez() {
		karta wynik;
		if(szczyt==null) {
			if(talia.size()==0) {
				talia=cmentaz;
				cmentaz=new ArrayList<karta>();
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
	
	public static karta dobiezCmentaz() {
		if(cmentaz.size()==0) {
			return null;
		}else {
			int numer=cmentaz.size();
			numer--;
			karta wynik = cmentaz.get(numer);
			cmentaz.remove(wynik);
			return wynik;
		}
	}
	
	//ciepie kartê na cmentarz
	public static void odzuc(karta k) {
		cmentaz.add(k);
	}
	
	//liczy odleg³oœæ miêdzy graczami
	public static int policzDystans(gracz jeden, gracz dwa) {
		int pierwszy = dajNumerGracza(jeden);
		int drugi = dajNumerGracza(dwa);
		if(pierwszy ==-1 || drugi==-1) {
			System.out.print("liczenie dystansu-przysz³y z³e dane");
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
	
	//te dwie funckje sprawdzaj¹ pokera (efekt bary³ki, dynamitu itd). w³¹cznie z doci¹gniêciem karty i wyrzuceniem jej
	public static boolean poker(String kolor) {
		karta kart = dobiez();
		boolean wynik;
		if(kolor==kart.dajKolor()) {
			wynik=true;
		}else {
			wynik=false;
		}
		odzuc(kart);
		return wynik;
	}
	
	public static boolean poker(String kolor, int min, int max) {
		karta kart = dobiez();
		boolean wynik;
		if(kolor==kart.dajKolor() && min>=kart.dajNumer() && max<=kart.dajNumer()) {
			wynik=true;
		}else {
			wynik=false;
		}
		odzuc(kart);
		return wynik;
	}
	
	public static void zgon(gracz g) {
		gracz akt = dajAktualnegoGracza();
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
		gracz sep = null;
		for(gracz zywy : gracze) {
			if(zywy.dajPostac().dajNazwe()=="Sam Sêp") {
				sep=zywy;
			}
		}
		if(sep==null) {
			for(karta k : g.dajReke()) {
				odzuc(k);
				g.zReki(k);
			}
		}else {
			for(karta k : g.dajReke()) {
				sep.doReki(k);
				g.zReki(k);
			}
		}
	}	
	
	
	public static void stworzTalie() {
		talia = new ArrayList<karta>();
		karta tmp;
				
		tmp = new eq(1, "Volcanic", 10, "pik", true, 1, 0, false, true);
		talia.add(tmp);
		tmp = new eq(2, "Volcanic", 10, "trefl", true, 1, 0, false, true);
		talia.add(tmp);
		tmp = new eq(3, "Schofield", 13, "pik", true, 2, 0, false, false);
		talia.add(tmp);
		tmp = new eq(4, "Schofield", 11, "trefl", true, 2, 0, false, false);
		talia.add(tmp);
		tmp = new eq(5, "Schofield", 12, "trefl", true, 2, 0, false, false);
		talia.add(tmp);
		tmp = new eq(6, "Remington", 13, "trefl", true, 3, 0, false, false);
		talia.add(tmp);
		tmp = new eq(7, "Rev Carabine", 14, "trefl", true, 4, 0, false, false);
		talia.add(tmp);
		tmp = new eq(8, "Winchester", 8, "pik", true, 5, 0, false, false);
		talia.add(tmp);
		tmp = new eq(9, "Luneta", 14, "pik", false, 1, 0, false, false);
		talia.add(tmp);
		tmp = new eq(10, "Bary³ka", 14, "pik", false, 0, 0, true, false);
		talia.add(tmp);
		tmp = new eq(11, "Bary³ka", 12, "pik", false, 0, 0, true, false);
		talia.add(tmp);
		tmp = new eq(12, "Mustang", 8, "kier", false, 0, 1, false, false);
		talia.add(tmp);
		tmp = new eq(13, "Mustang", 9, "kier", false, 0, 1, false, false);
		talia.add(tmp);
		tmp = new piwko(14, "Piwko", 6, "kier");
		talia.add(tmp);
		tmp = new piwko(15, "Piwko", 7, "kier");
		talia.add(tmp);
		tmp = new piwko(16, "Piwko", 8, "kier");
		talia.add(tmp);
		tmp = new piwko(17, "Piwko", 9, "kier");
		talia.add(tmp);
		tmp = new piwko(18, "Piwko", 10, "kier");
		talia.add(tmp);
		tmp = new piwko(19, "Piwko", 11, "kier");
		talia.add(tmp);
		tmp = new dynamit(20, "Dynamit", 2, "kier");
		talia.add(tmp);
		tmp = new bang(21, "Bang", 12, "kier");
		talia.add(tmp);
		tmp = new bang(22, "Bang", 13, "kier");
		talia.add(tmp);
		tmp = new bang(23, "Bang", 14, "kier");
		talia.add(tmp);
		tmp = new bang(24, "Bang", 2, "karo");
		talia.add(tmp);
		tmp = new bang(25, "Bang", 3, "karo");
		talia.add(tmp);
		tmp = new bang(26, "Bang", 4, "karo");
		talia.add(tmp);
		tmp = new bang(27, "Bang", 5, "karo");
		talia.add(tmp);
		tmp = new bang(28, "Bang", 6, "karo");
		talia.add(tmp);
		tmp = new bang(29, "Bang", 7, "karo");
		talia.add(tmp);
		tmp = new bang(30, "Bang", 8, "karo");
		talia.add(tmp);
		tmp = new bang(31, "Bang", 9, "karo");
		talia.add(tmp);
		tmp = new bang(32, "Bang", 10, "karo");
		talia.add(tmp);
		tmp = new bang(33, "Bang", 11, "karo");
		talia.add(tmp);
		tmp = new bang(34, "Bang", 12, "karo");
		talia.add(tmp);
		tmp = new bang(35, "Bang", 13, "karo");
		talia.add(tmp);
		tmp = new bang(36, "Bang", 14, "karo");
		talia.add(tmp);
		tmp = new bang(37, "Bang", 14, "pik");
		talia.add(tmp);
		tmp = new bang(38, "Bang", 2, "trefl");
		talia.add(tmp);
		tmp = new bang(39, "Bang", 3, "trefl");
		talia.add(tmp);
		tmp = new bang(40, "Bang", 4, "trefl");
		talia.add(tmp);
		tmp = new bang(41, "Bang", 5, "trefl");
		talia.add(tmp);
		tmp = new bang(42, "Bang", 6, "trefl");
		talia.add(tmp);
		tmp = new bang(43, "Bang", 7, "trefl");
		talia.add(tmp);
		tmp = new bang(44, "Bang", 8, "trefl");
		talia.add(tmp);
		tmp = new bang(45, "Bang", 9, "trefl");
		talia.add(tmp);
		tmp = new welsfargo(46, "Wells Fargo", 3, "kier");
		talia.add(tmp);
		tmp = new dylizans(47, "Dyli¿ans", 9, "pik");
		talia.add(tmp);
		tmp = new dylizans(48, "Dyli¿ans", 9, "pik");
		talia.add(tmp);
		tmp = new wiezienie(49, "Wiêzienie", 4, "kier");
		talia.add(tmp);
		tmp = new wiezienie(50, "Wiêzienie", 11, "pik");
		talia.add(tmp);
		tmp = new wiezienie(51, "Wiêzienie", 10, "pik");
		talia.add(tmp);
		tmp = new salon(52, "Saloon", 5, "kier");
		talia.add(tmp);
		tmp = new gatling(53, "Gatling", 10, "kier");
		talia.add(tmp);
		tmp = new pudlo(54, "Pud³o", 2, "pik");
		talia.add(tmp);
		tmp = new pudlo(55, "Pud³o", 3, "pik");
		talia.add(tmp);
		tmp = new pudlo(56, "Pud³o", 4, "pik");
		talia.add(tmp);
		tmp = new pudlo(57, "Pud³o", 5, "pik");
		talia.add(tmp);
		tmp = new pudlo(58, "Pud³o", 6, "pik");
		talia.add(tmp);
		tmp = new pudlo(59, "Pud³o", 7, "pik");
		talia.add(tmp);
		tmp = new pudlo(60, "Pud³o", 8, "pik");
		talia.add(tmp);
		tmp = new pudlo(61, "Pud³o", 10, "trefl");
		talia.add(tmp);
		tmp = new pudlo(62, "Pud³o", 11, "trefl");
		talia.add(tmp);
		tmp = new pudlo(63, "Pud³o", 12, "trefl");
		talia.add(tmp);
		tmp = new pudlo(64, "Pud³o", 13, "trefl");
		talia.add(tmp);
		tmp = new pudlo(65, "Pud³o", 14, "trefl");
		talia.add(tmp);
		tmp = new kasia(66, "Kasia Balou", 13, "kier");
		talia.add(tmp);
		tmp = new kasia(67, "Kasia Balou", 9, "karo");
		talia.add(tmp);
		tmp = new kasia(68, "Kasia Balou", 10, "karo");
		talia.add(tmp);
		tmp = new kasia(69, "Kasia Balou", 11, "karo");
		talia.add(tmp);
		tmp = new panika(70, "Panika", 8, "karo");
		talia.add(tmp);
		tmp = new panika(71, "Panika", 11, "kier");
		talia.add(tmp);
		tmp = new panika(72, "Panika", 12, "kier");
		talia.add(tmp);	
		tmp = new panika(73, "Panika", 14, "kier");
		talia.add(tmp);
		tmp = new sklep(74, "Sklep", 12, "pik");
		talia.add(tmp);	
		tmp = new sklep(75, "Sklep", 9, "trefl");
		talia.add(tmp);
		tmp = new pojedynek(76, "Pojedynek", 12, "karo");
		talia.add(tmp);
		tmp = new pojedynek(77, "Pojedynek", 11, "pik");
		talia.add(tmp);	
		tmp = new pojedynek(78, "Pojedynek", 8, "trefl");
		talia.add(tmp);
		tmp = new indianie(79, "Indianie", 13, "karo");
		talia.add(tmp);
		tmp = new indianie(80, "Indianie", 14, "karo");
		talia.add(tmp);
	}
	
	public static List<postac> listaPostaci(){
		List<postac> lista = new ArrayList<postac>();
		postac tmp;
		
		tmp = new postac(1, 4, "Bart Cassady");  //done
		lista.add(tmp);
		tmp = new postac(2, 4, "Black Jack");  //done
		lista.add(tmp);
		tmp = new postac(3, 4, "Calamity Janet");  //done
		lista.add(tmp);
		tmp = new postac(4, 3, "El Gringo");  //gdy oberwie, zabiera kartê z ³apy tego, co go zrani³ (nie dzia³a przy wybuchu dynamitu)
		lista.add(tmp);
		tmp = new postac(5, 4, "Jesse Jones");  //done
		lista.add(tmp);
		tmp = new postac(6, 4, "Jourdonnais");  //done
		lista.add(tmp);
		tmp = new postac(7, 4, "Kit Carlson");  //done
		lista.add(tmp);
		tmp = new postac(8, 4, "Lusky Duke");  //sprawdza pokera dwa razy
		lista.add(tmp);
		tmp = new postac(9, 3, "Paul Regret");  //done
		lista.add(tmp);
		tmp = new postac(10, 4, "Pedro Ramirez");  //done
		lista.add(tmp);
		tmp = new postac(11, 4, "Rose Doolan");  //done
		lista.add(tmp);
		tmp = new postac(12, 4, "Sid Ketchum");  //W DOWOLNYM MOMENCIE mo¿e spaliæ dwie karty z ³apy by wylezyæ 1hp.
		lista.add(tmp);
		tmp = new postac(13, 4, "Slab Zabójca");  //done
		lista.add(tmp);
		tmp = new postac(14, 4, "Suzy Lafayette");  //gdy ma pust¹ rêkê, dobieta kartê z talii
		lista.add(tmp);
		tmp = new postac(15, 4, "Sam Sêp");  //done
		lista.add(tmp);
		tmp = new postac(16, 4, "Willy the Kid");  //done
		lista.add(tmp);	
		
		return lista;
	}
	
	
	
	
	
	//testowe - jak bêdzie siê implementowa³o finaln¹ wersjê to mo¿na olaæ
	public void napiszTalie() {
		System.out.print("\nTalia:\n");
		if(talia.size()>0) {
			for(karta k : talia) {
				k.opisz();
			}
		}else {
			System.out.print("pusto");
		}
	}
	
	public void napiszCmentaz() {
		System.out.print("\nCmentaz:\n");
		if(cmentaz.size()>0) {
			for(karta k : cmentaz) {
				k.opisz();
			}
		}else {
			System.out.print("pusto");
		}
	}
	
	public void napiszGraczy() {
		System.out.print("\nListaGraczy:\n");
		for(gracz g : gracze) {
			System.out.print("Gracz " + g.dajNick() + " ma " + g.zdrowie() + " z " + g.maxZdrowie() + "hp.\n" );
			g.opiszReke();
		}
	}
	
	public void zassaj() {
		for(gracz g : gracze) {
			g.dobiezKarte();
			g.dobiezKarte();
			g.dobiezKarte();
			g.dobiezKarte();
			g.dobiezKarte();
		}
	}
}
