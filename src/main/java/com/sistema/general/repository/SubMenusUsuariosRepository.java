package com.sistema.general.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.general.table.SubMenusUsuarios;

public interface SubMenusUsuariosRepository extends JpaRepository<SubMenusUsuarios, Long>{
	
	//List<SubMenusUsuarios> findAllByUsuarioId(Long usuarioId);

}
