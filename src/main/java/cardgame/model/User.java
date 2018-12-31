package cardgame.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "app_user")
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;
	
	@Column(nullable = false, unique = true)
	private String username;

	private String password;
	private Integer score;
	
	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;
	
	@ManyToOne
	@JoinColumn(name = "room_id")
	private Room room;
	
	public User() {
		
	}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		score = 0;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public Integer getScore() {
		return score;
	}
	
	public Role getRole() {
		return role;
	}
	
	public Room getRoom() {
		return room;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	public void setRoom(Room room) {
		this.room = room;
	}
}
