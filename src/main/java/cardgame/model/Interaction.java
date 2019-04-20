package cardgame.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class Interaction implements Serializable {
	
	@JsonProperty("playerId")
	private long playerId;
	
	@JsonProperty("type")
	private InteractionType type;
	
	@JsonProperty("selection")
	private int selection;
	
	public Interaction() {
		
	}
	
	public Interaction(long playerId, InteractionType type, int selection) {
		this.playerId = playerId;
		this.type = type;
		this.selection = selection;
	}
	
	public Interaction(long playerId, String type, int selection) {
		this.playerId = playerId;
		this.type = InteractionType.valueOf(type);
		this.selection = selection;
	}
	
	public long getPlayerId(){
		return playerId;
	}
	
	public InteractionType getType() {
		return type;
	}
	
	public int getSelection() {
		return selection;
	}
	
	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}
	
	public void setType(InteractionType type) {
		this.type = type;
	}
	
	public void setSelection(int selection) {
		this.selection = selection;
	}
}
