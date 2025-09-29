package curso.api.rest.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import curso.api.rest.model.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {
	
	@Query("select u from Cliente u where u.login = ?1")
	Cliente findUserByLogin(String login);

}
