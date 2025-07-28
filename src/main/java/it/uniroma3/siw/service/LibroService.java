package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.repository.LibroRepository;

@Service
public class LibroService {
	
	@Autowired LibroRepository libroRepository;
	
	public Libro getLibroById(Long id) {
		Optional<Libro> l= this.libroRepository.findById(id); 	
		if(l.isEmpty())						//oppure si può usare orElse(null)... vedi
			return null;
		else 
			return l.get();
	}
	
	public Iterable<Libro> getAllLibri(){
		return this.libroRepository.findAll();
	}
	
	public void save(Libro libro) {
		this.libroRepository.save(libro);
	}
	
	public void deleteLibroById(Long id) {
		this.libroRepository.deleteById(id);
	}
	
	public Iterable<Libro> findLibriNonInAutore(Long idAutore){
		return this.libroRepository.findLibriNonInAutore(idAutore);
	}
	
	public List<Libro> getUltimiLibriInseriti(int numero){
		return this.libroRepository.getUltimiLibriInseriti(numero);
	}

}
