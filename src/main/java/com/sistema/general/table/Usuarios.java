package com.sistema.general.table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class Usuarios {
	
	/*
	 CREATE TABLE usuarios (
		usuario_id SERIAL NOT NULL,
		nombre VARCHAR(30) NOT NULL,
		apellido_paterno VARCHAR(20) NOT NULL,
		apellido_materno VARCHAR(20) NOT NULL,
		email VARCHAR(30) NOT NULL,
		password TEXT NOT NULL,
		nom_imagen VARCHAR(30),
		PRIMARY KEY(usuario_id)
	) 
	*/
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
	private String email = "";
	@Column(name="password", nullable=false)
	private String password;
	private String nomImage;
	@Column(name="fecha_registro", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable=false, insertable=false, nullable = false)
	private LocalDate fechaRegistro;
	@OneToMany(mappedBy = "usuario")
    private List<MenusUsuarios> menuUsuarios;
	@OneToMany(mappedBy = "usuariosub")
    private List<SubMenusUsuarios> subMenuUsuarios;
	
	
	public Usuarios() {
		
	}
	
	public Usuarios(String nombre, String apellidoPaterno, String apellidoMaterno, String email,
			String password, String nomImage) {
		this.nombre = nombre;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.email = email;
		this.password = password;
		this.nomImage = nomImage;
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

	public Usuarios(Long usuarioId, String nombre, String apellidoPaterno, String apellidoMaterno, String email,
					String password, String nomImage, LocalDate fechaRegistro) {
		this.usuarioId = usuarioId;
		this.nombre = nombre;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.email = email;
		this.password = password;
		this.nomImage = nomImage;
		this.fechaRegistro = fechaRegistro;
	}

	/*@JsonIgnore
	@JsonProperty(value = "usuarioId")*/
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

	@JsonIgnore
	@JsonProperty(value = "password")
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

	@JsonIgnore
	@JsonProperty(value = "fechaRegistro")
	public LocalDate getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDate fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	
	@JsonIgnore
	@JsonProperty(value = "menuUsuarios")
	public List<MenusUsuarios> getMenuUsuarios() {
		return menuUsuarios;
	}

	public void setMenuUsuarios(List<MenusUsuarios> menuUsuarios) {
		this.menuUsuarios = menuUsuarios;
	}

	@JsonIgnore
	@JsonProperty(value = "subMenuUsuarios")
	public List<SubMenusUsuarios> getSubMenuUsuarios() {
		return subMenuUsuarios;
	}

	public void setSubMenuUsuarios(List<SubMenusUsuarios> subMenuUsuarios) {
		this.subMenuUsuarios = subMenuUsuarios;
	}

	@Override
	public String toString() {
		return "Usuario [usuarioId=" + usuarioId + ", nombre=" + nombre + ", apellidoPaterno=" + apellidoPaterno
				+ ", apellidoMaterno=" + apellidoMaterno + ", email=" + email + ", password=" + password + ", nomImage="
				+ nomImage + ", fechaRegistro=" + fechaRegistro + "]";
	}
}
