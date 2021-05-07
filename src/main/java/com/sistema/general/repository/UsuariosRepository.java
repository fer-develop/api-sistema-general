package com.sistema.general.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.general.table.Usuarios;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Long>{

	Optional<Usuarios>  findOneByUsuarioId(Long usuarioId) throws Exception;

	Optional<Usuarios> findOneByEmail(String email) throws Exception;

	Optional<Usuarios>  findOneByEmailAndPassword(String email, String password) throws Exception;
	
	long countByEmail(String email) throws Exception;
	
	long countByEmailNot(String email) throws Exception;

}
