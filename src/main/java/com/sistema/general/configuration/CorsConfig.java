package com.sistema.general.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sistema.general.entity.JWToken;

@Configuration
public class CorsConfig {
	
	@Bean
	public FilterRegistrationBean<JWToken> filtroAutenticacionBean() {
		FilterRegistrationBean<JWToken> autenticacionBean = new FilterRegistrationBean<>();
		JWToken jwtoken = new JWToken();
		autenticacionBean.setFilter(jwtoken);
		//autenticacionBean.addUrlPatterns("/api/v1/menu/*");
		//autenticacionBean.addUrlPatterns("/api/v1/usuarios/obtener");
		autenticacionBean.addUrlPatterns("/api/v1/usuarios/renovar");
		//autenticacionBean.addUrlPatterns("/api/v1/usuarios/actualizar/*");
		return autenticacionBean;
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		//"http://192.168.3.6:4200", 
		String[] endpointsPermitidos = {"http://localhost:4200", "http://192.168.3.6:4200"};
		
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
				.allowedMethods("GET", "POST", "PUT", "DELETE")
				.allowedHeaders("*")
				.allowedOrigins("*");
			}
		};
	}
	
	
	
}
