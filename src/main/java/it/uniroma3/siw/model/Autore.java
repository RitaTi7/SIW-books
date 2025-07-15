package it.uniroma3.siw.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Autore {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	private String cognome;
	private LocalDate dataNascita;
	private LocalDate dataMorte;		//TODO: morte può essere NULL
	private String nazionalità;			//deve essere scelto da un elenco??
	
	//TODO: eventuale collegamento ai libri scritti
	

}
