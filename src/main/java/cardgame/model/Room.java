package cardgame.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "room")
public class Room implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long roomId;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private Boolean active;
	
	@OneToMany(mappedBy = "room")
	private List<User> players;
	
	public Room() {
		
	}
	
	public Room(String name) {
		this.name = name;
		active = false;
		players = new ArrayList<>();
	}
	
	public Long getRoomId() {
		return roomId;
	}
	
	public String getName() {
		return name;
	}
	
	public Boolean getActive() {
		return active;
	}
	
	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}
	
	public List<User> getPlayers() {
		return players;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public void setPlayers(List<User> players) {
		this.players = players;
	}
}
