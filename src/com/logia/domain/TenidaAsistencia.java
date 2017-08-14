package com.logia.domain;

import com.coreweb.domain.Domain;
import com.coreweb.domain.Tipo;

public class TenidaAsistencia extends Domain {

	private Mason mason; // cuando es null, es un invitado
	private String nombreH = "";
	private Tipo cargo;

	public Mason getMason() {
		return mason;
	}

	public void setMason(Mason mason) {
		this.mason = mason;
	}

	public Tipo getCargo() {
		return cargo;
	}

	public void setCargo(Tipo cargo) {
		this.cargo = cargo;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getNombreH() {
		return nombreH;
	}

	public void setNombreH(String nombreH) {
		this.nombreH = nombreH;
	}

}
