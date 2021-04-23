package com.sistema.general.configuration;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sistema.general.entity.Usuarios;
import com.sistema.general.repository.UsuariosRepository;


@Configuration
public class UsuariosConfig {
	@Bean
	CommandLineRunner commandLineRunner(UsuariosRepository repository) {
		return args -> {
			Usuarios alan = new Usuarios(
					"Alan",
					"Castillo",
					"Verdugo",
					"alan.castillo@gmail.com",
					"asdasdasd",
					"asda",
					LocalDate.now()
			);
			Usuarios jorge = new Usuarios(
					"Jorge",
					"Castillo",
					"Verdugo",
					"alan.castillo.uas@gmail.com",
					"asdasdasdasdasdasd",
					"asda",
					LocalDate.now()
			);
			
			repository.saveAll(List.of(alan, jorge));
		};
	}
}
