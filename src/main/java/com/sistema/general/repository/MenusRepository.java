package com.sistema.general.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.general.table.Menus;

@Repository
public interface MenusRepository extends JpaRepository<Menus, Long> {

}
