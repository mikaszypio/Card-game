package cardgame.model;

import java.util.ArrayList;

public class Room {

	private int roomId;
	private String name;
	private boolean active;
	private ArrayList<ChatMessage> chat;
	
	public Room() {
		
	}
	
	public Room(String name) {
		this.name = name;
		active = false;
		chat = new ArrayList<ChatMessage>();
	}
	
	public int getRoomId() {
		return roomId;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean getActive() {
		return active;
	}
	
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	
	public ArrayList<ChatMessage> getChat() {
		return chat;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public void setChat(ArrayList<ChatMessage> chat) {
		this.chat = chat;
	}
}
