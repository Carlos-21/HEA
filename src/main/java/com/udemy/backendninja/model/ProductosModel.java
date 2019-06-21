package com.udemy.backendninja.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.udemy.backendninja.entity.Detordenventa;
import com.udemy.backendninja.entity.Inventario;

public class ProductosModel {
	private String codprod;
	private Inventario inventario;
	private BigDecimal idprod;
	private String nombreprod;
	private String descripcionprod;
	private Set<Detordenventa> detordenventas = new HashSet<Detordenventa>(0);
	private BigDecimal precio;
	// Campos adicionales a la entidad
	private int cantidad;

	public ProductosModel() {

	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public String getCodprod() {
		return codprod;
	}

	public void setCodprod(String codprod) {
		this.codprod = codprod;
	}

	public Inventario getInventario() {
		return inventario;
	}

	public void setInventario(Inventario inventario) {
		this.inventario = inventario;
	}

	public BigDecimal getIdprod() {
		return idprod;
	}

	public void setIdprod(BigDecimal idprod) {
		this.idprod = idprod;
	}

	public String getNombreprod() {
		return nombreprod;
	}

	public void setNombreprod(String nombreprod) {
		this.nombreprod = nombreprod;
	}

	public String getDescripcionprod() {
		return descripcionprod;
	}

	public void setDescripcionprod(String descripcionprod) {
		this.descripcionprod = descripcionprod;
	}

	public Set<Detordenventa> getDetordenventas() {
		return detordenventas;
	}

	public void setDetordenventas(Set<Detordenventa> detordenventas) {
		this.detordenventas = detordenventas;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

}
