package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Autore;
import it.uniroma3.siw.service.AutoreService;
import jakarta.validation.Valid;

@Controller
public class AutoreController {
	
	@Autowired AutoreService autoreService;
	
	@GetMapping("/autore/{id}")
	public String mostraAutore(@PathVariable("id") Long id, Model model) {
//		Autore autore= this.autoreService.getAutoreById(id);
//		if(autore==null)
//			return "errore.html";
//		else {
//			model.addAttribute("autore", autore);
//			return "autore.html";
//		}
		
		model.addAttribute("autore", this.autoreService.getAutoreById(id));
		return "autore.html";
	}
	
	@GetMapping("/autore")
	public String mostraAutori(Model model) {
		model.addAttribute("autori", this.autoreService.getAllAutori());
		return "autori.html";
	}
	
	@GetMapping("/formNuovoAutore")
	public String formNuovoAutore(Model model) {
		model.addAttribute("autore", new Autore());
		return "formNuovoAutore.html";
	}
	
	@PostMapping("/autore")
	public String nuovoAutore(@Valid @ModelAttribute("autore") Autore autore, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			return "formNuovoAutore.html";
		}
		else {
			this.autoreService.save(autore);
			model.addAttribute("autore", autore);
			return "redirect:autore/" + autore.getId();
		}
	}
	
	@GetMapping("/aggiornaAutori")
	public String aggiornaAutori(Model model) {
		model.addAttribute("autori", this.autoreService.getAllAutori());
		return "aggiornaAutori.html";
	}
	
	@GetMapping("/eliminaAutore/{id}")
	public String eliminaAutore(@PathVariable("id") Long id, Model model) {
		this.autoreService.deleteAutoreById(id);
		model.addAttribute("autori", this.autoreService.getAllAutori());
		return "aggiornaAutori.html";
	}
	
	@GetMapping("/modificaAutore/{id}")
	public String modificaAutore(@PathVariable("id") Long id, Model model) {
		model.addAttribute("autore", this.autoreService.getAutoreById(id));
		return "modificaAutore.html";
	}
	
	@GetMapping("/formModificaAutore/{id}")
	public String formModificaAutore(@PathVariable("id") Long id, Model model) {
		model.addAttribute("autore", this.autoreService.getAutoreById(id));
		return "formModificaAutore.html";
	}
	
	@PostMapping("/modificaAutore/{id}")
	public String modificaLibro(@PathVariable("id") Long id, @Valid @ModelAttribute("autore") Autore autoreModificato, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors())
			return "formModificaAutore.html";
		else {
			Autore autoreEsistente= this.autoreService.getAutoreById(id);
			autoreEsistente.setNome(autoreModificato.getNome());
			autoreEsistente.setCognome(autoreModificato.getCognome());
			autoreEsistente.setDataNascita(autoreModificato.getDataNascita());
			//if(autoreModificato.getDataMorte()!=null)
			autoreEsistente.setDataMorte(autoreModificato.getDataMorte());
			autoreEsistente.setNazionalita(autoreModificato.getNazionalita());
			this.autoreService.save(autoreEsistente);
			model.addAttribute("autori", this.autoreService.getAllAutori());
			return "aggiornaAutori.html";
		}
	}

}
