package com.udemy.backendninja.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udemy.backendninja.entity.Cabordencompra;
import com.udemy.backendninja.entity.Factura;
import com.udemy.backendninja.entity.Materiaprima;

public interface RepositoryFactura extends JpaRepository<Factura, Serializable> {
	public Factura findByCodfactura(String codigo);

}
