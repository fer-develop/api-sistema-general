package com.sistema.general.service;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sistema.general.entity.JWToken;
import com.sistema.general.entity.Response;
import com.sistema.general.table.Usuarios;
import com.sistema.general.repository.SistemaRepository;

@Service
@Transactional
public class SistemaService {
	
	final SistemaRepository sistemaRepository;
	Logger logger = LoggerFactory.getLogger(SistemaService.class);
	@Autowired
	public SistemaService(SistemaRepository sistemaRepository) {
		this.sistemaRepository = sistemaRepository;
	}

	public Response getUsuarios() throws Exception {
		logger.info("Iniciando Metodo: getUsuarios");
		Response response = null;
		try {
			List<Usuarios> usuarios = sistemaRepository.findAll();
			if (usuarios != null) {
				response = new Response(1,
						"Se encontraron los usuarios.",
						usuarios);
			} else {
				response = new Response(0,
						"No hay usuarios registrados.",
						List.of());
			}
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return response;
	}
	
	public Response postUsuario(Usuarios usuarioData) throws Exception {
		logger.info("Iniciando Metodo: postUsuario");
	    Response response = null;
		try {
			String emailLower = usuarioData.getEmail().toLowerCase();
			usuarioData.setEmail(emailLower);
			if (sistemaRepository.countByEmail(usuarioData.getEmail()) > 0) {
				response = new Response(0, "Ya existe un usuario registrado con este correo.", List.of());
			} else {
				int strength = 5;
				BCryptPasswordEncoder bCryptPasswordEncoder =
						new BCryptPasswordEncoder(strength, new SecureRandom());
				String encodedPassword = bCryptPasswordEncoder.encode(usuarioData.getPassword());
				usuarioData.setPassword(encodedPassword);
				Usuarios user = sistemaRepository.save(usuarioData);
				if (user != null) {
					logger.info("El usuario ha sido guardado correctamente.");
					response = new Response(1, "Se inserto el usuario correctamente.", user);
				} else {
					throw new Exception("Ocurrio un error al registrar usuario.");
				}
			}
		} catch(Exception e) {
			logger.error(e.toString());
		}
		return response;
	}

	public Response login(String email, String password) throws Exception {
		logger.info("Iniciando Metodo: login");
		Response response = null;
		JWToken tokenClass = new JWToken();
		try {
			Usuarios findByEmail = sistemaRepository.findOneByEmail(email);
			if (findByEmail != null)
			{
				BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
				CharSequence sc = password;
				if(bCryptPasswordEncoder.matches(sc, findByEmail.getPassword())) {
					logger.info("El usuario " + email + "esta intentando iniciar sesion.");
					response = new Response(1, "Bienvenido(a) " + findByEmail.getNombre(), findByEmail, tokenClass.generarJWToken(findByEmail));
				} else {
					response = new Response(0, "La contraseña no es valida.", List.of());
				}
			} else {
				response = new Response(0, "Este email no se encuentra registrado.", List.of());
			}
		} catch(Exception e) {
			logger.error(e.toString());
		}
		return response;
	}
	
	public Response putUsuario(Long usuarioId, Usuarios usuarioData) throws Exception {
		logger.info("Iniciando Metodo: putUsuario");
		Response response = null;
		try {
			Usuarios usuario = sistemaRepository.getOne(usuarioId);
			if (usuario != null) {
				if (usuarioData.getEmail() != null && usuarioData.getEmail() != "") {
					if (sistemaRepository.countByEmail(usuarioData.getEmail()) > 0) {
						response = new Response(0, "El email proporcionado ya esta registrado.");	
					} else {
						usuario.setNombre(getValor(usuarioData.getNombre(), usuario.getNombre()));
						usuario.setApellidoMaterno(getValor(usuarioData.getApellidoMaterno(), usuario.getApellidoMaterno()));
						usuario.setApellidoPaterno(getValor(usuarioData.getApellidoPaterno(), usuario.getApellidoPaterno()));
						usuario.setEmail(getValor(usuarioData.getEmail(), usuario.getEmail()));
						usuario.setNomImage(getValor(usuarioData.getNomImage(), usuario.getNomImage()));
						usuario.setPassword(getValor(usuarioData.getPassword(), usuario.getPassword()));
						response = new Response(1, "Se actualizo el usuario.", sistemaRepository.save(usuario));
					}
				} else {
					usuario.setNombre(getValor(usuarioData.getNombre(), usuario.getNombre()));
					usuario.setApellidoMaterno(getValor(usuarioData.getApellidoMaterno(), usuario.getApellidoMaterno()));
					usuario.setApellidoPaterno(getValor(usuarioData.getApellidoPaterno(), usuario.getApellidoPaterno()));
					usuario.setNomImage(getValor(usuarioData.getNomImage(), usuario.getNomImage()));
					usuario.setPassword(getValor(usuarioData.getPassword(), usuario.getPassword()));
					response = new Response(1, "Se actualizo el usuario.", sistemaRepository.save(usuario));
				}
			} else {
				response = new Response(0, "El usuario no pudo ser encontrado.");
			}
		} catch(Exception e) {
			logger.error(e.toString());
		}
		return response;
	}
	
	public String getValor(String cadena1, String cadena2) {
		if (cadena1 != null && cadena1 != "") {
			return cadena1;
		} else {
			return cadena2;
		}
	}
	
	public Response putImage(MultipartFile imagen) {
		logger.info("Iniciando Metodo: putImage");
		
		Response response = null;
		try {
			File theDir = new File("./fotos/");
			if (!theDir.exists()){
			    theDir.mkdirs();
			}
			String pathFotos = "./fotos/";
			byte[] bytesImg = imagen.getBytes();
			
			InputStream is = new ByteArrayInputStream(bytesImg);
			BufferedImage bi = ImageIO.read(is);
			BufferedImage imagenResize = this.resizeImage(bi, 150, 150);
			
			String extension = FilenameUtils.getExtension(imagen.getOriginalFilename());
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(imagenResize, extension, baos);
			byte[] bytes = baos.toByteArray();
			
			Path path = Paths.get(pathFotos + imagen.getOriginalFilename());
			
			Files.write(path, bytes);
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return response;
	}
	
	BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws Exception {
	    return Scalr.resize(originalImage, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, targetWidth, targetHeight, Scalr.OP_ANTIALIAS);
	}
	
}
