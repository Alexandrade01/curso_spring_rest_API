package curso.api.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


//@ComponentScan(basePackages = {"curso.*"})
//@EnableJpaRepositories(basePackages = {"curso.api.rest.repository"})
//@EnableTransactionManagement
//@EnableWebMvc
//@RestController
//@EnableAutoConfiguration
@SpringBootApplication
@EntityScan(basePackages = {"curso.api.rest.model"})
public class CursospringrestApiApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(CursospringrestApiApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(CursospringrestApiApplication.class, args);
    }
}

