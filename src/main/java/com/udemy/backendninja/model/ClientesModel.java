package com.udemy.backendninja.model;

import java.util.HashSet;
import java.util.Set;

import com.udemy.backendninja.entity.Cabordenventa;

public class ClientesModel {
	private String codcliente;
	private int idCliente;
	private String nombrecliente;
	private String appatcliente;
	private String apmatcliente;
	private String razonsocialcliente;
	private String rfccliente;
	private String ubigeocliente;
	private String telefonocliente;
	private String correocliente;
	private Set<Cabordenventa> cabordenventas = new HashSet<Cabordenventa>(0);
	
	public ClientesModel() {
		
	}
	
	public String getCodcliente() {
		return codcliente;
	}
	public void setCodcliente(String codcliente) {
		this.codcliente = codcliente;
	}
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int IdCliente) {
		this.idCliente = IdCliente;
	}
	public String getNombrecliente() {
		return nombrecliente;
	}
	public void setNombrecliente(String nombrecliente) {
		this.nombrecliente = nombrecliente;
	}
	public String getAppatcliente() {
		return appatcliente;
	}
	public void setAppatcliente(String appatcliente) {
		this.appatcliente = appatcliente;
	}
	public String getApmatcliente() {
		return apmatcliente;
	}
	public void setApmatcliente(String apmatcliente) {
		this.apmatcliente = apmatcliente;
	}
	public String getRazonsocialcliente() {
		return razonsocialcliente;
	}
	public void setRazonsocialcliente(String razonsocialcliente) {
		this.razonsocialcliente = razonsocialcliente;
	}
	public String getRfccliente() {
		return rfccliente;
	}
	public void setRfccliente(String rfccliente) {
		this.rfccliente = rfccliente;
	}
	public String getUbigeocliente() {
		return ubigeocliente;
	}
	public void setUbigeocliente(String ubigeocliente) {
		this.ubigeocliente = ubigeocliente;
	}
	public String getTelefonocliente() {
		return telefonocliente;
	}
	public void setTelefonocliente(String telefonocliente) {
		this.telefonocliente = telefonocliente;
	}
	public String getCorreocliente() {
		return correocliente;
	}
	public void setCorreocliente(String correocliente) {
		this.correocliente = correocliente;
	}
	public Set<Cabordenventa> getCabordenventas() {
		return cabordenventas;
	}
	public void setCabordenventas(Set<Cabordenventa> cabordenventas) {
		this.cabordenventas = cabordenventas;
	}
	public String toString() {
		return getAppatcliente()+" "+getApmatcliente()+", "+getNombrecliente();
	}
	
	
}
