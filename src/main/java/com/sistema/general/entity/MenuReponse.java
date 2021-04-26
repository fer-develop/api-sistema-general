package com.sistema.general.entity;

import java.util.List;

public class MenuReponse {
	
	private String titulo;
	private String icono;
	private List<SubMenuOpts> submenu;
	
	public MenuReponse(String titulo, String icono) {
		super();
		this.titulo = titulo;
		this.icono = icono;
	}

	public MenuReponse(String titulo, String icono, List<SubMenuOpts> submenu) {
		super();
		this.titulo = titulo;
		this.icono = icono;
		this.submenu = submenu;
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

	public List<SubMenuOpts> getSubmenu() {
		return submenu;
	}

	public void setSubmenu(List<SubMenuOpts> submenu) {
		this.submenu = submenu;
	}

	
}
