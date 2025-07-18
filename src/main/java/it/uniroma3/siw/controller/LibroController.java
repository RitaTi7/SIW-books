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
import it.uniroma3.siw.service.AutoreService;
import it.uniroma3.siw.service.LibroService;
import jakarta.validation.Valid;

@Controller
public class LibroController {
	
	@Autowired LibroService libroService;
	@Autowired AutoreService autoreService;
	
	@GetMapping("/libro/{id}")
	public String mostraLibro(@PathVariable("id") Long id, Model model) {
		/*Libro libro= this.libroService.getLibroById(id);
		if(libro==null)
			return "errore.html";
		else {
			model.addAttribute("libro", libro);
			return "libro.html";
		}*/
		
		model.addAttribute("libro", this.libroService.getLibroById(id));
		return "libro.html";
	}
	
	@GetMapping("/libro")
	public String mostraLibri(Model model) {
		model.addAttribute("libri", this.libroService.getAllLibri());
		
		return "libri.html";
	}
	
	@GetMapping("/")
	public String home(Model model) {
		return "index.html";
	}
	
	@GetMapping("/formNuovoLibro")
	public String formNuovoLibro(Model model) {
		model.addAttribute("libro", new Libro());
		return "formNuovoLibro.html";
	}
	
	@PostMapping("/libro")
	public String nuovoLibro(@Valid @ModelAttribute("libro") Libro libro, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			return "formNuovoLibro";
		}
		else {
			this.libroService.save(libro);
			model.addAttribute("libro", libro);
			return "redirect:libro/" + libro.getId();
		}
	}
	
	@GetMapping("/aggiornaLibri")
	public String aggiornLibri(Model model) {
		model.addAttribute("libri", this.libroService.getAllLibri());
		return "aggiornaLibri.html";
	}
	
	@GetMapping("/eliminaLibro/{id}")
	public String eliminaLibro(@PathVariable ("id") Long id, Model model) {
		this.libroService.deleteLibroById(id);
		model.addAttribute("libri", this.libroService.getAllLibri());
		return "aggiornaLibri.html";
	}
	
	@GetMapping("/modificaLibro/{id}")
	public String modificaLibro(@PathVariable("id") Long id, Model model) {
		model.addAttribute("libro", this.libroService.getLibroById(id));
		return "modificaLibro.html";
	}
	
	@GetMapping("/formModificaLibro/{id}")
	public String formModificaLibro(@PathVariable("id") Long id, Model model) {
		model.addAttribute("libro", this.libroService.getLibroById(id));
		return "formModificaLibro.html";
	}
	
	@PostMapping("/modificaLibro/{id}")
	public String modificaLibro(@PathVariable("id") Long id, @Valid @ModelAttribute("libro") Libro libroModificato, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors())
			return "formModificaLibro.html";
		else {
			Libro libroEsistente= this.libroService.getLibroById(id);
			libroEsistente.setTitolo(libroModificato.getTitolo());
			libroEsistente.setAnno(libroModificato.getAnno());
			this.libroService.save(libroEsistente);
			model.addAttribute("libri", this.libroService.getAllLibri());
			return "aggiornaLibri.html";
		}
	}
	
	@GetMapping("/modifcaAutoriDiLibro/{id}")
	public String modificaAutoriDiLibro(@PathVariable("id") Long id, Model model) {
		model.addAttribute("autori", this.autoreService.getAllAutori());
		return "";
	}
	
}
