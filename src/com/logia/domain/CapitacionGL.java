package com.logia.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.coreweb.domain.Domain;
import com.coreweb.domain.Tipo;

public class CapitacionGL extends Domain {

	private Tipo periodo;
	private Date fecha;
	private double importe = 0;
	private String descripcion = "";
	private Tipo mes;
	private Mason mason;

	public Tipo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Tipo periodo) {
		this.periodo = periodo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Tipo getMes() {
		return mes;
	}

	public void setMes(Tipo mes) {
		this.mes = mes;
	}

	public Mason getMason() {
		return mason;
	}

	public void setMason(Mason mason) {
		this.mason = mason;
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}

}
