package com.udemy.backendninja.model;

public class Producto {
	private int idprod;
	private String codprod;
	private String nombreprod;
	private String descripcionprod;
	private double precioventaprod;
	private int cantidadprod;
	private int idinventory;
	private double precio;
	
	public Producto() {
		
	}
	
	
	public double getPrecio() {
		return precio;
	}


	public void setPrecio(double precio) {
		this.precio = precio;
	}


	public int getIdprod() {
		return idprod;
	}
	public void setIdprod(int idprod) {
		this.idprod = idprod;
	}
	public String getCodprod() {
		return codprod;
	}
	public void setCodprod(String codprod) {
		this.codprod = codprod;
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
	public double getPrecioventaprod() {
		return precioventaprod;
	}
	public void setPrecioventaprod(double precioventaprod) {
		this.precioventaprod = precioventaprod;
	}
	public int getCantidadprod() {
		return cantidadprod;
	}
	public void setCantidadprod(int cantidadprod) {
		this.cantidadprod = cantidadprod;
	}
	public int getIdinventory() {
		return idinventory;
	}
	public void setIdinventory(int idinventory) {
		this.idinventory = idinventory;
	}
	
	
}
