package com.udemy.backendninja.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.udemy.backendninja.entity.Cabordencompra;

public interface RepositoryCabOrdenCompra extends JpaRepository<Cabordencompra,Serializable>{
	
	public Cabordencompra findByOpcCodordencompra(String codCabOrdenCompra);
	
	@Query(value="SELECT s_cabOrdenCompra.nextval from dual", nativeQuery=true)
	public Long getNextCabOrdCompra();
}
