package cardgame.game.model;

import cardgame.game.gra;
import cardgame.game.kontakt;
import cardgame.game.model.cards.eq;
import cardgame.game.model.cards.karta;
import cardgame.game.model.cards.postac;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class gracz {

	private postac hero;
	private String nick;
	private int ID;
	private int rola;		//1=szeryf, 2=pomocnik, 3=renegat, 4=bandyta
	private List<karta> reka;
	private int hp;
	private eq bron;
	private eq dodatek;
	private boolean strzelal;
	private boolean czyDynamit;
	private boolean czyWiezienie;

	public gracz(String a) {
		nick=a;
		reka = new ArrayList<karta>(); 
		bron=null;
		dodatek=null;
		czyDynamit=false;
		czyWiezienie=false;
		strzelal=false;
		
		hp=5;
	}
	
	public gracz(String a, int id) {
		nick=a;
		ID=id;
		reka = new ArrayList<karta>(); 
		bron=null;
		dodatek=null;
		czyDynamit=false;
		czyWiezienie=false;
		strzelal=false;
		
		hp=5;
	}
	
	public postac dajPostac() {
		return hero;
	}
	
	public void ustawPostac(postac p) {
		hero=p;
	}
	
	public int dajRole() {
		return rola;
	}
	
	public void ustawRole(int i) {
		rola=i;
	}
	
	public String dajNick() {
		return nick;
	}
	
	public List<karta> dajReke(){
		return reka;
	}
	
	public eq dajBron() {
		return bron;
	}
	
	public eq dajDodatek() {
		return dodatek;
	}
	
	//t�pe funckje ustawiaj�ce bro�/dodatek - w praktyce u�ywanej chyba tylko z nullem przy kradzierzy
	public void ustawBron(eq k) {
		bron=k;
	}
	
	public void ustawDodatek(eq k) {
		dodatek=k;
	}
	
	//bystra funkcja wywo�ywana gdy zagrywam bro�/dodatek 
	public void wyposaz(eq cos){
		if(cos.czyBron()==true){
			if(bron!=null) {
				gra.odzuc(bron);
			}
			bron=cos;
		}else {
			if(dodatek!=null) {
				gra.odzuc(dodatek);
			}
			dodatek=cos;
		}
	}
	
	public boolean czyStrzelal() {
		return strzelal;
	}
	
	public void ustawStrzelanie(boolean s) {
		strzelal=s;
	}
	
	public void dostanDynamit() {
		czyDynamit=true;
	}
	
	public void doPaki() {
		czyWiezienie=true;
	}
	
	//zwraca zasi�g gracza
	public int zasieg() {
		int wynik=1;
		if(bron!=null) {wynik=wynik+bron.dajZasiegMod(); }
		if(dodatek!=null) {wynik=wynik+dodatek.dajZasiegMod(); }
		if(hero.dajNazwe()=="Rose Doolan") {wynik=wynik+1; }
		return wynik;
	}
	
	//zwraca zasi�g bez uwzgl�dnienia broni (jaka� karta potrzebuje takiego)
	public int zasiegCzysty() {
		int wynik=1;
		if(dodatek!=null) {wynik=wynik+dodatek.dajZasiegMod(); }
		if(hero.dajNazwe()=="Rose Doolan") {wynik=wynik+1; }
		return wynik;
	}
	
	//zwraca negatywny podyfikator zasi�gu DO tego gracza (efekt mustanga)
	public int modZasiegu() {
		int wynik=0;
		if(dodatek!=null) {wynik=wynik+dodatek.dajObronaMod(); }
		if(hero.dajNazwe()=="Paul Regret") {wynik=wynik+1; }
		return wynik;
	}
	
	public int zdrowie() {
		return hp;
	}
	
	public int maxZdrowie() {
		int hapsy = hero.dajMaxHp();
		if(rola==1) {hapsy++;}
		return hapsy;
		//return 5;
	}
	
	//m�wi, czy gracz ma bary�k�
	public boolean obrona() {
		if(dodatek!=null) {
			return dodatek.czyObrona();
		}else {
			return false;
		}
	}
	
	//m�wi, czy bro� gracza mo�e strzela� wiele razy
	public boolean wielostrzal() {
		if(bron!=null) {
			return bron.czyMultistrzal();
		}else {
			return false;
		}
	}
	
	public void dobiezKarte() {
		karta k;
		k=gra.dobiez();
		reka.add(k);
	}
	
	public void doReki(karta k) {
		reka.add(k);
	}
	
	public void zReki(karta k) {
		reka.remove(k);
	}
	
	//metoda sprawdza, czy masz dan� kart� na r�ce, je�li masz to odrzuca. W finalnej wersji powinna jeszcze pyta�, czy chcesz odrzuci�, ale... co� jest nie tak w kontakcie
	public boolean testKarty(String nazwa, String zrodlo) {
		System.out.print("Testuje gracz " + nick + "\n");
		for(karta k : reka) {
			if(k.dajNazwe()==nazwa) {
				reka.remove(k);
				gra.odzuc(k);
				System.out.print("Odrzucoco kart� " + nazwa + "\n");
				return true;
				
				/*  wersja z zapytaniem
				boolean czy = kontakt.czyOdzucic(nazwa, zrodlo);
				if(czy==false) {
					return false;
				}else {
					reka.remove(k);
					gra.odzuc(k);
					return true;
				}
				*/
						
			}				
		}
		System.out.print("Brak karty " + nazwa + "-to zaboli. \n");
		return false;
	}

	//ogarnia dynamit, je�li jest-wywo�ywa� zawsze na start tury
	public void sprawdzDynamit() {
		if(czyDynamit==true) {
			czyDynamit=false;
			boolean wybuch = gra.poker("pik", 2, 9); 
			if(wybuch==true) {
				zran(3);
			}else {
				gracz g = gra.dajNastepnegoGracza();
				g.dostanDynamit();
			}
		}
	}
	
	//ogarnia wiezienie, je�li jest-wywo�ywa� zawsze na start tury
	public boolean sprawdzWiezienie() {
		if(czyWiezienie==true) {
			boolean wyjscie = gra.poker("kier");
			return wyjscie;
		}
		return true;
	}
	
	public void lecz(int ile) {
		hp=hp+ile;
		if(hp>maxZdrowie()) {
			hp=maxZdrowie();
		}
	}
	
	//funcja wywo�ywana dostaniem banga
	public void postrzel() {
		if(hero.dajNazwe()=="Jourdonnais") {
			if(gra.poker("kier")==true) {
				System.out.print("Epicko�� Jourdonnaisa ochroni�a\n");
				return;
			}
		}
		if(obrona()==true) {
			if(gra.poker("kier")==true) {
				System.out.print("Bary�ka ochroni�a\n");
				return;
			}
		}		
		boolean czy = testKarty("Pud�o", "Bang");
		if(czy==true){
			System.out.print("Spud�owa�\n");
		}else {
			zran(1);
		}
	}
	
	//funcja wywo�ywana dostaniem banga od postaci kt�r� trzeba podw�jnie pud�owa�
	public void postrzelBardziej() {
		int barylki=0;
		int pudla=0;
		
		if(hero.dajNazwe()=="Jourdonnais") {
			if(gra.poker("kier")==true) {
				System.out.print("Epicko�� Jourdonnaisa pomaga uskoczy�\n");
				barylki++;
			}
		}
		if(obrona()==true) {
			if(gra.poker("kier")==true) {
				System.out.print("Bary�ka pomaga uskoczy�\n");
				barylki++;
			}
		}	
		for(karta k : reka) {
			if(k.dajNazwe()=="Pud�o") {
				pudla++;
			}
		}
		
		if(barylki==2) {
			System.out.print("Unikn��e� precyzyjnego trafienia\n");
			return;
		}
		if(barylki==1) {
			boolean czy = testKarty("Pud�o", "Bang");
			if(czy==true){
				System.out.print("Spud�owa� dzi�ki pomocy bary�ki/�wietno�ci Jourdonnaisa\n");
			}
		}
		if(barylki==0) {
			if(pudla>1) {
				//pytanie czy chcesz pud�owa�
				int ile=2;
				for(karta k : reka) {
					if(k.dajNazwe()=="Pud�o" && ile>0) {
						ile--;
						reka.remove(k);
						gra.odzuc(k);
					}
				}
				System.out.print("Jakim� cudem spud�owa� \n");
				return;
			}
		}
		zran(1);
	}
	
	public void zran(int ile) {
		hp=hp-ile;
		if(hero.dajNazwe()=="Bart Cassady") {
			while(ile>0) {
				dobiezKarte();
				ile--;
			}
		}
		while(hp<1) {
			if(testKarty("Piwko", "Zgon")==true) {
				hp++;
			}else {
				gra.zgon(this);
			}
		}
	}
	
	//wywo�ywane na pocz�tku tury
	public void wezKarty() {	
		boolean czy;
		switch(hero.dajNazwe()) {
			case "Pedro Ramirez":
				czy = kontakt.czyDobracInaczej();
				if(czy==true) {
					karta k = gra.dobiezCmentaz();
					if(k==null) {
						dobiezKarte();
						dobiezKarte();
					}else {
						doReki(k);
						dobiezKarte();
					}
				}else {
					dobiezKarte();
					dobiezKarte();
				}			
				break;
			case "Black Jack":
				dobiezKarte();
				karta k = gra.dobiez();
				if(k.dajKolor()=="kier" || k.dajKolor()=="karo") {
					dobiezKarte();
				}
				doReki(k);
				break;
			case "Jesse Jones":
				czy = kontakt.czyDobracInaczej();
				if(czy==true) {
					gracz cel = kontakt.wybiezCel();
					List<karta> reka = cel.dajReke();
					Random rand = new Random();
					karta wynik = reka.get(rand.nextInt(reka.size()));
					reka.remove(wynik);
					doReki(wynik);
					dobiezKarte();
				}else {
					dobiezKarte();
					dobiezKarte();
				}				
				break;
			case "Kit Carlson":
				List<karta> wyciogniete = new ArrayList<karta>();
				wyciogniete.add(gra.dobiez());
				wyciogniete.add(gra.dobiez());
				wyciogniete.add(gra.dobiez());
				System.out.print("Wyci�gn��e� te trzy karty-wybierz kt�rej z nich nie chcesz");
				karta smiec = kontakt.wybiezKarte(wyciogniete);
				wyciogniete.remove(smiec);
				gra.naSzczyt(smiec);
				for(karta ka : wyciogniete) {
					doReki(ka);
				}
			default:
				dobiezKarte();
				dobiezKarte();
				break;
		}			
	}

	//wywo�ywane na koniec tury
	public void odzucKarty() {
		int ile = reka.size();
		int ileMoze = zdrowie();
		while(ile>ileMoze) {
			karta k = kontakt.wybiezKarte(reka);
			gra.odzuc(k);	
			ile--;
		}
	}
	
	
	
	
	
	
	
	//funkcje do test�w-ola� w finalnej implementacji
	public void pisz() {
		System.out.print(nick);
		System.out.print("\n");
	}
	
	public void opisz() {
		String wyjscie = "Posta�";
		if(obrona()==true) {wyjscie = wyjscie+" chowa si� za beczk�,";}
		if(wielostrzal()==true) {wyjscie = wyjscie+" mo�e strzela� wiele razy,";}
		wyjscie = wyjscie+" ma "+zasieg()+"pkt zasiegu, inni za� maj� zasi�g do niego zwi�kszony o "+modZasiegu()+".";
		System.out.println(wyjscie);
	}
	
	public void opiszReke() {
		String wyjscie = "Posta� ma na r�ce nast�puj�ce karty:";
		for(karta kar : reka) {
			wyjscie = wyjscie + " " + kar.dajNazwe();
		}
		System.out.println(wyjscie);
	}
}
