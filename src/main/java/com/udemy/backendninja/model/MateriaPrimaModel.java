package com.udemy.backendninja.model;

public class MateriaPrimaModel {

	private long idmatprim;
	private String codmatprima;
	private String nombrematprima;
	private double preciomatprima;
	private int cantidadmatprima;
	private String idcategory;
	private String descmatprima;

	public MateriaPrimaModel() {
	}
	
	

	public MateriaPrimaModel(long idmatprim, String codmatprima, String nombrematprima, double preciomatprima,
			int cantidadmatprima, String idcategory, String descmatprima) {
		super();
		this.idmatprim = idmatprim;
		this.codmatprima = codmatprima;
		this.nombrematprima = nombrematprima;
		this.preciomatprima = preciomatprima;
		this.cantidadmatprima = cantidadmatprima;
		this.idcategory = idcategory;
		this.descmatprima = descmatprima;
	}



	public long getIdmatprim() {
		return idmatprim;
	}

	public void setIdmatprim(long idmatprim) {
		this.idmatprim = idmatprim;
	}

	public String getCodmatprima() {
		return codmatprima;
	}

	public void setCodmatprima(String codmatprima) {
		this.codmatprima = codmatprima;
	}

	public String getNombrematprima() {
		return nombrematprima;
	}

	public void setNombrematprima(String nombrematprima) {
		this.nombrematprima = nombrematprima;
	}

	public double getPreciomatprima() {
		return preciomatprima;
	}

	public void setPreciomatprima(double preciomatprima) {
		this.preciomatprima = preciomatprima;
	}

	public int getCantidadmatprima() {
		return cantidadmatprima;
	}

	public void setCantidadmatprima(int cantidadmatprima) {
		this.cantidadmatprima = cantidadmatprima;
	}

	public String getIdcategory() {
		return idcategory;
	}

	public void setIdcategory(String idcategory) {
		this.idcategory = idcategory;
	}

	public String getDescmatprima() {
		return descmatprima;
	}

	public void setDescmatprima(String descmatprima) {
		this.descmatprima = descmatprima;
	}

	@Override
	public String toString() {
		return "codmatprima:" + codmatprima + ",nombrematprima:" + nombrematprima + ",preciomatprima:" + preciomatprima
				+ ",cantidadmatprima:" + cantidadmatprima + ",idcategory:" + idcategory + ",descmatprima:"
				+ descmatprima;
	}

}
