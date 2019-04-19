package cardgame.game.model.cards;

import cardgame.game.model.Deck;
import cardgame.game.model.Gracz;
import java.util.List;

public class Equipment extends Card {

	private boolean bron;
	
	private int zasiegMod;
	private int obronaMod;
	private boolean obrona;
	private boolean miltistrzal;
	
	public Equipment(int id, String naz, int num, String col, boolean czyBron, int zasM, int obrM, boolean czyBroni, boolean czyWielo){
		ID=id;
		nazwa=naz;
		numer=num;
		kolor=col;
		bron=czyBron;
		zasiegMod=zasM;
		obronaMod=obrM;
		obrona=czyBroni;
		miltistrzal=czyWielo;
		//gra=g;
	}
	
	public boolean czyBron() {
		return bron;
	}
	
	public int dajZasiegMod() {
		return zasiegMod;
	}
	
	public int dajObronaMod() {
		return obronaMod;
	}
	
	public boolean czyObrona() {
		return obrona;
	}
	
	public boolean czyMultistrzal() {
		return miltistrzal;
	}
	
	@Override
	public boolean zagraj(Deck deck, List<Gracz> players, Gracz currentPlayer) {
		Gracz g = currentPlayer;
		g.wyposaz(this, deck);
		return true;
	}
}
