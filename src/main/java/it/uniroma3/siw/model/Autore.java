package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Autore {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String nome;
	@NotBlank
	private String cognome;
	
	@NotNull
	private LocalDate dataNascita;		//TODO: la nascita non può superare la morte
	private LocalDate dataMorte;		//TODO: morte può essere NULL e non può superare la data corrente
	@NotBlank
	private String nazionalita;			//deve essere scelto da un elenco??
	
	
	@ManyToMany(mappedBy="autori")
	private List<Libro> libri;
	
	//fotografia
	
	
	public Long getId() {
		return id;
	}
	public String getNome() {
		return nome;
	}
	public String getCognome() {
		return cognome;
	}
	public LocalDate getDataNascita() {
		return dataNascita;
	}
	public LocalDate getDataMorte() {
		return dataMorte;
	}
	public String getNazionalita() {
		return nazionalita;
	}
	public List<Libro> getLibri() {
		return libri;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}
	public void setDataMorte(LocalDate dataMorte) {
		this.dataMorte = dataMorte;
	}
	public void setNazionalita(String nazionalità) {
		this.nazionalita = nazionalità;
	}
	public void setLibri(List<Libro> libri) {
		this.libri = libri;
	}
	
	//TODO: metodo ToString?
	@Override
	public boolean equals(Object obj) {
		if(obj==null || obj.getClass()!=this.getClass())
			return false;
		
		Autore a= (Autore) obj;
		
		return a.getNome().equals(this.getNome()) && a.getCognome().equals(this.getCognome()) && a.getDataNascita().equals(this.getDataNascita()) && a.getNazionalita().equals(this.getNazionalita());
	}
	
	@Override
	public int hashCode() {
		return this.getClass().hashCode()+this.getNome().hashCode()+this.getCognome().hashCode()+this.getDataNascita().hashCode()+this.getNazionalita().hashCode();
	}

}
