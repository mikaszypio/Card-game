package testy;

import java.util.ArrayList;
import java.util.List;

public class gracz {

	private postac hero;
	private String nick;
	private int rola;		//1=szeryf, 2=pomocnik, 3=-renegat, 4=bandyta
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
	
	public postac dajPostac() {
		return hero;
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
			boolean wyj�cie = gra.poker("kier");
			return wyj�cie;
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
		if(hp<1) {
			//ratowanie �ycia piwkiem
			//zgon
		}
	}
	
	//wywo�ywane na pocz�tku tury
	public void wezKarty() {	
		if(false) {
			//wersja dla bochatera co inaczej dobiera
		}else {
			dobiezKarte();
			dobiezKarte();
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
