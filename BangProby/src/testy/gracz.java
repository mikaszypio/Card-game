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
	
	//têpe funckje ustawiaj¹ce broñ/dodatek - w praktyce u¿ywanej chyba tylko z nullem przy kradzierzy
	public void ustawBron(eq k) {
		bron=k;
	}
	
	public void ustawDodatek(eq k) {
		dodatek=k;
	}
	
	//bystra funkcja wywo³ywana gdy zagrywam broñ/dodatek 
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
	
	//zwraca zasiêg gracza
	public int zasieg() {
		int wynik=1;
		if(bron!=null) {wynik=wynik+bron.dajZasiegMod(); }
		if(dodatek!=null) {wynik=wynik+dodatek.dajZasiegMod(); }
		if(hero.dajNazwe()=="Rose Doolan") {wynik=wynik+1; }
		return wynik;
	}
	
	//zwraca zasiêg bez uwzglêdnienia broni (jakaœ karta potrzebuje takiego)
	public int zasiegCzysty() {
		int wynik=1;
		if(dodatek!=null) {wynik=wynik+dodatek.dajZasiegMod(); }
		if(hero.dajNazwe()=="Rose Doolan") {wynik=wynik+1; }
		return wynik;
	}
	
	//zwraca negatywny podyfikator zasiêgu DO tego gracza (efekt mustanga)
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
	
	//mówi, czy gracz ma bary³kê
	public boolean obrona() {
		if(dodatek!=null) {
			return dodatek.czyObrona();
		}else {
			return false;
		}
	}
	
	//mówi, czy broñ gracza mo¿e strzelaæ wiele razy
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
	
	//metoda sprawdza, czy masz dan¹ kartê na rêce, jeœli masz to odrzuca. W finalnej wersji powinna jeszcze pytaæ, czy chcesz odrzuciæ, ale... coœ jest nie tak w kontakcie
	public boolean testKarty(String nazwa, String zrodlo) {
		System.out.print("Testuje gracz " + nick + "\n");
		for(karta k : reka) {
			if(k.dajNazwe()==nazwa) {
				reka.remove(k);
				gra.odzuc(k);
				System.out.print("Odrzucoco kartê " + nazwa + "\n");
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

	//ogarnia dynamit, jeœli jest-wywo³ywaæ zawsze na start tury
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
	
	//ogarnia wiezienie, jeœli jest-wywo³ywaæ zawsze na start tury
	public boolean sprawdzWiezienie() {
		if(czyWiezienie==true) {
			boolean wyjœcie = gra.poker("kier");
			return wyjœcie;
		}
		return true;
	}
	
	public void lecz(int ile) {
		hp=hp+ile;
		if(hp>maxZdrowie()) {
			hp=maxZdrowie();
		}
	}
	
	//funcja wywo³ywana dostaniem banga
	public void postrzel() {
		if(hero.dajNazwe()=="Jourdonnais") {
			if(gra.poker("kier")==true) {
				System.out.print("Epickoœæ Jourdonnaisa ochroni³a\n");
				return;
			}
		}
		if(obrona()==true) {
			if(gra.poker("kier")==true) {
				System.out.print("Bary³ka ochroni³a\n");
				return;
			}
		}		
		boolean czy = testKarty("Pud³o", "Bang");
		if(czy==true){
			System.out.print("Spud³owa³\n");
		}else {
			zran(1);
		}
	}
	
	//funcja wywo³ywana dostaniem banga od postaci któr¹ trzeba podwójnie pud³owaæ
	public void postrzelBardziej() {
		int barylki=0;
		int pudla=0;
		
		if(hero.dajNazwe()=="Jourdonnais") {
			if(gra.poker("kier")==true) {
				System.out.print("Epickoœæ Jourdonnaisa pomaga uskoczyæ\n");
				barylki++;
			}
		}
		if(obrona()==true) {
			if(gra.poker("kier")==true) {
				System.out.print("Bary³ka pomaga uskoczyæ\n");
				barylki++;
			}
		}	
		for(karta k : reka) {
			if(k.dajNazwe()=="Pud³o") {
				pudla++;
			}
		}
		
		if(barylki==2) {
			System.out.print("Unikn¹³eœ precyzyjnego trafienia\n");
			return;
		}
		if(barylki==1) {
			boolean czy = testKarty("Pud³o", "Bang");
			if(czy==true){
				System.out.print("Spud³owa³ dziêki pomocy bary³ki/œwietnoœci Jourdonnaisa\n");
			}
		}
		if(barylki==0) {
			if(pudla>1) {
				//pytanie czy chcesz pud³owaæ
				int ile=2;
				for(karta k : reka) {
					if(k.dajNazwe()=="Pud³o" && ile>0) {
						ile--;
						reka.remove(k);
						gra.odzuc(k);
					}
				}
				System.out.print("Jakimœ cudem spud³owa³ \n");
				return;
			}
		}
		zran(1);
	}
	
	public void zran(int ile) {
		hp=hp-ile;
		if(hp<1) {
			//ratowanie ¿ycia piwkiem
			//zgon
		}
	}
	
	//wywo³ywane na pocz¹tku tury
	public void wezKarty() {	
		if(false) {
			//wersja dla bochatera co inaczej dobiera
		}else {
			dobiezKarte();
			dobiezKarte();
		}
	}
	
	//wywo³ywane na koniec tury
	public void odzucKarty() {
		int ile = reka.size();
		int ileMoze = zdrowie();
		while(ile>ileMoze) {
			karta k = kontakt.wybiezKarte(reka);
			gra.odzuc(k);	
			ile--;
		}
	}
	
	
	
	
	
	
	
	//funkcje do testów-olaæ w finalnej implementacji
	public void pisz() {
		System.out.print(nick);
		System.out.print("\n");
	}
	
	public void opisz() {
		String wyjscie = "Postaæ";
		if(obrona()==true) {wyjscie = wyjscie+" chowa siê za beczk¹,";}
		if(wielostrzal()==true) {wyjscie = wyjscie+" mo¿e strzelaæ wiele razy,";}
		wyjscie = wyjscie+" ma "+zasieg()+"pkt zasiegu, inni zaœ maj¹ zasiêg do niego zwiêkszony o "+modZasiegu()+".";
		System.out.println(wyjscie);
	}
	
	public void opiszReke() {
		String wyjscie = "Postaæ ma na rêce nastêpuj¹ce karty:";
		for(karta kar : reka) {
			wyjscie = wyjscie + " " + kar.dajNazwe();
		}
		System.out.println(wyjscie);
	}
}
