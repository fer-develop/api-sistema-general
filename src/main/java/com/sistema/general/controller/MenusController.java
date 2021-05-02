package com.sistema.general.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.general.entity.Response;
import com.sistema.general.service.MenuService;

@RestController
@RequestMapping("/api/v1/menu")
public class MenusController {
	
	Logger logger = LoggerFactory.getLogger(MenusController.class);
	
	private final MenuService menuService;
	
	@Autowired
	public MenusController(MenuService menuService) {
		this.menuService = menuService;
	}
	
	@GetMapping("/obtener")
	public Response getMenu(HttpServletRequest request) {
		logger.info("Iniciando Servicio: getMenu");
		Long usuarioId = (Long) request.getAttribute("usuarioId");
		
		try {
			return menuService.getMenu(usuarioId);
		} catch(Exception e) {
			logger.error(e.toString());
		}
		return new Response(-1, "Ocurrio un error al retornar menu");
	}
	
	/*@PostMapping("/generar/{usuarioId}")
	public Response postMenu(@PathVariable("usuarioId") String usuarioId) {
		
		
		
		
		
		return new Response(-1, "Ocurrio un error al generar menu");
	}*/
	
}
