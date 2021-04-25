package com.sistema.general.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.general.table.Usuarios;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Long>{

	Usuarios findOneByUsuarioId(Long usuarioId) throws Exception;

	Usuarios findOneByEmail(String email) throws Exception;

	Usuarios findOneByEmailAndPassword(String email, String password) throws Exception;
	
	long countByEmail(String email) throws Exception;

}
