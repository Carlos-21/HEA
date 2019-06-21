package com.udemy.backendninja.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.udemy.backendninja.entity.Productos;

@Repository("RepositoryProductos")
public interface RepositoryProductos extends JpaRepository<Productos, Serializable> {
	public Productos findByCodprod(String codigoProducto);
}
