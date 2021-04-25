package com.sistema.general;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.sistema.general.entity.JWToken;

@SpringBootApplication
public class SistemaGeneralApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaGeneralApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<JWToken> filtroAutenticacionBean() {
		FilterRegistrationBean<JWToken> autenticacionBean = new FilterRegistrationBean<>();
		JWToken jwtoken = new JWToken();
		autenticacionBean.setFilter(jwtoken);
		autenticacionBean.addUrlPatterns("/api/v1/menu/*");
		//autenticacionBean.addUrlPatterns("/api/v1/usuarios/obtener");
		autenticacionBean.addUrlPatterns("/api/v1/usuarios/actualizar/*");
		return autenticacionBean;
	}
	
}
