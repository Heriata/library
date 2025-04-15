package deveducate.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class LibraryApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(LibraryApplication.class, args);
    }
}
