package com.sistema.general.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.general.table.MenusUsuarios;
import com.sistema.general.table.Usuarios;

@Repository
public interface MenusUsuariosRepository extends JpaRepository<MenusUsuarios, Long>{
	
	List<MenusUsuarios> findAllByUsuario(Usuarios usuario);
}
