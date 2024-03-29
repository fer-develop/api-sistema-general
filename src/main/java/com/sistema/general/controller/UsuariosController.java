package com.sistema.general.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sistema.general.entity.Response;
import com.sistema.general.table.Usuarios;

import com.sistema.general.service.UsuariosService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.*;


@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuariosController {
	
	Logger logger = LoggerFactory.getLogger(UsuariosController.class);

	private final UsuariosService sistemaService;

	@Autowired
	public UsuariosController(UsuariosService sistemaService) {
		this.sistemaService = sistemaService;
	}

	@GetMapping("/obtener")
	public Response getUsuarios() throws Exception{
		logger.info("Iniciando Servicio: getUsuarios");
		try {
			return sistemaService.getUsuarios();
		} catch(Exception e) {
			logger.error(e.toString());		
		}
		return new Response(-1, "Ocurrio un error al obtener usuarios.");
	}
	
	@GetMapping("/obtener/{usuarioId}")
	public Response getUsuarioById(@PathVariable Long usuarioId) throws Exception {
		logger.info("Iniciando Servicio: getUsuarioById");
		try {
			return sistemaService.getUsuarioById(usuarioId);
		} catch(Exception e) {
			logger.error(e.toString());		
		}
		return new Response(-1, "Ocurrio un error al obtener usuario.");
	}
	
	@PostMapping("/registrar")
	public Response postUsuario(@RequestBody Usuarios userData) throws Exception{
		logger.info("Iniciando Servicio: postUsuario");
		try {
			return sistemaService.postUsuario(userData);
		} catch(Exception e) {
			logger.error(e.toString());
		}
		return new Response(-1, "Ocurrio un error al registrar usuario.");
	}
	
	@PutMapping("/actualizar/{usuarioId}")
	public Response putUsuario(@PathVariable Long usuarioId, @RequestBody Usuarios userData) throws Exception{
		try {
			return sistemaService.putUsuario(usuarioId, userData);
		} catch(Exception e) {
			logger.error(e.toString());
		}
		return new Response(-1, "Ocurrio un error al actualizar usuario.");
	}
	
	@DeleteMapping("/eliminar/{usuarioId}")
	public Response deleteUsuario(@PathVariable Long usuarioId) {
		try {
			return sistemaService.deleteUsuario(usuarioId);
		} catch(Exception e) {
			logger.error(e.toString());
		}
		return new Response(-1, "Ocurrio un error al actualizar usuario.");
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
		}
		return new Response(-1, "Ocurrio un error al hacer login.");
	}
	
	
	@GetMapping("/renovar")
	public Response actualizarToken(HttpServletRequest request) {
		logger.info("Iniciando Servicio: actualizarToken");
	
		try {
			if (request != null) {
				return sistemaService.renovarToken(request);
			} else {
				throw new Exception("Falta el token.");
			}
		} catch(Exception e) {
			logger.error(e.toString());
		}
		return new Response(-1, "Ocurrio un error al obtener token.");
	}
	
	@PostMapping("/subir/imagen/{usuarioId}")
	public Response putImage(@PathVariable Long usuarioId, @RequestParam("imagen") MultipartFile imagen) throws Exception{
		logger.info("Iniciando Servicio: putImage");
		try {
			if (imagen != null) {
				return sistemaService.putImage(usuarioId, imagen);
			}
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return new Response(-1, "Ocurrio un error al subir imagen");
	}
	
	@RequestMapping(value="/obtener/imagen/{nombreImagen}", method = RequestMethod.GET, produces=MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<Resource>  getImage(@PathVariable String nombreImagen) throws IOException {
		logger.info("Iniciando Servicio: getImage");
		try {
			if (nombreImagen != null) {
				
					final ByteArrayResource inputStream = new ByteArrayResource(Files.readAllBytes(Paths.get(
			                "./fotos/"+nombreImagen
			        )));
					return ResponseEntity
				                .status(HttpStatus.OK)
				                .contentLength(inputStream.contentLength())
				                .body(inputStream);
				
			} else {
				return ResponseEntity.badRequest().body(null);
			}
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return ResponseEntity.badRequest().body(null);
	}
	
}
