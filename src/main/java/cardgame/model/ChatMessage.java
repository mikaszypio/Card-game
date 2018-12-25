package cardgame.model;

public class ChatMessage {

	private int roomId;
	private int userId;
	private String content;
	
	public ChatMessage() {
		
	}
	
	public ChatMessage(int roomId, int userId, String content) {
		this.roomId = roomId;
		this.userId = userId;
		this.content = content;
	}
	
	public int getRoomId() {
		return roomId;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public String getContent() {
		return content;
	}
}
