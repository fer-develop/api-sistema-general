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
public class SubMenusUsuarios {
	
	/*
	 CREATE TABLE submenus_usuarios 
	 (
		submenu_opcion SERIAL PRIMARY KEY NOT NULL,
		submenu_id INTEGER NOT NULL,
		usuario_id INTEGER NOT NULL,
		visible BOOLEAN DEFAULT false,
		CONSTRAINT fk_usuario
			FOREIGN KEY(usuario_id)
			REFERENCES usuarios(usuario_id),
		CONSTRAINT fk_submenu
	      FOREIGN KEY(submenu_id) 
		  REFERENCES submenus(submenu_id)
	)
	*/
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long submenuOpcion;
	@ManyToOne
    @JoinColumn(name="submenu_id")
    private SubMenus submenuUsuario;
	@ManyToOne
    @JoinColumn(name="usuario_id")
    private Usuarios usuariosub;
	private Boolean visible;
	
	
	public SubMenusUsuarios(Long submenuOpcion, SubMenus submenuUsuario, Usuarios usuariosub, Boolean visible) {
		super();
		this.submenuOpcion = submenuOpcion;
		this.submenuUsuario = submenuUsuario;
		this.usuariosub = usuariosub;
		this.visible = visible;
	}
	
	public SubMenusUsuarios(SubMenus submenuUsuario, Usuarios usuariosub, Boolean visible) {
		super();
		this.submenuUsuario = submenuUsuario;
		this.usuariosub = usuariosub;
		this.visible = visible;
	}


	public Long getSubmenuOpcion() {
		return submenuOpcion;
	}


	public void setSubmenuOpcion(Long submenuOpcion) {
		this.submenuOpcion = submenuOpcion;
	}


	public SubMenus getSubmenuUsuario() {
		return submenuUsuario;
	}


	public void setSubmenuUsuario(SubMenus submenuUsuario) {
		this.submenuUsuario = submenuUsuario;
	}


	public Usuarios getUsuariosub() {
		return usuariosub;
	}


	public void setUsuariosub(Usuarios usuariosub) {
		this.usuariosub = usuariosub;
	}


	public Boolean getVisible() {
		return visible;
	}


	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	
}
