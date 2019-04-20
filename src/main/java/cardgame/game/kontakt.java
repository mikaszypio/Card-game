package cardgame.game;

import cardgame.game.model.Player;
import cardgame.game.model.cards.Card;
import java.util.List;
import java.util.Scanner;

public class kontakt {
	//caa klasa jest mieciem do testowania, finalnie zastpi si j metodami czcymi si z gui
	//acz nawet teraz nie chce w peni dziaa momentami
//	public static Card wybiezKarte(List<Card> reka) {
//		while(true) {
//			System.out.print("Twoja reka\n");
//			for(Card kar : reka) {
//				String wyjscie = kar.dajID() + "-" + kar.dajNazwe() + "\n";
//				System.out.print(wyjscie);
//			}
//			System.out.print("Wybiez karte (wpisz numer)\n");
//			Scanner reader = new Scanner(System.in);
//			int id = -1;
//			try {
//				id = Integer.parseInt(reader.nextLine());
//			} catch (NumberFormatException e) {
//				e.printStackTrace();
//			}
//			reader.close();
//			for(Card kar : reka) {
//				if(kar.dajID() == id) {
//					return kar;
//				}
//			}		
//		}
//	}
	
	public static String coChceszZniszczyc() {
		while(true) {
			System.out.print("Co chcesz mu zniszczy? Kart z rki, bro czy dodatek? (R/B/D)");			
			Scanner reader = new Scanner(System.in);
			String wynik = reader.nextLine();
			reader.close();
			if(wynik=="R") { return "reka"; }		
			if(wynik=="B") { return "bron"; }	
			if(wynik=="D") { return "dodatek"; }	
			System.out.print("Nie rozpoznano odpowiedzi. Wpisz R, B lub D");
		}
	}
	
//	public static Player wybiezCel(List<Player> players) {
//		List<Player> gracze = players;
//		while(true) {
//			System.out.print("Lista graczy\n");
//			for(Player g : gracze) {
//				String wyjscie = g.getNickname() + "\n";
//				System.out.print(wyjscie);
//			}
//			System.out.print("Wybiez gracza (wpisz nick)\n");
//			Scanner reader = new Scanner(System.in);
//			String nazwa = reader.nextLine();
//			reader.close();
//			for(Player g : gracze) {
//				if(g.getNickname()==nazwa) {
//					return g;
//				}
//			}		
//		}
//	}
	
	public static boolean czyOdzucic(String nazwa, String zrodlo) {
		System.out.print("Czy chcesz zuy kart " + nazwa + " by ustrzec si przed " + zrodlo + "(1/0)\n");
		while(true) {
			Scanner reader = new Scanner(System.in);
			int wynik = -1;
			try {
				wynik = Integer.parseInt(reader.nextLine());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			reader.close();
			if(wynik==1) {
				return true;
			}
			if(wynik==0) {
				return false;
			}
			System.out.print("Nie rozpoznano odpowiedzi, sprbuj jeszcze raz. \n");
		}
	}
	
	public static boolean czyDobracInaczej() {
		System.out.print("Czy chcesz dobra kart w inny sposb ni z talii (1/0)\n");
		while(true) {
			Scanner reader = new Scanner(System.in);
			int wynik = -1;
			try {
				wynik = Integer.parseInt(reader.nextLine());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			reader.close();
			if(wynik==1) {
				return true;
			}
			if(wynik==0) {
				return false;
			}
			System.out.print("Nie rozpoznano odpowiedzi, sprbuj jeszcze raz. \n");
		}
	}
}
