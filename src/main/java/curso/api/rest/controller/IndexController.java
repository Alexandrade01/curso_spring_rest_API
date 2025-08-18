package curso.api.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController /* Arquitetura REST */
@RequestMapping(value="/usuario")
public class IndexController {
	
	/* Serviço RESTful */
	/* defaultValue -> valor default caso não seja enviado nenhum valor */
	@GetMapping(value="pesquisaNome/",produces= "application/json")
	public ResponseEntity init(@RequestParam (defaultValue = "Nome não identificado", required = true) String nome , 
			@RequestParam  (defaultValue = "Login não identificado", required = true) String Login) {
		
		System.out.println("Parametro recebido " + nome);
		System.out.println("Login  recebido " + Login);

		
		return new ResponseEntity("Olá Rest Spring Boot" + "\n" + "Seu nome é ".concat(nome) + "\n"
				+ "Seu Login é ".concat(Login), HttpStatus.OK);
	}

}
