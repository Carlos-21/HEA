package com.udemy.backendninja.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.udemy.backendninja.entity.Clientes;

@Repository("RepositoryClientes")
public interface RepositoryClientes extends JpaRepository<Clientes,Serializable>{
	public Clientes findByCodcliente(String codCliente);
	public void deleteByCodcliente (String codCliente);
}
