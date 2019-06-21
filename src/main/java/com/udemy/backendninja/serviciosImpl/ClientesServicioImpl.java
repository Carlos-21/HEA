package com.udemy.backendninja.serviciosImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.udemy.backendninja.entity.Clientes;
import com.udemy.backendninja.model.ClientesModel;
import com.udemy.backendninja.repository.RepositoryClientes;
import com.udemy.backendninja.servicios.ClientesServicio;

@Service("clientesServicioImpl")
public class ClientesServicioImpl implements ClientesServicio{

	@Autowired
	@Qualifier("RepositoryClientes")
	private RepositoryClientes clientesRepository;
	
	@Override
	public Clientes addClientes(ClientesModel cliente) {
		Clientes clienteAux = clientesRepository.save(convertirModelAEntity(cliente));
		return clienteAux;
	}
	
	@Override
	public ClientesModel findClienteByCod(String codCliente) {
		Clientes clienteAux = clientesRepository.findByCodcliente(codCliente);
		return convertirEntidadAModel(clienteAux);
	}
	
	@Override
	public void removeCliente(String codCliente) {
		clientesRepository.deleteById(codCliente);
	}
	@Override
	public List<ClientesModel> findAllClientes() {
		List<ClientesModel> lcm = new ArrayList<>();
		List<Clientes> lc = clientesRepository.findAll();
		for(Clientes c : lc)
			lcm.add(convertirEntidadAModel(c));
		return lcm;
	}

	public Clientes convertirModelAEntity(ClientesModel cliente) {
		Clientes cli = new Clientes();
		cli.setApmatcliente(cliente.getApmatcliente());
		cli.setAppatcliente(cliente.getAppatcliente());
		cli.setCabordenventas(cliente.getCabordenventas());
		cli.setCodcliente(cliente.getCodcliente());
		cli.setCorreocliente(cliente.getCorreocliente());
		cli.setNombrecliente(cliente.getNombrecliente());
		cli.setRazonsocialcliente(cliente.getRazonsocialcliente());
		cli.setRfccliente(cliente.getRfccliente());
		cli.setTelefonocliente(cliente.getTelefonocliente());
		cli.setUbigeocliente(cliente.getUbigeocliente());
		return cli;
	}
	
	public ClientesModel convertirEntidadAModel(Clientes cliente) {
		ClientesModel cliModel = new ClientesModel();
		cliModel.setApmatcliente(cliente.getApmatcliente());
		cliModel.setAppatcliente(cliente.getAppatcliente());
		cliModel.setCabordenventas(cliente.getCabordenventas());
		cliModel.setCodcliente(cliente.getCodcliente());
		//cliModel.setIdCliente(Integer.parseInt(cliente.getIdcliente()));
		cliModel.setCorreocliente(cliente.getCorreocliente());
		cliModel.setNombrecliente(cliente.getNombrecliente());
		cliModel.setRazonsocialcliente(cliente.getRazonsocialcliente());
		cliModel.setRfccliente(cliente.getRfccliente());
		cliModel.setTelefonocliente(cliente.getTelefonocliente());
		cliModel.setUbigeocliente(cliente.getUbigeocliente());
		return cliModel;
	}

	

	
}
