package curso.api.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import curso.api.rest.model.Cliente;
import curso.api.rest.repository.ClienteRepository;

@RestController /* Arquitetura REST */
@RequestMapping(value = "/usuario")
public class IndexController {

	@Autowired
	private ClienteRepository clienteRepository;

	@Value("${spring.application.name}")
	private String appName;

	/* Serviço RESTful */
	/* defaultValue -> valor default caso não seja enviado nenhum valor */
	/* Consulta por ID */
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Object> init(@PathVariable Long id) {

		Optional<Cliente> client = clienteRepository.findById(id);

		return new ResponseEntity<Object>(client, HttpStatus.OK);

	}

	/* Consulta todos */
	@GetMapping(value = "/" , produces="application/json")
	public ResponseEntity<List<Cliente>> findAll() {

		List<Cliente> listClient = (List<Cliente>) clienteRepository.findAll();

		return new ResponseEntity<List<Cliente>>(listClient, HttpStatus.OK);
	}

}
