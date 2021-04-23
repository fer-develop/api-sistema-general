package com.sistema.general.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.general.entity.Login;
import com.sistema.general.entity.Response;
import com.sistema.general.entity.Usuarios;
import com.sistema.general.service.UsuariosService;

import org.slf4j.*;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuariosController {
	
	Logger logger = LoggerFactory.getLogger(UsuariosController.class);
	
	@Autowired
	UsuariosService usuariosService;
	
	@GetMapping()
	public Response getUsuarios() throws Exception{
		
		try {
	
			logger.info("Iniciando Servicio: getUsuarios");		
			return new Response(1, 
								"Se obtuvieron los datos de los usuarios", 
								usuariosService.getUsuarios()
							   );
	
		}
		catch(Exception e) {
			logger.error(e.toString());		
			return new Response(-1, "Ocurrio un error al obtener usuarios.", null);
		}
	
	}
	
	@PostMapping("/login")
	public Response login(@RequestBody Usuarios usuarioData) throws Exception{
		
		try {
			
			logger.info("Iniciando Servicio: login");
			Login usuario = usuariosService.login(usuarioData.getEmail(), usuarioData.getPassword());
			
			
			if (usuario != null) {
				return new Response(1, 
						"Bienvenido " + usuario.getNombre(), 
						usuariosService.login(usuarioData.getEmail(), 
											  usuarioData.getPassword())
					   );
			} else {
				return new Response(0, 
						"El email o contraseña son incorrectos.", 
						usuariosService.login(usuarioData.getEmail(), 
											  usuarioData.getPassword())
					   );
			}
				
			
			
		}
		catch(Exception e) {
			logger.error(e.toString());	
			return new Response(-1, "Ocurrio un error al obtener usuarios.", null);
		}
	
	}
	
	@PostMapping()
	public Response postUsuario(@RequestBody Usuarios userData) throws Exception{
		
		try {
			
			usuariosService.postUsuario(userData);
			return new Response(1, "Se registro el usuario correctamente.", null);
		
		}
		catch(Exception e) {
			return new Response(-1, "Ocurrio un error al obtener usuarios.", null);
		}
	
	}
	
	@PutMapping
	public Response putUsuario(@RequestBody Usuarios userData) throws Exception{
		
		try {
			
			/* */
			
			return new Response(1, "1", List.of("hola"));
		}
		catch(Exception e) {
			return new Response(-1, "Ocurrio un error al obtener usuarios.", null);
		}
	
	}

}
