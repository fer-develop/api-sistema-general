package com.sistema.general.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.general.entity.Login;
import com.sistema.general.entity.Usuarios;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Long>{
	
	/*public Integer create(String nombre, 
						  String apellidoPaterno,
						  String apellidoMaterno, 
						  String email, 
						  String password,
						  LocalDate fechaRegistro);*/
	
	public Login findByEmailAndPassword(String email, String password) throws Exception;
	
	public long countByEmail(String email) throws Exception;
	/*public Integer getCountByEmail(String email) throws Exception;*/
}
