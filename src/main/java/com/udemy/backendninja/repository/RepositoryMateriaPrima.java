package com.udemy.backendninja.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udemy.backendninja.entity.Materiaprima;

@Repository("RepositoryMateriaPrima")
public interface RepositoryMateriaPrima extends JpaRepository<Materiaprima, Serializable> {

	public Materiaprima findByCodmatprima(String codigo);
	
}
