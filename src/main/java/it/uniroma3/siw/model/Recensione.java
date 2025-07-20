package it.uniroma3.siw.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Recensione {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String titolo;
	
	@NotNull
	@Min(1)
	@Max(5)
	private Integer voto;
	private String testo;
	
	@ManyToOne
	private Libro libro;
	
	
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
	public Libro getLibro() {
		return libro;
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
	public void setLibro(Libro libro) {
		this.libro = libro;
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
