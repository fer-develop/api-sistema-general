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
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.hibernate.exception.ConstraintViolationException;
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
				response = new Response(1, "Se encontraron los usuarios.", usuarios);
			} else {
				response = new Response(0, "No hay usuarios registrados.");
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
			
			// Limpiar espacios en blanco al prin - final
			usuarioData.setNombre(usuarioData.getNombre().toLowerCase().trim());
			usuarioData.setApellidoPaterno(usuarioData.getApellidoPaterno().toLowerCase().trim());
			usuarioData.setApellidoMaterno(usuarioData.getApellidoMaterno().toLowerCase().trim());
			usuarioData.setEmail(usuarioData.getEmail().toLowerCase().trim());
			
			String emailLower = usuarioData.getEmail();
			
			usuarioData.setEmail(emailLower);
			if (usuariosRepository.countByEmail(usuarioData.getEmail()) > 0) {
				response = new Response(0, "Ya existe un usuario registrado con este correo.");
			} else {
				int strength = 5;
				BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());
				String encodedPassword = bCryptPasswordEncoder.encode(usuarioData.getPassword());
				usuarioData.setPassword(encodedPassword);
				Usuarios user = usuariosRepository.save(usuarioData);
				if (user != null) {
					logger.info("El usuario ha sido guardado correctamente.");
					response = new Response(1, "Usuario registrado correctamente.", user);
				} else {
					throw new Exception("Ocurrio un error al registrar usuario.");
				}
			}
		} catch (Exception e) {
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
			if (findByEmail != null) {
				BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
				CharSequence sc = password;
				if (bCryptPasswordEncoder.matches(sc, findByEmail.getPassword())) {
					logger.info("El usuario " + email + "esta intentando iniciar sesion.");
					response = new Response(1, "Bienvenido(a) " + findByEmail.getNombre(), findByEmail,
							tokenClass.generarJWToken(findByEmail));
					logger.info(response.toString());
				} else {
					response = new Response(0, "Contraseña invalida.");
				}
			} else {
				response = new Response(0, "Usuario no registrado.");
			}
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return response;
	}

	public Response renovarToken(HttpServletRequest token) {
		Response response = null;
		logger.info(token.toString());
		try {
			Long usuarioId = (Long) token.getAttribute("usuarioId");
			JWToken tokenClass = new JWToken();
			if (usuarioId > 0) {
				Usuarios findByIdUser = usuariosRepository.findOneByUsuarioId(usuarioId);
				if (findByIdUser != null) {
					logger.info("El usuario " + findByIdUser.getEmail() + "esta intentando iniciar sesion.");
					response = new Response(1, "Token renovado.", findByIdUser, tokenClass.generarJWToken(findByIdUser));
				} else {
					response = new Response(0, "Este usuario no se encuentra registrado.");
				}
			} else {
				response = new Response(0, "Token invalido.");
			}
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return response;
	}

	public Response putUsuario(Long usuarioId, Usuarios usuarioData) throws Exception {
		logger.info("Iniciando Metodo: putUsuario");
		Response response = null;
		try {
			
			// Limpiar espacios en blanco al prin - final
			
			Usuarios usuario = usuariosRepository.getOne(usuarioId);
			if (usuario != null) {
				
				if (!usuarioData.getEmail().equals("")) usuarioData.setEmail(usuarioData.getEmail().trim().toLowerCase());
				
				if (usuarioData.getEmail().equals(usuario.getEmail().toString())) {
					usuario.setNombre(getValor(usuarioData.getNombre(), usuario.getNombre()));
					usuario.setApellidoMaterno(
							getValor(usuarioData.getApellidoMaterno(), usuario.getApellidoMaterno()));
					usuario.setApellidoPaterno(
							getValor(usuarioData.getApellidoPaterno(), usuario.getApellidoPaterno()));
					usuario.setEmail(getValor(usuarioData.getEmail(), usuario.getEmail()));
					usuario.setPassword(getValor(usuarioData.getPassword(), usuario.getPassword()));
					response = new Response(1, "Se actualizo el usuario.", usuariosRepository.save(usuario));
				} else {
					Long emailExiste = usuariosRepository.countByEmailNot(usuarioData.getEmail());
					logger.info(emailExiste.toString());
					if (emailExiste == 1) {
						response = new Response(0, "Este email está registrado.");
					} else {
						usuario.setNombre(getValor(usuarioData.getNombre(), usuario.getNombre()));
						usuario.setApellidoMaterno(
								getValor(usuarioData.getApellidoMaterno(), usuario.getApellidoMaterno()));
						usuario.setApellidoPaterno(
								getValor(usuarioData.getApellidoPaterno(), usuario.getApellidoPaterno()));
						usuario.setEmail(getValor(usuarioData.getEmail(), usuario.getEmail()));
						usuario.setPassword(getValor(usuarioData.getPassword(), usuario.getPassword()));
						response = new Response(1, "Se actualizo el usuario.", usuariosRepository.save(usuario));
					}
				}
			} else {
				response = new Response(0, "El usuario no pudo ser encontrado.");
			}
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return response;
	}

	public String getValor(String cadena1, String cadena2) {
		if (cadena1 != null && cadena1 != "") {
			return cadena1.toLowerCase().trim();
		} else {
			return cadena2.toLowerCase().trim();
		}
	}

	public Response putImage(Long usuarioId, MultipartFile imagen) throws Exception {
		logger.info("Iniciando Metodo: putImage");
		Response response = null;
		try {
			String extension = FilenameUtils.getExtension(imagen.getOriginalFilename());
			String nameImage = UUID.randomUUID().toString() + "." + extension;
			if (extension.contains("jpg") || extension.contains("jpeg") || extension.contains("png")) {
				File theDir = new File("./fotos/");
				if (!theDir.exists()) {
					theDir.mkdirs();
				}
				String pathFotos = "./fotos/";
				byte[] bytesImg = imagen.getBytes();

				InputStream is = new ByteArrayInputStream(bytesImg);
				BufferedImage bi = ImageIO.read(is);
				BufferedImage imagenResize = this.resizeImage(bi, 300, 300);

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(imagenResize, extension, baos);
				byte[] bytes = baos.toByteArray();

				Path path = Paths.get(pathFotos + nameImage);

				Files.write(path, bytes);

				Usuarios usuario = usuariosRepository.findOneByUsuarioId(usuarioId);
				if (usuario != null) {
					usuario.setNomImage(nameImage);
					response = new Response(1, "Se actualizo la imagen del usuario.", usuariosRepository.save(usuario));
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
		return Scalr.resize(originalImage, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, targetWidth, targetHeight,
				Scalr.OP_ANTIALIAS);
	}

}
