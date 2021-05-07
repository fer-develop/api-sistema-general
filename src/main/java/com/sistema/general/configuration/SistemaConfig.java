package com.sistema.general.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sistema.general.table.Menus;
import com.sistema.general.table.MenusUsuarios;
import com.sistema.general.table.SubMenus;
import com.sistema.general.table.SubMenusUsuarios;
import com.sistema.general.table.Usuarios;
import com.sistema.general.repository.MenusRepository;
import com.sistema.general.repository.MenusUsuariosRepository;
import com.sistema.general.repository.SubMenusRepository;
import com.sistema.general.repository.SubMenusUsuariosRepository;
import com.sistema.general.repository.UsuariosRepository;


@Configuration
public class SistemaConfig {
	// Inserciones de Usuarios por Default

	@Bean
	CommandLineRunner commandLineRunner(UsuariosRepository usuariosrepository, 
										MenusRepository menusRepository, 
										SubMenusRepository submenuRepository,
										SubMenusUsuariosRepository submenuUsuariosRepository,
										MenusUsuariosRepository menusUsuariosRepository) {
		return args -> {
			// Registrar Usuarios prueba
			Usuarios alan = new Usuarios(
					"Alan Fernando",
					"Castillo",
					"Verdugo",
					"fer_dev@outlook.com",
					"$2a$05$Pf12mYYB6id2mx.QNoD6relt6CdGSg23TgUBe/zTagSQcIJ7ym4xu",
					"9334c3a7-ac53-46d6-b1d3-377dc3706388.jpg",
					"ADMIN_ROLE"
			);
			
			Usuarios usuarioReturned = usuariosrepository.save(alan);
			
			Menus sistema  = new Menus(
					"Sistema",
					"mdi mdi-gauge"
			);
			
			Menus menuReturned = menusRepository.save(sistema);
			
			SubMenus optSubMenu = new SubMenus("Prueba","/prueba", menuReturned);
			
			SubMenus submenuReturned = submenuRepository.save(optSubMenu);
			
			MenusUsuarios usuarioMenu = new MenusUsuarios(
					menuReturned,
					usuarioReturned,
					true
			);
			
			SubMenusUsuarios submenuusuarioasd = new SubMenusUsuarios(
					submenuReturned, 
					usuarioReturned, 
					true
			);
			
			MenusUsuarios menuUsuario = menusUsuariosRepository.save(usuarioMenu);
			
			SubMenusUsuarios subMenuUsuario = submenuUsuariosRepository.save(submenuusuarioasd);
			
			
			//for (Menus menu : menuReturned) {
				
				
				
				
				//listSubmenuPrueba.add(optSubMenu);
				//
			//}
			
		};
	}
	
	
}
