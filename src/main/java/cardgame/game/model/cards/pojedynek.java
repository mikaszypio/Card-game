package cardgame.game.model.cards;

import cardgame.game.kontakt;
import cardgame.game.model.Deck;
import cardgame.game.model.Gracz;
import java.util.List;

public class pojedynek extends Card{
	
	public pojedynek(int id, String naz, int num, String col) {
		ID=id;
		nazwa=naz;
		obrazek = "Brak obrazu";
		opis = "Brak opisu";
		numer=num;
		kolor=col;
	}
	
	public pojedynek(int id, String obraz, String opek, String naz, int num, String col) {
		ID=id;
		nazwa=naz;
		obrazek = obraz;
		opis = opek;
		numer=num;
		kolor=col;
	}
	
	@Override
	public boolean zagraj(Deck deck, List<Gracz> players, Gracz currentPlayer) {
		Gracz cel = kontakt.wybiezCel(players);
		Gracz wyzywajacy = currentPlayer;
		boolean odbito = false;
		boolean toczySie = true;
		while(toczySie==true) {
			if(odbito==true) {
				if(wyzywajacy.testKarty("bang", "Pojedynek", deck)==true) {
					odbito=false;
				}else {
					wyzywajacy.zran(1, deck);
					toczySie=false;
				}
			}else {
				if(cel.testKarty("bang", "Pojedynek", deck)==true) {
					odbito=true;
				}else {
					cel.zran(1, deck);
					toczySie=false;
				}
			}
		}	
		return true;
	}
}
