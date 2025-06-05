package com.example.spring_boot_thymeleaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@Controller
public class SpringBootThymeleafApplication {
	public static void main(String[] args) {
		// 1) Load .env from your project root
        Dotenv dotenv = Dotenv.configure()
                              .filename(".env")
                              .load();

        // 2) Push each JDBC variable into System properties
        System.setProperty("JDBC_DATABASE_URL",      dotenv.get("JDBC_DATABASE_URL"));
        System.setProperty("JDBC_DATABASE_USERNAME", dotenv.get("JDBC_DATABASE_USERNAME"));
        System.setProperty("JDBC_DATABASE_PASSWORD", dotenv.get("JDBC_DATABASE_PASSWORD"));

        // 3) Now launch Spring Boot
        SpringApplication.run(SpringBootThymeleafApplication.class, args);
	}
}