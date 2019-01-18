package cardgame.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "room")
public class Room implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long roomId;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private Boolean active;
	
	@Column(nullable = false)
	private Byte readiness;
	
	@OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<User> users;
	
	public Room() {
		
	}
	
	public Room(String name) {
		this.name = name;
		active = false;
		readiness = -1;
		users = new ArrayList<>();
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
	
	public Byte getReadiness() {
		return readiness;
	}
	
	public List<User> getUsers() {
		return users;
	}
	
	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public void setReadiness(byte readiness) {
		this.readiness = readiness;
	}
	
	public void setUsers(List<User> users) {
		this.users = users;
	}
}
