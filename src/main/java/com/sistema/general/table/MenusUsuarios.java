package com.sistema.general.table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class MenusUsuarios {

	/*
	 CREATE TABLE menus_usuarios (
		menu_opcion_id SERIAL PRIMARY KEY NOT NULL,
		menu_id INTEGER NOT NULL,
		usuario_id INTEGER NOT NULL,
		visible BOOLEAN DEFAULT false,
		CONSTRAINT fk_menu
	      FOREIGN KEY(menu_id) 
		  REFERENCES menus(menu_id),
		CONSTRAINT fk_usuario
		 FOREIGN KEY(usuario_id)
		 REFERENCES usuarios(usuario_id)
	)
	*/
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long menuOpcionId;
	@ManyToOne
    @JoinColumn(name="menu_id")
    private Menus menuUsuario;
	@ManyToOne
    @JoinColumn(name="usuario_id")
    private Usuarios usuario;
	private Boolean visible;
	
	
	public MenusUsuarios(Long menuOpcionId, Menus menuUsuario, Usuarios usuario, Boolean visible) {
		super();
		this.menuOpcionId = menuOpcionId;
		this.menuUsuario = menuUsuario;
		this.usuario = usuario;
		this.visible = visible;
	}
	
	public MenusUsuarios(Menus menuUsuario, Usuarios usuario, Boolean visible) {
		super();
		this.menuUsuario = menuUsuario;
		this.usuario = usuario;
		this.visible = visible;
	}


	public Long getMenuOpcionId() {
		return menuOpcionId;
	}


	public void setMenuOpcionId(Long menuOpcionId) {
		this.menuOpcionId = menuOpcionId;
	}


	public Menus getMenuUsuario() {
		return menuUsuario;
	}


	public void setMenuUsuario(Menus menuUsuario) {
		this.menuUsuario = menuUsuario;
	}


	public Usuarios getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}


	public Boolean getVisible() {
		return visible;
	}


	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	
}
