package it.uniroma3.siw.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.repository.RecensioneRepository;

@Service
public class RecensioneService {
	
	@Autowired RecensioneRepository recensioneRepository;
	
	public void save(Recensione recensione) {
		this.recensioneRepository.save(recensione);
	}
	
	public Recensione getRecensioneById(Long id) {
		Optional<Recensione> r= this.recensioneRepository.findById(id); 	
		if(r.isEmpty())						//oppure si può usare orElse(null)... vedi
			return null;
		else 
			return r.get();
	}
	
	public void deleteRecensioneById(Long id) {
		this.recensioneRepository.deleteById(id);
	}


}
