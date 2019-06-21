package com.udemy.backendninja.serviciosImpl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udemy.backendninja.entity.Cabordencompra;
import com.udemy.backendninja.entity.Clientes;
import com.udemy.backendninja.entity.Factura;
import com.udemy.backendninja.entity.Proveedores;
import com.udemy.backendninja.repository.RepositoryCabOrdenCompra;
import com.udemy.backendninja.repository.RepositoryClientes;
import com.udemy.backendninja.repository.RepositoryFactura;
import com.udemy.backendninja.repository.RepositoryProveedor;
import com.udemy.backendninja.servicios.CabOrdenCompraServicio;
import com.udemy.backendninja.servicios.FacturaServicio;

@Service("FacturaServicioImpl")
public class FacturaServicioImpl implements FacturaServicio {

	@Autowired RepositoryClientes repoCliente;
	@Autowired RepositoryFactura repoFactura;
	@Override
	public Factura ingresarFactura(String codigoFactura, Clientes cliente,String direcDest, String direcOrigen, double costoTotal) {
		Factura factura= new Factura();
		int id =(int) Math.random()*10000;
	
		factura.setClientes(cliente);
		factura.setCodfactura(codigoFactura);
		factura.setCosto(new BigDecimal(costoTotal));
		factura.setDirecciondestino(direcDest);
		factura.setDireccionemision(direcOrigen);
		return repoFactura.save(factura);
	}
	@Override
	public Factura findByCodfactura(String codigo) {
		Factura factura = repoFactura.findByCodfactura(codigo);
		return factura;
	}


}
