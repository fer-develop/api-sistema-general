package com.sistema.general.table;

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
public class Menus {
	
	/* 
	CREATE TABLE menus 
	(
		menu_id SERIAL PRIMARY KEY NOT NULL,
		titulo VARCHAR(20) NOT NULL,
		icono VARCHAR(20)
	)
	*/
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long menuId;
	@Column(nullable = false, columnDefinition = "VARCHAR(20)")
	private String titulo;
	@Column(nullable = false, columnDefinition = "VARCHAR(20)")
	private String icono;
	@OneToMany(mappedBy = "menu")
    private List<SubMenus> submenu;
	@OneToMany(mappedBy = "menuUsuario")
    private List<MenusUsuarios> menuUsuario;
	

	public Menus(Long menuId, String titulo, String icono) {
		super();
		this.menuId = menuId;
		this.titulo = titulo;
		this.icono = icono;
	}
	
	public Menus(Long menuId, String titulo, String icono, List<SubMenus> submenu) {
		super();
		this.menuId = menuId;
		this.titulo = titulo;
		this.icono = icono;
		this.submenu = submenu;
	}
	
	public Menus(String titulo, String icono, List<SubMenus> submenu) {
		super();
		this.titulo = titulo;
		this.icono = icono;
		this.submenu = submenu;
	}
	
	public Menus(String titulo, String icono) {
		super();
		this.titulo = titulo;
		this.icono = icono;
	}

	public Long getMenuId() {
		return menuId;
	}
	
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getIcono() {
		return icono;
	}
	
	public void setIcono(String icono) {
		this.icono = icono;
	}
	
	public List<SubMenus> getSubmenu() {
		return submenu;
	}

	public void setSubmenu(List<SubMenus> submenu) {
		this.submenu = submenu;
	}

	public List<MenusUsuarios> getMenuUsuario() {
		return menuUsuario;
	}

	public void setMenuUsuario(List<MenusUsuarios> menuUsuario) {
		this.menuUsuario = menuUsuario;
	}
	
}
