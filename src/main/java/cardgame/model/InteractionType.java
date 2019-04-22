package cardgame.model;

public enum InteractionType {
	ALTERNATIVEGET (1),
	CARDSELECTION (2),
	DEATH (3),
	DESTROYCARD (4),
	ENDOFTURN (5),
	FORCEEND (6),
	TARGETSELECTION (7),
	USECOUNTERCARD (8);
	
	private final int value;
	private InteractionType(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
