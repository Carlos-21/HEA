package com.udemy.backendninja.model;

public class OrdenCompra {
	String inputCodigoBusqueda;
	String codOrdenCompra;
	String codProv;
	String RazSocial;
	String contactoProveedor;
	String tipoPago;
	String fechaOrden;
	String tipoOperacion;
	
	public OrdenCompra() {
		super();
	}
	public String getInputCodigoBusqueda() {
		return inputCodigoBusqueda;
	}
	public void setInputCodigoBusqueda(String inputCodigoBusqueda) {
		this.inputCodigoBusqueda = inputCodigoBusqueda;
	}
	public String getCodOrdenCompra() {
		return codOrdenCompra;
	}
	public void setCodOrdenCompra(String codOrdenCompra) {
		this.codOrdenCompra = codOrdenCompra;
	}
	public String getCodProv() {
		return codProv;
	}
	public void setCodProv(String codProv) {
		this.codProv = codProv;
	}
	public String getRazSocial() {
		return RazSocial;
	}
	public void setRazSocial(String razSocial) {
		RazSocial = razSocial;
	}
	public String getContactoProveedor() {
		return contactoProveedor;
	}
	public void setContactoProveedor(String contactoProveedor) {
		this.contactoProveedor = contactoProveedor;
	}
	public String getTipoPago() {
		return tipoPago;
	}
	public void setTipoPago(String tipoPago) {
		this.tipoPago = tipoPago;
	}
	public String getFechaOrden() {
		return fechaOrden;
	}
	public void setFechaOrden(String fechaOrden) {
		this.fechaOrden = fechaOrden;
	}
	public String getTipoOperacion() {
		return tipoOperacion;
	}
	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}
	@Override
	public String toString() {
		return "OrdenCompra [inputCodigoBusqueda=" + inputCodigoBusqueda + ", codOrdenCompra=" + codOrdenCompra
				+ ", codProv=" + codProv + ", RazSocial=" + RazSocial + ", contactoProveedor=" + contactoProveedor
				+ ", tipoPago=" + tipoPago + ", fechaOrden=" + fechaOrden + ", tipoOperacion=" + tipoOperacion + "]";
	}
	

}
