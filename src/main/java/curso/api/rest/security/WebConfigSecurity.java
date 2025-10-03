package curso.api.rest.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import curso.api.rest.service.ImplementacaoUserDetailsService;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity implements SecurityFilterChain  {

	@Autowired
	private ImplementacaoUserDetailsService implementacaoUserDetailsService;

	@Bean
	 SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
		
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/").permitAll() // Permite acesso à página inicial
						.anyRequest().authenticated() // Requer autenticação para outras URLs
				)
				
				.logout(logout -> logout
						.logoutUrl("/logout") // URL de logout
						.logoutSuccessUrl("/index") // Redireciona após logout
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // Mapeia logout
				)
				
				.formLogin(Customizer.withDefaults()) // Login padrão
				.httpBasic(Customizer.withDefaults()); // HTTP Basic se necessário

		/* Filtra as requisições de login para autenticação */

		/*
		 * Filtra demais requisições para verificar a presença do TOKEN JWT no HEADER
		 * HTTP
		 */

		return http.build();
	}

	@Bean
	 AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(implementacaoUserDetailsService).passwordEncoder(passwordEncoder()).and().build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public boolean matches(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Filter> getFilters() {
		// TODO Auto-generated method stub
		return null;
	}
}
