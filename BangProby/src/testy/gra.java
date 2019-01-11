package testy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//pomys³ siê zjawi³, by ka¿da gra by³a osobnym procesem. w takim przypadku ta klasa sta³a by siê klas¹ g³ówn¹ (zawiera main). tylko trza by by³o do niej dodaæ np 
//tworzenie decku i postaci. jako argument program by przyjmowa³ listê graczy. 
public class gra {

	private static List<karta> talia;
	private static List<karta> cmentaz;
	private static List<gracz> gracze;
	private static int ileGraczy;
	private static int aktualny;
	
	public gra(List<gracz> g,  List<karta> t) {
		talia = t;
		gracze=g;
		cmentaz = new ArrayList<karta>();
		ileGraczy = gracze.size();
		aktualny=0;
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
		for(int x=1; x<100; x++) {
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
			//graj
			//zagranie karty zwraca boola-jeœli jest true, znaczy siê uda³o. a wiêc trzeba odrzuciæ kartê z ³apki
			g.odzucKarty();
		}
	}
	
	//wyci¹ga i zwraca kartê z talii
	public static karta dobiez() {
		if(talia.size()==0) {
			talia=cmentaz;
			cmentaz=new ArrayList<karta>();
		}
		Random rand = new Random();
		karta wynik = talia.get(rand.nextInt(talia.size()));
		talia.remove(wynik);
		return wynik;
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
