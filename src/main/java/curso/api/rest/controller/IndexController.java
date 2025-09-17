package curso.api.rest.controller;

import java.awt.PageAttributes.MediaType;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import curso.api.rest.CursospringrestApiApplication;
import curso.api.rest.model.Cliente;
import curso.api.rest.model.Telefone;
import curso.api.rest.repository.ClienteRepository;

@RestController /* Arquitetura REST */
@RequestMapping(value = "/cliente")
public class IndexController {

	private final CursospringrestApiApplication cursospringrestApiApplication;

	@Autowired
	private ClienteRepository clienteRepository;

	IndexController(CursospringrestApiApplication cursospringrestApiApplication) {
		this.cursospringrestApiApplication = cursospringrestApiApplication;
	}

	/* Serviço RESTful */
	/* defaultValue -> valor default caso não seja enviado nenhum valor */
	/* Consulta por ID */
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Object> findById(@PathVariable Long id) {

		Optional<Cliente> client = clienteRepository.findById(id);

		return new ResponseEntity<Object>(client, HttpStatus.OK);

	}

	/* Consulta todos */
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<List<Cliente>> findAll() {

		List<Cliente> listClient = (List<Cliente>) clienteRepository.findAll();

		return new ResponseEntity<List<Cliente>>(listClient, HttpStatus.OK);
	}

	@PostMapping(value = "/", produces = "application/json")
	public ResponseEntity<Cliente> cadastroCliente(@RequestBody Cliente cliente) {

		for (Telefone telefone : cliente.getTelefones()) {

			telefone.setCliente(cliente);
		}

		Cliente clienteSalvo = clienteRepository.save(cliente);

		return new ResponseEntity<Cliente>(clienteSalvo, HttpStatus.CREATED);

	}

	@PutMapping(value = "/", produces = "application/json")
	public ResponseEntity<Cliente> atualizaCliente(@RequestBody Cliente cliente) {

		if (!cliente.getTelefones().isEmpty()) {
			for (Telefone telefone : cliente.getTelefones()) {

				telefone.setCliente(cliente);
			}
		}

//		for(int pos = 0; pos <cliente.getTelefones().size();pos++) {
//			
//			cliente.getTelefones().get(pos).setCliente(cliente);
//			
//		}

		Cliente clienteSalvo = clienteRepository.save(cliente);

		return new ResponseEntity<Cliente>(clienteSalvo, HttpStatus.CREATED);

	}

//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	@PutMapping(value = "/id/{id}", produces = "application/json")
//	public ResponseEntity<Cliente> atualizaClientebyId(@PathVariable Long id, @RequestBody Cliente cliente) {
//
//		Optional<Cliente> clientesPorId = clienteRepository.findById(id);
//
//		if (clientesPorId.isPresent()) {
//
//			clientesPorId.get().setConta(cliente.getConta());
//			clientesPorId.get().setDataNascimento(cliente.getDataNascimento());
//			clientesPorId.get().setLogin(cliente.getLogin());
//			clientesPorId.get().setSenha(cliente.getSenha());
//
//			Cliente clienteSalvo = clienteRepository.save(clientesPorId.get());
//
//			return new ResponseEntity<Cliente>(clienteSalvo, HttpStatus.CREATED);
//
//		}
//
//		else {
//			return new ResponseEntity("não encontrado", HttpStatus.NO_CONTENT);
//		}
//
//	}

	@DeleteMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Object> deletarClienteById(@PathVariable Long id) {

		clienteRepository.deleteById(id);

		return ResponseEntity.ok().build();

	}

}
