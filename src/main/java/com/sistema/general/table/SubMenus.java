package com.sistema.general.table;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class SubMenus {
	
	/*
	 CREATE TABLE submenus 
	 (
		submenu_id SERIAL PRIMARY KEY NOT NULL,
		menu_id INTEGER NOT NULL,
		titulo VARCHAR(20) NOT NULL,
		url VARCHAR(20) NOT NULL,
	  		CONSTRAINT fk_menu
      		FOREIGN KEY(menu_id) 
	  		REFERENCES menus(menu_id)
	)
	*/
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long submenuId;
	@Column(nullable = false, columnDefinition = "VARCHAR(20)")
	private String titulo;
	@Column(nullable = false, columnDefinition = "VARCHAR(20)")
	private String url;
	@ManyToOne
    @JoinColumn(name="menu_id")
    private Menus menu;
	@OneToMany(mappedBy = "usuariosub")
    private List<SubMenusUsuarios> subMenuUsuarios;

	
	public SubMenus(Long submenuId, String titulo, String url, Menus menu, List<SubMenusUsuarios> subMenuUsuarios) {
		super();
		this.submenuId = submenuId;
		this.titulo = titulo;
		this.url = url;
		this.menu = menu;
		this.subMenuUsuarios = subMenuUsuarios;
	}
	
	public SubMenus(String titulo, String url) {
		super();
		this.titulo = titulo;
		this.url = url;
	}
	
	public SubMenus(String titulo, String url, Menus menu) {
		super();
		this.titulo = titulo;
		this.url = url;
		this.menu = menu;
	}

	public Long getSubmenuId() {
		return submenuId;
	}

	public void setSubmenuId(Long submenuId) {
		this.submenuId = submenuId;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Menus getMenu() {
		return menu;
	}

	public void setMenu(Menus menu) {
		this.menu = menu;
	}

	public List<SubMenusUsuarios> getSubMenuUsuarios() {
		return subMenuUsuarios;
	}

	public void setSubMenuUsuarios(List<SubMenusUsuarios> subMenuUsuarios) {
		this.subMenuUsuarios = subMenuUsuarios;
	}
	
}
