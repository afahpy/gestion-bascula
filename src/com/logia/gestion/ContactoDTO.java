package com.logia.gestion;

import com.coreweb.dto.DTO;

public class ContactoDTO extends DTO {

	private String nombre = "";
	private String emailContacto = "";
	private String mensaje = "";

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmailContacto() {
		return emailContacto;
	}

	public void setEmailContacto(String emailContacto) {
		this.emailContacto = emailContacto;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}
