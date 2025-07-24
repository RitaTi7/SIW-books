package it.uniroma3.siw.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Credentials {
	
	public static final String DEFAULT_ROLE= "DEFAULT";
	public static final String ADMIN_ROLE= "ADMIN";
	
	//conviene che i nomi dei ruoli siano: admin, user e guest (sarà di default)
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	private String username;
	private String password;
	private String role;
	
	@OneToOne(cascade= CascadeType.ALL)
	private User user;
	
	public Long getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getRole() {
		return role;
	}
	public User getUser() {
		return user;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setRole(String ruolo) {
		this.role = ruolo;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj==null || obj.getClass()!=this.getClass())
			return false;
		
		Credentials c= (Credentials) obj;
		
		return c.getPassword().equals(this.getPassword()) && c.getRole().equals(this.getRole())
				&&c.getUser().equals(this.getUser())&& c.getUsername().equals(this.getUsername());
 	}
	
	@Override
	public int hashCode() {
		return this.getClass().hashCode()+this.getPassword().hashCode()+this.getRole().hashCode()+
				this.getUsername().hashCode()+this.getUser().hashCode();
	}
}
