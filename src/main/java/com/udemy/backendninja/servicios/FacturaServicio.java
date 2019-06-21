package com.udemy.backendninja.servicios;

import java.math.BigDecimal;

import com.udemy.backendninja.entity.Cabordencompra;
import com.udemy.backendninja.entity.Clientes;
import com.udemy.backendninja.entity.Factura;
import com.udemy.backendninja.model.MateriaPrimaModel;

public interface FacturaServicio {
	
	public Factura ingresarFactura(String codigoFactura, Clientes cliente,String direcDest, String direcOrigen, double costoTotal);
	public abstract Factura findByCodfactura(String codigo);
	
}
