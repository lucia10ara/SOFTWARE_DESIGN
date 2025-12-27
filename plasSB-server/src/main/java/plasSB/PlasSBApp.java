package plasSB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories; 

@SpringBootApplication
@EnableJpaRepositories("plasSB.dao") 
public class PlasSBApp {

    public static void main(String[] args) {
        SpringApplication.run(PlasSBApp.class, args);
    }
}