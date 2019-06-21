package com.udemy.backendninja.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udemy.backendninja.entity.Proveedores;

@Repository
public interface RepositoryProveedor extends JpaRepository<Proveedores,Serializable>{
	public Proveedores findByCodprov(String codProv);
}
