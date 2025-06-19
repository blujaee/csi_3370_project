package com.example.spring_boot_thymeleaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@Controller
public class SpringBootThymeleafApplication {
    static {
        // load .env into system properties for both app and tests
        Dotenv dotenv = Dotenv.configure()
                              .filename(".env")
                              .ignoreIfMissing()
                              .load();

        System.setProperty("JDBC_DATABASE_URL",      dotenv.get("JDBC_DATABASE_URL"));
        System.setProperty("JDBC_DATABASE_USERNAME", dotenv.get("JDBC_DATABASE_USERNAME"));
        System.setProperty("JDBC_DATABASE_PASSWORD", dotenv.get("JDBC_DATABASE_PASSWORD"));
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootThymeleafApplication.class, args);
	}
}
