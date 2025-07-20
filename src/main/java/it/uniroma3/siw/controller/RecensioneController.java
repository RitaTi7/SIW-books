package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.service.LibroService;
import it.uniroma3.siw.service.RecensioneService;

@Controller
public class RecensioneController {
	
	@Autowired RecensioneService recensioneService;
	@Autowired LibroService libroService;
	
	/*mostra tutte le recensioni di un dato libro*/
	@GetMapping("/recensione/{idLibro}")
	public String mostraRecensioni(@PathVariable("idLibro") Long id, Model model) {
		model.addAttribute("recensioni", this.libroService.getLibroById(id).getRecensioni());
		return "recensioniDiLibro.html";
	}
	
	@GetMapping("/formNuovaRecensione/{idLibro}")				//non so se è necessario l'id del libro
	public String formNuovaRecensione(@PathVariable("idLibro") Long id, Model model) {
		model.addAttribute("recensione", new Recensione());
		return "formNuovaRecensione.html";
	}
	
	@PostMapping("/nuovaRecensione/{idLibro}")
	public String nuovaRecensione(@PathVariable("idLibro") Long id, Model model) {
		return "";
	}

}
