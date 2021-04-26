package com.sistema.general.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.general.table.SubMenus;

@Repository
public interface SubMenusRepository extends JpaRepository<SubMenus, Long>{
	
}
