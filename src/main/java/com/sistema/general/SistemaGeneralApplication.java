package com.sistema.general;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.sistema.general.entity.JWToken;


@SpringBootApplication
public class SistemaGeneralApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaGeneralApplication.class, args);
	}

	
	
}
