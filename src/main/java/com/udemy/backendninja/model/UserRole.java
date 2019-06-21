package com.udemy.backendninja.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "rol", uniqueConstraints = @UniqueConstraint(columnNames = { "nombrerol", "username" }))
public class UserRole {

	@Id
	@GeneratedValue
	@Column(name = "user_role_id",unique=true,nullable=false)
	private int userRoleIdD;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "username",referencedColumnName="username",nullable=false)
	private User usuario;
	@Column(name = "nombrerol",nullable=false,length=45)
	private String role;

	public UserRole() {

	}

	public UserRole(int userRoleIdD, User user, String role) {
		this.userRoleIdD = userRoleIdD;
		this.usuario = user;
		this.role = role;
	}

	public int getUserRoleIdD() {
		return userRoleIdD;
	}

	public void setUserRoleIdD(int userRoleIdD) {
		this.userRoleIdD = userRoleIdD;
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
