package com.udemy.backendninja.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "usuarios")
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "idusuario",unique=true,nullable=false,length=5)
	private int iduser;
	@Column(name = "username",unique=true,nullable=false,length=45)
	private String username;
	@Column(name = "contrasena",nullable=false,length=60)
	private String contrasena;
	@Column(name = "nombreusuario")
	private String nombreusuario;
	@Column(name = "apellidopaternousuario")
	private String apellidopaternousuario;
	@Column(name = "apellidomaternousuario")
	private String apellidomaternousuario;
	@Column(name = "emailusuario")
	private String emailusuario;
	@Column(name = "edadusuario")
	private int edadusuario;
	@Column(name = "telefono")
	private String telefono; // 111-111
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "usuario")
	private Set<UserRole> userRole = new HashSet<UserRole>();

	public User() {

	}

	public User(int iduser, String username, String contrasena, String nombreusuario, String apellidopaternousuario,
			String apellidomaternousuario, String emailusuario, int edadusuario, String telefono,
			Set<UserRole> userRole) {
		super();
		this.iduser = iduser;
		this.username = username;
		this.contrasena = contrasena;
		this.nombreusuario = nombreusuario;
		this.apellidopaternousuario = apellidopaternousuario;
		this.apellidomaternousuario = apellidomaternousuario;
		this.emailusuario = emailusuario;
		this.edadusuario = edadusuario;
		this.telefono = telefono;
		this.userRole = userRole;
	}

	public User(int iduser, String username, String contrasena, String nombreusuario, String apellidopaternousuario,
			String apellidomaternousuario, String emailusuario, int edadusuario, String telefono) {
		super();
		this.iduser = iduser;
		this.username = username;
		this.contrasena = contrasena;
		this.nombreusuario = nombreusuario;
		this.apellidopaternousuario = apellidopaternousuario;
		this.apellidomaternousuario = apellidomaternousuario;
		this.emailusuario = emailusuario;
		this.edadusuario = edadusuario;
		this.telefono = telefono;
	}

	public int getIduser() {
		return iduser;
	}

	public void setIduser(int iduser) {
		this.iduser = iduser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getNombreusuario() {
		return nombreusuario;
	}

	public void setNombreusuario(String nombreusuario) {
		this.nombreusuario = nombreusuario;
	}

	public String getApellidopaternousuario() {
		return apellidopaternousuario;
	}

	public void setApellidopaternousuario(String apellidopaternousuario) {
		this.apellidopaternousuario = apellidopaternousuario;
	}

	public String getApellidomaternousuario() {
		return apellidomaternousuario;
	}

	public void setApellidomaternousuario(String apellidomaternousuario) {
		this.apellidomaternousuario = apellidomaternousuario;
	}

	public String getEmailusuario() {
		return emailusuario;
	}

	public void setEmailusuario(String emailusuario) {
		this.emailusuario = emailusuario;
	}

	public int getEdadusuario() {
		return edadusuario;
	}

	public void setEdadusuario(int edadusuario) {
		this.edadusuario = edadusuario;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Set<UserRole> getUserRole() {
		return userRole;
	}

	public void setUserRole(Set<UserRole> userRole) {
		this.userRole = userRole;
	}

	
}
