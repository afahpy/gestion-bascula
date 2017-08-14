package com.logia.domain;

import java.util.Date;

import com.coreweb.domain.Domain;
import com.coreweb.domain.Tipo;

public class MovimientoHospitalario extends Domain {

	private Date fecha = new Date();
	private Tipo tipoMovimiento;// ingreso, egreso
	private double importe = 0;
	private String descripcion = "";

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Tipo getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(Tipo tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
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

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
