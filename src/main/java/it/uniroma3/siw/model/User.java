package it.uniroma3.siw.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String name;
	@NotBlank
	private String surname;
	@NotBlank
	private String email;
	
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getSurname() {
		return surname;
	}
	public String getEmail() {
		return email;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String nome) {
		this.name = nome;
	}
	public void setSurname(String cognome) {
		this.surname = cognome;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj==null || obj.getClass()!=this.getClass())
			return false;
		
		User u= (User) obj;
		
		return u.getName().equals(this.getName())&& u.getSurname().equals(this.getSurname()) && u.getEmail().equals(this.getEmail());
	}
	
	@Override
	public int hashCode() {
		return this.getClass().hashCode()+this.getName().hashCode()+this.getSurname().hashCode()+this.getEmail().hashCode();
	}
	
}
