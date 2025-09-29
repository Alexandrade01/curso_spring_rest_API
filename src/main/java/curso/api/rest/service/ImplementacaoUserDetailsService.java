package curso.api.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import curso.api.rest.model.Cliente;
import curso.api.rest.repository.ClienteRepository;

@Service
public class ImplementacaoUserDetailsService implements UserDetailsService {

	
	public ImplementacaoUserDetailsService(ClienteRepository clienteRepository) {
		super();
		this.clienteRepository = clienteRepository;
		
	}

	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//Consulta no banco o cliente
		Cliente cliente = clienteRepository.findUserByLogin(username);
		
		if(cliente.equals(null)) {
			throw new UsernameNotFoundException("Cliente nao encontrado !");
			
		}
		
		//o novo objeto user ira ser importante para validacoes do spring security
		
		return new User(cliente.getLogin(), cliente.getPassword(), cliente.getAuthorities());
	}

}
