package cardgame.game;

import cardgame.game.model.cards.karta;
import cardgame.game.model.gracz;
import cardgame.game.gra;
import java.util.List;
import java.util.Scanner;

public class kontakt {
	//ca�a klasa jest �mieciem do testowania, finalnie zast�pi si� j� metodami ��cz�cymi si� z gui
	//acz nawet teraz nie chce w pe�ni dzia�a� momentami
	public static karta wybiezKarte(List<karta> reka) {
		while(true) {
			System.out.print("Twoja r�ka\n");
			for(karta kar : reka) {
				String wyjscie = kar.dajID() + "-" + kar.dajNazwe() + "\n";
				System.out.print(wyjscie);
			}
			System.out.print("Wybiez kart� (wpisz numer)\n");
			Scanner reader = new Scanner(System.in);
			int id = reader.nextInt();
			reader.close();
			for(karta kar : reka) {
				if(kar.dajID()==id) {
					return kar;
				}
			}		
		}
	}
	
	public static String coChceszZniszczyc() {
		while(true) {
			System.out.print("Co chcesz mu zniszczy�? Kart� z r�ki, bro� czy dodatek? (R/B/D)");			
			Scanner reader = new Scanner(System.in);
			String wynik = reader.nextLine();
			reader.close();
			if(wynik=="R") { return "reka"; }		
			if(wynik=="B") { return "bron"; }	
			if(wynik=="D") { return "dodatek"; }	
			System.out.print("Nie rozpoznano odpowiedzi. Wpisz R, B lub D");
		}
	}
	
	public static gracz wybiezCel() {
		List<gracz> gracze = gra.dajGraczy();
		while(true) {
			System.out.print("Lista graczy\n");
			for(gracz g : gracze) {
				String wyjscie = g.dajNick() + "\n";
				System.out.print(wyjscie);
			}
			System.out.print("Wybiez gracza (wpisz nick)/n");
			Scanner reader = new Scanner(System.in);
			String nazwa = reader.nextLine();
			reader.close();
			for(gracz g : gracze) {
				if(g.dajNick()==nazwa) {
					return g;
				}
			}		
		}
	}
	
	public static boolean czyOdzucic(String nazwa, String zrodlo) {
		System.out.print("Czy chcesz zu�y� kart� " + nazwa + " by ustrzec si� przed " + zrodlo + "(1/0)\n");
		while(true) {
			Scanner reader = new Scanner(System.in);
			int wynik = reader.nextInt();
			reader.close();
			if(wynik==1) {
				return true;
			}
			if(wynik==0) {
				return false;
			}
			System.out.print("Nie rozpoznano odpowiedzi, spr�buj jeszcze raz. \n");
		}
	}
	
	public static boolean czyDobracInaczej() {
		System.out.print("Czy chcesz dobra� kart� w inny spos�b ni� z talii (1/0)\n");
		while(true) {
			Scanner reader = new Scanner(System.in);
			int wynik = reader.nextInt();
			reader.close();
			if(wynik==1) {
				return true;
			}
			if(wynik==0) {
				return false;
			}
			System.out.print("Nie rozpoznano odpowiedzi, spr�buj jeszcze raz. \n");
		}
	}
}
