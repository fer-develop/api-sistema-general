package com.sistema.general.entity;

public class SubMenuOpts {
	
	private String titulo;
	private String url;
	
	public SubMenuOpts(String titulo, String url) {
		super();
		this.titulo = titulo;
		this.url = url;
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
	
}
