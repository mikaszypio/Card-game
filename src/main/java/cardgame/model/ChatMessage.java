package cardgame.model;

public class ChatMessage {

	private String author;
	private String content;
	private String time;
	
	public ChatMessage() {
		
	}
	
	public ChatMessage(String author, String content) {
		this(author, content, null);
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
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
}
