package cardgame.model;

import cardgame.viewmodel.PartialCard;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class CardNotification {
	
	@JsonProperty("playerId")
	private long playerId;
	
	@JsonProperty("type")
	private InteractionType type;
	
	@JsonProperty("list")
	private List<PartialCard> cards;
	
	public CardNotification(long playerId, List<PartialCard> cards) {
		this.playerId = playerId;
		this.type = InteractionType.CARDGETNOTIFY;
		this.cards = cards;
	}
	
	public long getPlayerId() {
		return playerId;
	}
	
	public InteractionType getType() {
		return type;
	}
	
	public List<PartialCard> getCards() {
		return cards;
	}
	
	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}
	
	public void setType(InteractionType type) {
		this.type = type;
	}
	
	public void setCards(List<PartialCard> cards) {
		this.cards = cards;
	}
}
