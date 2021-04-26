package com.sistema.general.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistema.general.entity.MenuReponse;
import com.sistema.general.entity.Response;
import com.sistema.general.entity.SubMenuOpts;
import com.sistema.general.repository.MenusUsuariosRepository;
import com.sistema.general.repository.SubMenusUsuariosRepository;
import com.sistema.general.table.MenusUsuarios;
import com.sistema.general.table.Usuarios;

@Service
@Transactional
public class MenuService {
	
	Logger logger = LoggerFactory.getLogger(MenuService.class);
	final MenusUsuariosRepository menusUsuariosRepository;
	final SubMenusUsuariosRepository subMenusUsuarioRepository;
	
	@Autowired
	public MenuService(MenusUsuariosRepository menusUsuariosRepository, SubMenusUsuariosRepository subMenusUsuarioRepository) {
		this.menusUsuariosRepository = menusUsuariosRepository;
		this.subMenusUsuarioRepository = subMenusUsuarioRepository;
	}
	
	
	public Response getMenu(Long usuarioId) {
		ArrayList<MenuReponse> menuReponseList = new ArrayList<>();
		Response response = null;
		try {
			Usuarios usuario = new Usuarios();
			usuario.setUsuarioId(Long.parseLong("1"));
			
			ArrayList<SubMenuOpts> submenuOpt = new ArrayList<>();
			
			MenuReponse menuResponse;
			
			SubMenuOpts dashboard = new SubMenuOpts("Dashboard", "/");
			submenuOpt.add(dashboard);
			
			SubMenuOpts progressbar = new SubMenuOpts("ProgressBar", "progress");
			submenuOpt.add(progressbar);
			
			SubMenuOpts graficas = new SubMenuOpts("Gráficas", "grafica1");
			submenuOpt.add(graficas);
			
			SubMenuOpts promesas = new SubMenuOpts("Promesas", "promesas");
			submenuOpt.add(promesas);

			menuResponse = new MenuReponse("Principal", "mdi mdi-gauge", submenuOpt); 			
			menuReponseList.add(menuResponse);
			
			
			
			
			return new Response(1, "Se obtuvo menu.", menuReponseList);
			//List<MenusUsuarios> menusUsuRes = menusUsuariosRepository.findAllByUsuario(usuario);
			//(List<SubMenusUsuarios> submenusUsuRes = subMenusUsuarioRepository.findAllByUsuarioId(usuarioId);
			
			//logger.info(menusUsuRes.toString());
			//logger.info(submenusUsuRes.toString());
			
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return new Response(-1, "Ocurrio un error al obtener menu.");
	}
}
