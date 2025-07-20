package it.uniroma3.siw.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Autore;
import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.service.AutoreService;
import it.uniroma3.siw.service.LibroService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
public class AutoreController {

    private final LibroService libroService;
	
	@Autowired AutoreService autoreService;

    AutoreController(LibroService libroService) {
        this.libroService = libroService;
    }
	
	@GetMapping("/autore/{id}")
	public String mostraAutore(@PathVariable("id") Long id, Model model) {
//		Autore autore= this.autoreService.getAutoreById(id);
//		if(autore==null)
//			return "errore.html";
//		else {
//			model.addAttribute("autore", autore);
//			return "autore.html";
//		}
		Autore autore=this.autoreService.getAutoreById(id);
		model.addAttribute("autore", autore);
		model.addAttribute("libri", autore.getLibri());
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
		Autore autore= this.autoreService.getAutoreById(id);
		for(Libro libro: autore.getLibri()) {
			libro.getAutori().remove(autore);
			this.libroService.save(libro);
		}
		this.autoreService.deleteAutoreById(id);
		model.addAttribute("autori", this.autoreService.getAllAutori());
		return "aggiornaAutori.html";
	}
	
	@GetMapping("/modificaAutore/{id}")
	public String modificaAutore(@PathVariable("id") Long id, Model model) {
		Autore autore= this.autoreService.getAutoreById(id);
		model.addAttribute("autore", autore);
		model.addAttribute("libri", autore.getLibri());
		return "modificaAutore.html";
	}
	
	@GetMapping("/formModificaAutore/{id}")
	public String formModificaAutore(@PathVariable("id") Long id, Model model) {
		model.addAttribute("autore", this.autoreService.getAutoreById(id));
		return "formModificaAutore.html";
	}
	
	@PostMapping("/modificaAutore/{id}")
	public String modificaAutore(@PathVariable("id") Long id, @Valid @ModelAttribute("autore") Autore autoreModificato, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors())
			return "formModificaAutore.html";
		else {
			Autore autoreEsistente= this.autoreService.getAutoreById(id);
			autoreEsistente.setNome(autoreModificato.getNome());
			autoreEsistente.setCognome(autoreModificato.getCognome());
			autoreEsistente.setDataNascita(autoreModificato.getDataNascita());
			autoreEsistente.setDataMorte(autoreModificato.getDataMorte());
			autoreEsistente.setNazionalita(autoreModificato.getNazionalita());
			this.autoreService.save(autoreEsistente);
			model.addAttribute("autori", this.autoreService.getAllAutori());
			return "aggiornaAutori.html";
		}
	}
	
	@GetMapping("/modificaLibriDiAutore/{id}")
	public String modificaLibriDiAutore(@PathVariable("id") Long id, Model model) {
		Autore autore= this.autoreService.getAutoreById(id);
		
		model.addAttribute("autore", autore);
		model.addAttribute("libri", autore.getLibri());
		
		return "modificaLibriDiAutore.html";
	}
	
	@Transactional
	@GetMapping("/rimuoviLibroDaAutore/{idAutore}/{idLibro}")
	public String rimuoviLibroDaAutore(@PathVariable("idAutore") Long idAutore, @PathVariable("idLibro") Long idLibro, Model model) {
		Autore autore= this.autoreService.getAutoreById(idAutore);
		Libro libro= this.libroService.getLibroById(idLibro);
		
		autore.getLibri().remove(libro);
		libro.getAutori().remove(autore);
		
		this.autoreService.save(autore);
		this.libroService.save(libro);
		
		return "redirect:/modificaLibriDiAutore/" + autore.getId();
	}
	
	@GetMapping("/aggiungiAltriLibriAdAutore/{id}")
	public String aggiungiAltriLibriAdAutore(@PathVariable("id") Long id, Model model) {
		model.addAttribute("autore", this.autoreService.getAutoreById(id));
		model.addAttribute("libri", this.libriDaAggiungere(id));
		return "aggiungiAltriLibriAdAutore.html";
	}
	
	@GetMapping("/aggiungiLibroAdAutore/{idAutore}/{idLibro}")
	public String aggiungiLibroAdAutore(@PathVariable("idAutore") Long idAutore, @PathVariable("idLibro") Long idLibro, Model model) {
		Autore autore= this.autoreService.getAutoreById(idAutore);
		Libro libro= this.libroService.getLibroById(idLibro);
		
		autore.getLibri().add(libro);
		libro.getAutori().add(autore);
		
		this.autoreService.save(autore);
		this.libroService.save(libro);
		
		model.addAttribute("autore", autore);
		model.addAttribute("libri", this.libriDaAggiungere(idAutore));
		
		return "redirect:/aggiungiAltriLibriAdAutore/" + autore.getId();
	}
	
	private List<Libro> libriDaAggiungere(Long idAutore){
		List<Libro> libri= new ArrayList<>();
		
		for(Libro l: this.libroService.findLibriNonInAutore(idAutore)) {
			libri.add(l);
		}
		return libri;
	}
}
