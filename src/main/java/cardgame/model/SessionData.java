package cardgame.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionData {
	
	@JsonProperty("userId")
	private long userId;
	
	@JsonProperty("username")
	private String username;
	
	public SessionData() {
		
	}
	
	public SessionData(long userId, String username) {
		this.userId = userId;
		this.username = username;
	}
	
	public SessionData(User user) {
		userId = user.getUserId();
		username = user.getUsername();
	}
	
	public long getUserId() {
		return userId;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
}
