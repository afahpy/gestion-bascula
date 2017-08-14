package com.coreweb.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("serial")
public class Usuario extends Domain {

	private String nombre;
	private String login;
	private String clave;
	private String motivoDeInactivacion;
	private boolean activo = true;
	private Date fechaDeIngreso;
	private Date fechaDeInactivacion;
	private Set<PermisoEditar> permisos = new HashSet<PermisoEditar>();
	private String extra = "";

	public Usuario() {

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public int compareTo(Object arg0) {
		System.out.println(this.getClass().getName() + " - compareTo NO IMPLEMENTADO");
		return -1;
	}

	public String toString() {
		String out = "";
		out += "N:" + this.nombre + " L:" + this.login + " C:" + this.clave;
		return out;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String getMotivoDeInactivacion() {
		return motivoDeInactivacion;
	}

	public void setMotivoDeInactivacion(String motivoDeInactivacion) {
		this.motivoDeInactivacion = motivoDeInactivacion;
	}

	public Date getFechaDeIngreso() {
		return fechaDeIngreso;
	}

	public void setFechaDeIngreso(Date fechaDeIngreso) {
		this.fechaDeIngreso = fechaDeIngreso;
	}

	public Date getFechaDeInactivacion() {
		return fechaDeInactivacion;
	}

	public void setFechaDeInactivacion(Date fechaDeInactivacion) {
		this.fechaDeInactivacion = fechaDeInactivacion;
	}

	public Set<PermisoEditar> getPermisos() {
		return permisos;
	}

	public void setPermisos(Set<PermisoEditar> permisos) {
		this.permisos = permisos;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

}
