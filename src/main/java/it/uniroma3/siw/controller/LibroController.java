package it.uniroma3.siw.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
		Libro libro=this.libroService.getLibroById(id);
		Set<Autore> autori= libro.getAutori();
		model.addAttribute("libro", libro);
		model.addAttribute("autori", autori);
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
	public String aggiornaLibri(Model model) {
		model.addAttribute("libri", this.libroService.getAllLibri());
		return "aggiornaLibri.html";
	}
	
	@GetMapping("/eliminaLibro/{id}")
	public String eliminaLibro(@PathVariable ("id") Long id, Model model) {		//TODO: guarda eliminaAutore-> valuta se adottare la stessa soluzione
		this.libroService.deleteLibroById(id);									//funziona grazie al mappedBy in autore (l'owner della tabella di join è Libro (libro_autori)
		model.addAttribute("libri", this.libroService.getAllLibri());
		return "aggiornaLibri.html";
	}
	
	@GetMapping("/modificaLibro/{id}")
	public String modificaLibro(@PathVariable("id") Long id, Model model) {
		Libro libro=this.libroService.getLibroById(id);
		model.addAttribute("libro", libro);
		model.addAttribute("autori", libro.getAutori());
		
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
	
	@GetMapping("/modificaAutoriDiLibro/{id}")
	public String modificaAutoriDiLibro(@PathVariable("id") Long id, Model model) {
		Libro libro= this.libroService.getLibroById(id);
		Set<Autore> autori= libro.getAutori();
		
		model.addAttribute("libro", libro);
		model.addAttribute("autori", autori);
		
		return "modificaAutoriDiLibro.html";
	}
	
	@Transactional
	@GetMapping("/rimuoviAutoreDaLibro/{idLibro}/{idAutore}")
	public String rimuoviAutoreDaLibro(@PathVariable("idLibro") Long idLibro, @PathVariable Long idAutore, Model model) {
		Libro libro= libroService.getLibroById(idLibro);
		Autore autore= autoreService.getAutoreById(idAutore);
//		Set<Autore> autoriDiLibro= libro.getAutori();
//		List<Libro> libriDiAutore= autore.getLibri();
		
		libro.getAutori().remove(autore);
		autore.getLibri().remove(libro);
		
		this.libroService.save(libro);
		this.autoreService.save(autore);
		
//		model.addAttribute("libro", libro);
//		model.addAttribute("autori", this.autoriDaAggiungere(idLibro));
		
		
		return "redirect:/modificaAutoriDiLibro/"+ libro.getId();
	}
	
	@GetMapping("/aggiungiAltriAutoriALibro/{id}")
	public String aggiungiAltriAutoriALibro(@PathVariable("id") Long id, Model model) {
		model.addAttribute("libro", this.libroService.getLibroById(id));
		model.addAttribute("autori", this.autoriDaAggiungere(id));
		return "aggiungiAltriAutoriALibro.html";
	}
	
	@GetMapping("/aggiungiAutoreALibro/{idLibro}/{idAutore}")
	public String aggiungiAutoreALibro(@PathVariable("idLibro") Long idLibro, @PathVariable("idAutore") Long idAutore, Model model) {
		Libro libro= this.libroService.getLibroById(idLibro);
		Autore autore= this.autoreService.getAutoreById(idAutore);
		libro.getAutori().add(autore);
		autore.getLibri().add(libro);
		
		this.libroService.save(libro);
		this.autoreService.save(autore);
		
		model.addAttribute("libro", libro);
		model.addAttribute("autori", this.autoriDaAggiungere(idLibro));
		
		return "redirect:/aggiungiAltriAutoriALibro/" + libro.getId();
	}
	

	
	/*ritorna la lista degli autori che non sono associati al libro di id idLibro*/
	//TODO: ha senso cambiare il tipo di ritorno da List a Set?? (così funziona)
	private List<Autore> autoriDaAggiungere(Long idLibro){
		List<Autore> autori= new ArrayList<>();
		for(Autore a: this.autoreService.findAutoriNonInLibro(idLibro)) {
			autori.add(a);
		}
		
		return autori;
	}
}
