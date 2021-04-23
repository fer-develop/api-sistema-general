package com.sistema.general.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistema.general.entity.Login;
import com.sistema.general.entity.Usuarios;
import com.sistema.general.repository.UsuariosRepository;



@Service
@Transactional
public class UsuariosService {
	
	@Autowired
	UsuariosRepository usuariosRepository;
	
	Logger logger = LoggerFactory.getLogger(UsuariosService.class);
	
	@SuppressWarnings("unchecked")
	public List<Usuarios> getUsuarios() throws Exception {
			
		logger.info("Iniciando Metodo: getUsuarios");
		
		@SuppressWarnings("rawtypes")
		List lista = null;
		
		try {
			
			lista = usuariosRepository.findAll();
			
			return lista;
		
		} catch (Exception e) {
			logger.error(e.toString());
		}
		
		return lista;
	
	}
	
	
	public Login login(String email, String password) throws Exception {
		
		logger.info("Iniciando Metodo: login");
		
		Login usuario = null;
		
		try {
			
			usuario = usuariosRepository.findByEmailAndPassword(email, password); 
			
			if (!usuario.getEmail().isEmpty()) {
				
				logger.info("El usuario " + email + "esta intentando iniciar sesion.");
				return usuario;
			
			} else {
				
				logger.info("El usuario " + email + " no existe.");
			
			}
			
		} catch(Exception e) {
			logger.error(e.toString());
		}
		
		return usuario;
	
	}
	
	public Usuarios postUsuario(Usuarios usuarioData) throws Exception {
		logger.info("Iniciando Metodo: postUsuario");
		
		Usuarios usuario = null;
	    
		try {
			
			usuario = usuariosRepository.save(usuarioData); 
				
			logger.info("El usuario ha sido guardado correctamente.");
			return usuario;
		
		} catch(Exception e) {
			logger.error(e.toString());
		}
		
		return usuario;
		
	}
	
}
