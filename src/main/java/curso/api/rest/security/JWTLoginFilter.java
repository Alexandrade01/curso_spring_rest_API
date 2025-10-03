package curso.api.rest.security;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import curso.api.rest.model.Cliente;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/* Estabelece o nosso gerenciador de token*/
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	/* Configurando o gerenciador de autenticacao */
	protected JWTLoginFilter(String url, AuthenticationManager authenticationManager) {

		/* Obriga a autenticar a url */
		super(new AntPathRequestMatcher(url));

		setAuthenticationManager(authenticationManager);

	}
	
	//retorna o usuario ao processar a autenticacao
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws StreamReadException, DatabindException, IOException {
		
		/* esta pegando o token para validar*/
		Cliente client = new ObjectMapper().readValue(request.getInputStream(), Cliente.class);
		
		/* retorna o usuario, login e acesso*/
		return getAuthenticationManager()
				.authenticate(new UsernamePasswordAuthenticationToken(client.getLogin(), client.getSenha()));
		
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		new JWTTokenAutenticacaoService().addAuthentication(response, authResult.getName());
	}

}
