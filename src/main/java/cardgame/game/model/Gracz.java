package cardgame.game.model;

import cardgame.game.Gra;
import cardgame.game.kontakt;
import cardgame.game.model.cards.Equipment;
import cardgame.game.model.cards.Card;
import cardgame.game.model.cards.Postac;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Gracz {

	private Gra gra;
	private Postac hero;
	private String nick;
	private Long ID;
	private int rola;		//1=szeryf, 2=pomocnik, 3=renegat, 4=bandyta
	private List<Card> reka;
	private int hp;
	private Equipment bron;
	private Equipment dodatek;
	private boolean strzelal;
	private boolean czyDynamit;
	private boolean czyWiezienie;

	public Gracz(String a) {
		nick=a;
		reka = new ArrayList<Card>(); 
		bron=null;
		dodatek=null;
		czyDynamit=false;
		czyWiezienie=false;
		strzelal=false;
		
		hp=5;
	}
	
	public Gracz(String a, Long id) {
		nick=a;
		ID=id;
		reka = new ArrayList<Card>(); 
		bron=null;
		dodatek=null;
		czyDynamit=false;
		czyWiezienie=false;
		strzelal=false;
		
		hp=5;
	}
	
	public Long dajId() {
		return ID;
	}
	
	public int dajHp() {
		return hp;
	}
	
	public void ustawGre(Gra g) {
		gra=g;
	}
	
	public Postac dajPostac() {
		return hero;
	}
	
	public void ustawPostac(Postac p) {
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
	
	public List<Card> dajReke(){
		return reka;
	}
	
	public Equipment dajBron() {
		return bron;
	}
	
	public Equipment dajDodatek() {
		return dodatek;
	}
	
	//t�pe funckje ustawiaj�ce bro�/dodatek - w praktyce u�ywanej chyba tylko z nullem przy kradzierzy
	public void ustawBron(Equipment k) {
		bron=k;
	}
	
	public void ustawDodatek(Equipment k) {
		dodatek=k;
	}
	
	//bystra funkcja wywo�ywana gdy zagrywam bro�/dodatek 
	public void wyposaz(Equipment cos){
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
		Card k;
		k=gra.dobiez();
		reka.add(k);
	}
	
	public void doReki(Card k) {
		reka.add(k);
	}
	
	public void zReki(Card k) {
		reka.remove(k);
	}
	
	//metoda sprawdza, czy masz dan� kart� na r�ce, je�li masz to odrzuca. W finalnej wersji powinna jeszcze pyta�, czy chcesz odrzuci�, ale... co� jest nie tak w kontakcie
	public boolean testKarty(String nazwa, String zrodlo) {
		System.out.print("Testuje gracz " + nick + "\n");
		for(Card k : reka) {
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
				Gracz g = gra.dajNastepnegoGracza();
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
		boolean czy = testKarty("Pudlo", "Bang");
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
		for(Card k : reka) {
			if(k.dajNazwe()=="Pudlo") {
				pudla++;
			}
		}
		
		if(barylki==2) {
			System.out.print("Unikn��e� precyzyjnego trafienia\n");
			return;
		}
		if(barylki==1) {
			boolean czy = testKarty("Pudlo", "Bang");
			if(czy==true){
				System.out.print("Spud�owa� dzi�ki pomocy bary�ki/�wietno�ci Jourdonnaisa\n");
			}
		}
		if(barylki==0) {
			if(pudla>1) {
				//pytanie czy chcesz pud�owa�
				int ile=2;
				for(Card k : reka) {
					if(k.dajNazwe()=="Pudlo" && ile>0) {
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
					Card k = gra.dobiezCmentaz();
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
				Card k = gra.dobiez();
				if(k.dajKolor()=="kier" || k.dajKolor()=="karo") {
					dobiezKarte();
				}
				doReki(k);
				break;
			case "Jesse Jones":
				czy = kontakt.czyDobracInaczej();
				if(czy==true) {
					Gracz cel = kontakt.wybiezCel(gra);
					List<Card> reka = cel.dajReke();
					Random rand = new Random();
					Card wynik = reka.get(rand.nextInt(reka.size()));
					reka.remove(wynik);
					doReki(wynik);
					dobiezKarte();
				}else {
					dobiezKarte();
					dobiezKarte();
				}				
				break;
			case "Kit Carlson":
				List<Card> wyciogniete = new ArrayList<Card>();
				wyciogniete.add(gra.dobiez());
				wyciogniete.add(gra.dobiez());
				wyciogniete.add(gra.dobiez());
				System.out.print("Wyci�gn��e� te trzy karty-wybierz kt�rej z nich nie chcesz");
				Card smiec = kontakt.wybiezKarte(wyciogniete);
				wyciogniete.remove(smiec);
				gra.naSzczyt(smiec);
				for(Card ka : wyciogniete) {
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
			Card k = kontakt.wybiezKarte(reka);
			gra.odzuc(k);	
			ile--;
		}
	}
}
