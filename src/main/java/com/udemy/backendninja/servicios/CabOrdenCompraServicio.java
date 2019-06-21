package com.udemy.backendninja.servicios;

import java.math.BigDecimal;

import com.udemy.backendninja.entity.Cabordencompra;

public interface CabOrdenCompraServicio {
	
	public Cabordencompra ingresarCabOrdenCompra(String codProv, String contactoProveedor,String fechaOrden,	String tipoPago, BigDecimal costo, String nombreUsuario);

	public Cabordencompra encontrarCabOrdenCompra(String codCabOrdenCompra);
	
	public boolean actualizarDetOrdenComprax(Cabordencompra coc);
	
	public boolean borrarCabOrdenCompra(Cabordencompra coc);
	
}
