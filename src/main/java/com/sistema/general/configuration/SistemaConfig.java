package com.sistema.general.configuration;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sistema.general.table.Usuarios;
import com.sistema.general.repository.UsuariosRepository;


@Configuration
public class SistemaConfig {
	// Inserciones de Usuarios por Default
	@Bean
	CommandLineRunner commandLineRunner(UsuariosRepository repository) {
		return args -> {
			Usuarios alan = new Usuarios(
					"Alan",
					"Castillo",
					"Verdugo",
					"alan.castillo@gmail.com",
					"asdasdasd",
					"asda"
			);
			Usuarios jorge = new Usuarios(
					"Jorge",
					"Castillo",
					"Verdugo",
					"alan.castillo.uas@gmail.com",
					"asdasdasdasdasdasd",
					"asda"
			);
			
			repository.saveAll(List.of(alan, jorge));
		};
	}
	
	//Inserciones de opciones de menu por Default
	
}
