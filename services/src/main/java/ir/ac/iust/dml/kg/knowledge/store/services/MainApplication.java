package ir.ac.iust.dml.kg.knowledge.store.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * Entry point of application
 */
@SpringBootApplication
@ImportResource("classpath:web-services-def.xml")
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
