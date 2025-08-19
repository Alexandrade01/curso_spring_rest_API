package curso.api.rest.repository;

import org.springframework.data.repository.CrudRepository;

import curso.api.rest.model.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {

}
