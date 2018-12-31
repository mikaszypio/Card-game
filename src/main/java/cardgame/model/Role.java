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
@Table(name = "role")
public class Role implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer roleId;
	
	@Column(nullable = false, unique = true)
	private String name;
	
	@OneToMany(mappedBy = "role")
	private List<User> users;
	
	public Role() {
		
	}
	
	public Role(String name) {
		this.name = name;
		this.users = new ArrayList<>();
	}
	
	public Integer getRoleId() {
		return roleId;
	}
	
	public String getName() {
		return name;
	}
	
	public List<User> users() {
		return users;
	}
	
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	public void setNameId(String name) {
		this.name = name;
	}
	
	public void setUsers(List<User> users) {
		this.users = users;
	}
}
