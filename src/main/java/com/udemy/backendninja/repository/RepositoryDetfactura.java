package com.udemy.backendninja.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udemy.backendninja.entity.Detfactura;
import com.udemy.backendninja.entity.Detordencompra;
import com.udemy.backendninja.entity.Factura;
import com.udemy.backendninja.entity.Materiaprima;

public interface RepositoryDetfactura extends JpaRepository<Detfactura,Serializable> {
	
	public List<Detfactura> findByFactura(Factura factura);
}
