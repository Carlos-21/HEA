package com.udemy.backendninja.servicios;

import com.udemy.backendninja.entity.Cabordencompra;

public interface DetOrdenCompraServicio {
	public boolean ingresarDetOrdenCompra(Cabordencompra cabOrdCom, String codMatPrim, String cantidad);
	public boolean existeDetOrdenCompraxCodCabordcompraxcodMP(String codCabOrdCom, String codMatPrim);
	public boolean borrarDetOrdenCompraxCodCabordcompraxcodMP(String codCabOrdCom, String codMatPrim);
}
