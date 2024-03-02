package com.example.ScrumWise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@SpringBootApplication
public class ScrumWiseApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScrumWiseApplication.class, args);
	}
//test
}
