package ch.hearc.SaphirLion.sample;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.hearc.SaphirLion.sample.data.HelloWorld;


@Controller
//Cette annotation peut-être ajoutée sur la classe et aura pour effet de préfixer les url des méthodes par /test
//@RequestMapping("/test")
public class HelloController {

	
	@GetMapping(value = "/hello")
	//Equivalent à @RequestMapping(value = "/hello", method = RequestMethod.GET)
	public @ResponseBody String helloWorld() {
		return "Hello World!";
	}
	
	@GetMapping({"/hello2","/hello2/{nom}"})
	//Equivalent à @RequestMapping(value = {"/hello2","/hello2/{nom}"}, method = RequestMethod.GET)
	public @ResponseBody String helloWorld2(@PathVariable(required = false) String nom) {
		
		nom = defineName(nom);
		
		return "Hello World! " + nom;
	}

	
	@GetMapping(value = "/hello3")
	//Equivalent à @RequestMapping(value = "/hello3", method = RequestMethod.GET)
	public @ResponseBody String helloWorld3(@RequestParam(required = false) String nom) {
		
		nom = defineName(nom);
		
		return "Hello World! " + nom;
	}
	
	
	@GetMapping({"/hello4","/hello4/{nom}"})
	//Equivalent à @RequestMapping(value = {"/hello4","/hello4/{nom}"}, method = RequestMethod.GET)
	public @ResponseBody HelloWorld helloWorld4(@PathVariable(required = false) String nom) {
		
		nom = defineName(nom);
		
		return new HelloWorld(nom);
	}
	
	//Factorisation de la gestion du null
	private String defineName(String nom) {
		if(nom == null) {
			nom = "Inconnu!";
		}
		return nom;
	}
}