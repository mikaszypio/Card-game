package cardgame.model;

public class ChatMessage {

	private String author;
	private String content;
	private String time;
	
	public ChatMessage() {
		
	}
	
	public ChatMessage(String content) {
		this(null, content, null);
	}
	
	public ChatMessage(String author, String content, String time) {
		this.author = author;
		this.content = content;
		this.time = time;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public String getContent() {
		return content;
	}
	
	public String getTime() {
		return time;
	}
}
