package com.udemy.backendninja.entity;
// Generated 18-nov-2018 22:05:07 by Hibernate Tools 4.3.5.Final

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Detordenventa generated by hbm2java
 */
@Entity
@Table(name = "DETORDENVENTA", schema = "EMPRESA")
public class Detordenventa implements java.io.Serializable {

	private DetordenventaId id;
	private Productos productos;
	private Usuarios usuarios;

	public Detordenventa() {
	}

	public Detordenventa(DetordenventaId id) {
		this.id = id;
	}

	public Detordenventa(DetordenventaId id, Productos productos, Usuarios usuarios) {
		this.id = id;
		this.productos = productos;
		this.usuarios = usuarios;
	}

	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "dovId", column = @Column(name = "DOV_ID", precision = 22, scale = 0)),
			@AttributeOverride(name = "dovCodordenventa", column = @Column(name = "DOV_CODORDENVENTA", length = 5)),
			@AttributeOverride(name = "dovCodproducto", column = @Column(name = "DOV_CODPRODUCTO", length = 5)),
			@AttributeOverride(name = "dovFechordenventa", column = @Column(name = "DOV_FECHORDENVENTA")),
			@AttributeOverride(name = "dovUserventa", column = @Column(name = "DOV_USERVENTA", precision = 5, scale = 0)) })
	public DetordenventaId getId() {
		return this.id;
	}

	public void setId(DetordenventaId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOV_CODPRODUCTO", insertable = false, updatable = false)
	public Productos getProductos() {
		return this.productos;
	}

	public void setProductos(Productos productos) {
		this.productos = productos;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOV_USERVENTA", insertable = false, updatable = false)
	public Usuarios getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(Usuarios usuarios) {
		this.usuarios = usuarios;
	}

}
