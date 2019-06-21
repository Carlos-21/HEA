package com.udemy.backendninja.entity;
// Generated 18-nov-2018 22:05:07 by Hibernate Tools 4.3.5.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Rol generated by hbm2java
 */
@Entity
@Table(name = "ROL", schema = "EMPRESA")
public class Rol implements java.io.Serializable {

	private long userRoleId;
	private String nombrerol;
	private String username;

	public Rol() {
	}

	public Rol(long userRoleId, String username) {
		this.userRoleId = userRoleId;
		this.username = username;
	}

	public Rol(long userRoleId, String nombrerol, String username) {
		this.userRoleId = userRoleId;
		this.nombrerol = nombrerol;
		this.username = username;
	}

	@Id

	@Column(name = "USER_ROLE_ID", unique = true, nullable = false, precision = 10, scale = 0)
	public long getUserRoleId() {
		return this.userRoleId;
	}

	public void setUserRoleId(long userRoleId) {
		this.userRoleId = userRoleId;
	}

	@Column(name = "NOMBREROL", length = 40)
	public String getNombrerol() {
		return this.nombrerol;
	}

	public void setNombrerol(String nombrerol) {
		this.nombrerol = nombrerol;
	}

	@Column(name = "USERNAME", nullable = false, length = 45)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}