package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import it.uniroma3.siw.model.Libro;

public interface LibroRepository extends CrudRepository<Libro, Long> {
	
	
	@Query(value= "SELECT* FROM libro l WHERE l.id NOT IN (SELECT libri_id FROM libro_autori WHERE libro_autori.autori_id = :idAutore)", nativeQuery=true)
	public Iterable<Libro> findLibriNonInAutore(Long idAutore);
	
	@Query(value="SELECT* FROM libro l  ORDER BY l.id DESC LIMIT 3", nativeQuery = true)
	public List<Libro> getUltimiLibriInseriti(int numero);

}
