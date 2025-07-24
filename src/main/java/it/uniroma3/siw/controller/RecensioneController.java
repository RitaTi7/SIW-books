package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.service.LibroService;
import it.uniroma3.siw.service.RecensioneService;
import jakarta.validation.Valid;

@Controller
public class RecensioneController {
	
	@Autowired RecensioneService recensioneService;
	@Autowired LibroService libroService;
	
	/*mostra tutte le recensioni di un dato libro*/
	@GetMapping("/recensione/{idLibro}")
	public String mostraRecensioni(@PathVariable("idLibro") Long id, Model model) {
		Libro libro= this.libroService.getLibroById(id);
		model.addAttribute("recensioni", libro.getRecensioni());
		model.addAttribute("libro", libro);
		return "recensioniDiLibro.html";
	}
	
	@GetMapping("/formNuovaRecensione/{idLibro}")				//non so se è necessario l'id del libro
	public String formNuovaRecensione(@PathVariable("idLibro") Long id, Model model) {
		model.addAttribute("recensione", new Recensione());
		model.addAttribute("libro", this.libroService.getLibroById(id));
		return "formNuovaRecensione.html";
	}
	
	@PostMapping("/recensione/{idLibro}")												//l'ordine è importante!!!!
	public String nuovaRecensione (@PathVariable("idLibro") Long id, @Valid @ModelAttribute("recensione") Recensione recensione, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors())
			return "formNuovaRecensione.html";
		else {
			Libro libro= this.libroService.getLibroById(id);
			recensione.setLibro(libro);		
			libro.getRecensioni().add(recensione);
			this.recensioneService.save(recensione);
			model.addAttribute("recensione", recensione);
			return "redirect:/recensione/" + libro.getId();
		}
	}
	
	//l'ultima funzione non funziona
	//TODO: valuta se creare un metodo per visualizzare la singola recensione dall'elenco delle recensioni di un libro (magari cliccando sul titolo)
	//cerca di capire dove devono essere create le recensioni
	//occhio al fatto che quando crei una nuova recensione di un libro essa deve essere inserita nella lista del libro!!!!
	//non sono elementi "indipendenti" come Libro e Autore che vengono soltanto inseriti nel db!!! (la Recensione ha anche un'associazione fissa!)

}
