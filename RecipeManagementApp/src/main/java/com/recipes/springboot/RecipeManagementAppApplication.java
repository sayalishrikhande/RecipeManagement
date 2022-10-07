package com.recipes.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class RecipeManagementAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipeManagementAppApplication.class, args);
	}

}
