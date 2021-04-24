package com.sistema.general.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.general.entity.Response;
import com.sistema.general.table.Usuarios;
import com.sistema.general.service.SistemaService;

import org.slf4j.*;

@RestController
@RequestMapping("/api/v1/usuarios")
public class SistemaController {
	
	Logger logger = LoggerFactory.getLogger(SistemaController.class);

	private final SistemaService sistemaService;

	@Autowired
	public SistemaController(SistemaService sistemaService) {
		this.sistemaService = sistemaService;
	}

	@GetMapping("/obtener")
	public Response getUsuarios() throws Exception{
		logger.info("Iniciando Servicio: getUsuarios");
		try {
			return sistemaService.getUsuarios();
		} catch(Exception e) {
			logger.error(e.toString());		
			return new Response(-1, "Ocurrio un error al obtener usuarios.");
		}
	}
	
	@PostMapping("/registrar")
	public Response postUsuario(@RequestBody Usuarios userData) throws Exception{
		logger.info("Iniciando Servicio: postUsuario");
		try {
			return sistemaService.postUsuario(userData);
		} catch(Exception e) {
			logger.error(e.toString());
			return new Response(-1, "Ocurrio un error al registrar usuario.");
		}
	}
	
	@PutMapping("/actualizar/{usuarioId}")
	public Response putUsuario(@PathVariable Long usuarioId, @RequestBody Usuarios userData) throws Exception{
		try {
			return sistemaService.putUsuario(usuarioId, userData);
		} catch(Exception e) {
			logger.error(e.toString());
			return new Response(-1, "Ocurrio un error al actualizar usuario.");
		}
	}

	@PostMapping("/login")
	public Response login(@RequestBody Usuarios usuarioData) throws Exception{
		logger.info("Iniciando Servicio: login");
		try {
			if (usuarioData.getEmail() != null && usuarioData.getPassword() != null) {
				return sistemaService.login(usuarioData.getEmail(), usuarioData.getPassword());
			} else {
				throw new Exception("Falta algun campo email o login.");
			}
		} catch(Exception e) {
			logger.error(e.toString());
			return new Response(-1, "Ocurrio un error al hacer login.");
		}
	}

}
