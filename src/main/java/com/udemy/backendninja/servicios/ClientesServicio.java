package com.udemy.backendninja.servicios;

import java.util.List;

import com.udemy.backendninja.entity.Clientes;
import com.udemy.backendninja.model.ClientesModel;

public interface ClientesServicio {
	public abstract Clientes addClientes(ClientesModel cliente);
	public abstract ClientesModel findClienteByCod(String codCliente);
	public abstract void removeCliente(String cliente);
	public abstract List<ClientesModel> findAllClientes();
}
