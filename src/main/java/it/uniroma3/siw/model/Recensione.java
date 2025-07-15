package it.uniroma3.siw.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Recensione {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	private String titolo;
	private Integer voto;		//TODO: voto dovrà essere compreso tra 0 e 5
	private String testo;
	
	
	public Long getId() {
		return id;
	}
	public String getTitolo() {
		return titolo;
	}
	public Integer getVoto() {
		return voto;
	}
	public String getTesto() {
		return testo;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public void setVoto(Integer voto) {
		this.voto = voto;
	}
	public void setTesto(String testo) {
		this.testo = testo;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if(obj==null || obj.getClass()!=this.getClass())
			return false;
		
		Recensione r= (Recensione) obj;
		
		return r.getTitolo().equals(this.getTitolo()) && r.getVoto().equals(this.getVoto()) && r.getTesto().equals(this.getTesto());
	}
	
	@Override
	public int hashCode() {
		return this.getClass().hashCode()+this.getTitolo().hashCode()+this.getVoto()+this.getTesto().hashCode();
	}
}
