package com.sistema.general.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/menu")
public class MenuController {
	
	@GetMapping("/obtener")
	public String getMenu(HttpServletRequest request) {
		long usuarioId = (Long) request.getAttribute("usuarioId");
		return "Autenticado! usuarioId: " + usuarioId;
	}
	
}
