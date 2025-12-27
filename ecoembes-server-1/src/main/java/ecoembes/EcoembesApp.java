package ecoembes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories; 

@SpringBootApplication
@EnableJpaRepositories("ecoembes.dao") 
public class EcoembesApp {

    public static void main(String[] args) {
        SpringApplication.run(EcoembesApp.class, args);
    }
}