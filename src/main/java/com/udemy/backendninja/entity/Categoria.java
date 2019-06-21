package com.udemy.backendninja.entity;
// Generated 18-nov-2018 22:05:07 by Hibernate Tools 4.3.5.Final

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Categoria generated by hbm2java
 */
@Entity
@Table(name = "CATEGORIA", schema = "EMPRESA", uniqueConstraints = @UniqueConstraint(columnNames = "NOMBRECATEGORIA"))
public class Categoria implements java.io.Serializable {

	private String codcategoria;
	private BigDecimal idcategoria;
	private String nombrecategoria;

	public Categoria() {
	}

	public Categoria(String codcategoria, String nombrecategoria) {
		this.codcategoria = codcategoria;
		this.nombrecategoria = nombrecategoria;
	}

	public Categoria(String codcategoria, BigDecimal idcategoria, String nombrecategoria) {
		this.codcategoria = codcategoria;
		this.idcategoria = idcategoria;
		this.nombrecategoria = nombrecategoria;
	}

	@Id

	@Column(name = "CODCATEGORIA", unique = true, nullable = false, length = 5)
	public String getCodcategoria() {
		return this.codcategoria;
	}

	public void setCodcategoria(String codcategoria) {
		this.codcategoria = codcategoria;
	}

	@Column(name = "IDCATEGORIA", precision = 22, scale = 0)
	public BigDecimal getIdcategoria() {
		return this.idcategoria;
	}

	public void setIdcategoria(BigDecimal idcategoria) {
		this.idcategoria = idcategoria;
	}

	@Column(name = "NOMBRECATEGORIA", unique = true, nullable = false, length = 50)
	public String getNombrecategoria() {
		return this.nombrecategoria;
	}

	public void setNombrecategoria(String nombrecategoria) {
		this.nombrecategoria = nombrecategoria;
	}

}
