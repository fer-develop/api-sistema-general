package com.sistema.general.service;

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
import java.util.UUID;

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
import com.sistema.general.repository.UsuariosRepository;

@Service
@Transactional
public class UsuariosService {
	
	final UsuariosRepository usuariosRepository;
	Logger logger = LoggerFactory.getLogger(UsuariosService.class);
	@Autowired
	public UsuariosService(UsuariosRepository usuariosRepository) {
		this.usuariosRepository = usuariosRepository;
	}

	public Response getUsuarios() throws Exception {
		logger.info("Iniciando Metodo: getUsuarios");
		Response response = null;
		try {
			List<Usuarios> usuarios = usuariosRepository.findAll();
			if (usuarios != null) {
				response = new Response(1,
						"Se encontraron los usuarios.",
						usuarios);
			} else {
				response = new Response(0,
						"No hay usuarios registrados.");
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
			if (usuariosRepository.countByEmail(usuarioData.getEmail()) > 0) {
				response = new Response(0, "Ya existe un usuario registrado con este correo.");
			} else {
				int strength = 5;
				BCryptPasswordEncoder bCryptPasswordEncoder =
						new BCryptPasswordEncoder(strength, new SecureRandom());
				String encodedPassword = bCryptPasswordEncoder.encode(usuarioData.getPassword());
				usuarioData.setPassword(encodedPassword);
				Usuarios user = usuariosRepository.save(usuarioData);
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
			Usuarios findByEmail = usuariosRepository.findOneByEmail(email);
			if (findByEmail != null)
			{
				BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
				CharSequence sc = password;
				if(bCryptPasswordEncoder.matches(sc, findByEmail.getPassword())) {
					logger.info("El usuario " + email + "esta intentando iniciar sesion.");
					response = new Response(1, "Bienvenido(a) " + findByEmail.getNombre(), findByEmail, tokenClass.generarJWToken(findByEmail));
				} else {
					response = new Response(0, "La contraseña no es valida.");
				}
			} else {
				response = new Response(0, "Este email no se encuentra registrado.");
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
			Usuarios usuario = usuariosRepository.getOne(usuarioId);
			if (usuario != null) {
				if (usuarioData.getEmail() != null && usuarioData.getEmail() != "") {
					if (usuariosRepository.countByEmail(usuarioData.getEmail()) > 0) {
						response = new Response(0, "El email proporcionado ya esta registrado.");	
					} else {
						usuario.setNombre(getValor(usuarioData.getNombre(), usuario.getNombre()));
						usuario.setApellidoMaterno(getValor(usuarioData.getApellidoMaterno(), usuario.getApellidoMaterno()));
						usuario.setApellidoPaterno(getValor(usuarioData.getApellidoPaterno(), usuario.getApellidoPaterno()));
						usuario.setEmail(getValor(usuarioData.getEmail(), usuario.getEmail()));
						usuario.setPassword(getValor(usuarioData.getPassword(), usuario.getPassword()));
						response = new Response(1, "Se actualizo el usuario.", usuariosRepository.save(usuario));
					}
				} else {
					usuario.setNombre(getValor(usuarioData.getNombre(), usuario.getNombre()));
					usuario.setApellidoMaterno(getValor(usuarioData.getApellidoMaterno(), usuario.getApellidoMaterno()));
					usuario.setApellidoPaterno(getValor(usuarioData.getApellidoPaterno(), usuario.getApellidoPaterno()));
					usuario.setPassword(getValor(usuarioData.getPassword(), usuario.getPassword()));
					response = new Response(1, "Se actualizo el usuario.", usuariosRepository.save(usuario));
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
	
	public Response putImage(Long usuarioId, MultipartFile imagen) throws Exception{
		logger.info("Iniciando Metodo: putImage");
		Response response = null;
		try {
			String extension = FilenameUtils.getExtension(imagen.getOriginalFilename());
			String nameImage = UUID.randomUUID().toString() + "." + extension;
			if (extension.contains("jpg") || extension.contains("jpeg") || extension.contains("png")) {
				File theDir = new File("./fotos/");
				if (!theDir.exists()){
				    theDir.mkdirs();
				}
				String pathFotos = "./fotos/";
				byte[] bytesImg = imagen.getBytes();
				
				InputStream is = new ByteArrayInputStream(bytesImg);
				BufferedImage bi = ImageIO.read(is);
				BufferedImage imagenResize = this.resizeImage(bi, 150, 150);
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(imagenResize, extension, baos);
				byte[] bytes = baos.toByteArray();
				
				Path path = Paths.get(pathFotos + nameImage);
				
				Files.write(path, bytes);
				
				Usuarios usuario = usuariosRepository.findOneByUsuarioId(usuarioId);
				if (usuario != null) {
					usuario.setNomImage(nameImage);
					response = new Response(1, "Se actualizo el usuario.", usuariosRepository.save(usuario));
				} else {
					response = new Response(0, "Ocurrio un error al actualizar foto.");
				}
			} else {
				response = new Response(0, "Formato no compatible/Verificar que sea .jpg .jpeg o .png .");
			}
		} catch (Exception e) {
			logger.error(e.toString());
			response = new Response(-1, "Ocurrio un error al subir fotografia.");
		}
		return response;
	}
	
	BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws Exception {
	    return Scalr.resize(originalImage, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, targetWidth, targetHeight, Scalr.OP_ANTIALIAS);
	}
	
}
