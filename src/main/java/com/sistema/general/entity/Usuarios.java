package com.sistema.general.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table
public class Usuarios {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long usuarioId;
	@Column(nullable=false)
	private String nombre;
	@Column(nullable=false)
	private String apellidoPaterno;
	@Column(nullable=false)
	private String apellidoMaterno;
	@Column(nullable=false, columnDefinition="TEXT UNIQUE")
	private String email;
	@Column(nullable=false)
	private String password;
	private String nomImage;
	@Column(name="fecha_registro", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable= false, insertable = false)
	private LocalDate fechaRegistro;
	

	public Usuarios() {
		
	}
	
	public Usuarios(String nombre, String apellidoPaterno, String apellidoMaterno, String email,
			String password, String nomImage, LocalDate fechaRegistro) {
		this.nombre = nombre;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.email = email;
		this.password = password;
		this.nomImage = nomImage;
		this.fechaRegistro = fechaRegistro;
	}

	public Usuarios(String nombre, 
				   String apellidoPaterno,
				   String apellidoMaterno, 
				   String email, 
				   String password,
			LocalDate fechaRegistro) {
		
		this.nombre = nombre;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.email = email;
		this.password = password;
		this.fechaRegistro = fechaRegistro;
	}

	public Usuarios(String nombre, 
				   String apellidoPaterno,
				   String apellidoMaterno, 
				   String email,
				   String password, 
				   String nomImage) {
		
		this.nombre = nombre;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.email = email;
		this.password = password;
		this.nomImage = nomImage;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNomImage() {
		return nomImage;
	}

	public void setNomImage(String nomImage) {
		this.nomImage = nomImage;
	}
	
	public LocalDate getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDate fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	
	@Override
	public String toString() {
		return "Usuario [usuarioId=" + usuarioId + ", nombre=" + nombre + ", apellidoPaterno=" + apellidoPaterno
				+ ", apellidoMaterno=" + apellidoMaterno + ", email=" + email + ", password=" + password + ", nomImage="
				+ nomImage + ", fechaRegistro=" + fechaRegistro + "]";
	}
}
