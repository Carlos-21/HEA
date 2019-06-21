package com.udemy.backendninja.servicios;

import java.util.List;

import com.udemy.backendninja.entity.Cabordencompra;
import com.udemy.backendninja.entity.Detfactura;
import com.udemy.backendninja.entity.Factura;

public interface DetFacturaServicio {
	public boolean insertarFactura(Factura factura, String codProducto, double cantidad, double precio);
	public List<Detfactura> findByFactura(Factura factura);
}
