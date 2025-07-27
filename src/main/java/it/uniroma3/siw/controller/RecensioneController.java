package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.LibroService;
import it.uniroma3.siw.service.RecensioneService;
import it.uniroma3.siw.service.UserService;
import jakarta.validation.Valid;

@Controller
public class RecensioneController {
	
	@Autowired RecensioneService recensioneService;
	@Autowired LibroService libroService;
	@Autowired CredentialsService credentialsService;
	@Autowired UserService userService;
	
	/*mostra tutte le recensioni di un dato libro
	@GetMapping("/user/recensione/{idLibro}")
	public String mostraRecensioni(@PathVariable("idLibro") Long id, Model model) {
		Libro libro= this.libroService.getLibroById(id);
		model.addAttribute("recensioni", libro.getRecensioni());
		model.addAttribute("libro", libro);
		return "common/recensioniDiLibro.html";
	}*/
	
	@GetMapping("/user/formNuovaRecensione/{idLibro}")				//non so se è necessario l'id del libro
	public String formNuovaRecensione(@PathVariable("idLibro") Long id, Model model) {
		model.addAttribute("recensione", new Recensione());
		model.addAttribute("libro", this.libroService.getLibroById(id));
		return "user/formNuovaRecensione.html";
	}
	
	@PostMapping("/user/recensione/{idLibro}")												//l'ordine è importante!!!!
	public String nuovaRecensione (@PathVariable("idLibro") Long id, @Valid @ModelAttribute("recensione") Recensione recensione, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("libro", this.libroService.getLibroById(id));
			return "user/formNuovaRecensione.html";	
		}
		else {
			
			UserDetails userDetails= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Credentials credentials= this.credentialsService.getCredentials(userDetails.getUsername());
			User user= credentials.getUser();
			
//			recensione.setUsernameUtente(credentials.getUsername());
			recensione.setUser(user);
			user.getRecensioni().add(recensione);
			
		    Libro libro= this.libroService.getLibroById(id);
			recensione.setLibro(libro);		
			libro.getRecensioni().add(recensione);
			this.recensioneService.save(recensione);
			return "redirect:/libro/" + libro.getId();
		}
	}
	
	@GetMapping("/eliminaRecensione/{idRecensione}/{idLibro}")
	public String eliminaRecensione(@PathVariable("idRecensione") Long idRec, @PathVariable("idLibro") Long idLibro, Model model) {
		Recensione recensione= this.recensioneService.getRecensioneById(idRec);
		Libro libro= this.libroService.getLibroById(idLibro);
		UserDetails userDetails= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials= this.credentialsService.getCredentials(userDetails.getUsername());
		User user= credentials.getUser();
		
		if(!credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
			if( ! (credentials.getRole().equals(Credentials.USER_ROLE) && user.equals(recensione.getUser()))) {
				return "redirect:/libro/" + libro.getId();
			}	
		}
		
		libro.getRecensioni().remove(recensione);
		this.libroService.save(libro);
		
		user.getRecensioni().remove(recensione);
		this.userService.saveUser(user);
		
		this.recensioneService.deleteRecensioneById(idRec);
		
		return "redirect:/libro/" + libro.getId();
	}
	
	
	
	
	//TODO: valuta se creare un metodo per visualizzare la singola recensione dall'elenco delle recensioni di un libro (magari cliccando sul titolo)

}
