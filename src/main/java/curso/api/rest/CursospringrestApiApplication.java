package curso.api.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@ComponentScan(basePackages = {"curso.*"})
//@EnableJpaRepositories(basePackages = {"curso.api.rest.repository"})
//@EnableTransactionManagement
//@EnableWebMvc
//@RestController
//@EnableAutoConfiguration
@SpringBootApplication
@EntityScan(basePackages = { "curso.api.rest.model" })
public class CursospringrestApiApplication extends SpringBootServletInitializer implements WebMvcConfigurer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(CursospringrestApiApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(CursospringrestApiApplication.class, args);
	}
	
	//Mapeamento Global que reflete a todo o sistema
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		
//		registry.addMapping("/**"); // liberação global de todos os endpoints - cors
		
		registry.addMapping("/cliente/**").allowedMethods("*").allowedOrigins("*"); // liberação global de rest cliente - cors
		
//		registry.addMapping("/cliente/**").allowedMethods("POST", "PUT","DELETE"); // liberação global de DETERMINADAS REQUISIÇÕES cliente - cors - 
		
		// liberação global de DETERMINADAS REQUISIÇÕES e determinados urls - cliente - cors -
		
//		registry.addMapping("/cliente/**").allowedMethods("POST", "PUT","DELETE").allowedOrigins("www.cliente40.com.br","www.clienteABC.com.br"); 
	}
}
