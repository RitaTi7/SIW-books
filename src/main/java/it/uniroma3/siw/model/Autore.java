package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Autore {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	private String cognome;
	private LocalDate dataNascita;
	private LocalDate dataMorte;		//TODO: morte può essere NULL
	private String nazionalita;			//deve essere scelto da un elenco??
	
	@ManyToMany(mappedBy="autori")
	private List<Libro> libri;
	
	
	
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
	public String getNazionalità() {
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
	public void setNazionalità(String nazionalità) {
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
		
		return a.getNome().equals(this.getNome()) && a.getCognome().equals(this.getCognome()) && a.getDataNascita().equals(this.getDataNascita()) && a.getNazionalità().equals(this.getNazionalità());
	}
	
	@Override
	public int hashCode() {
		return this.getClass().hashCode()+this.getNome().hashCode()+this.getCognome().hashCode()+this.getDataNascita().hashCode()+this.getNazionalità().hashCode();
	}

}
