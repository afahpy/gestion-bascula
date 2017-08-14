package com.logia.domain;

import java.util.HashSet;
import java.util.Set;

import com.coreweb.domain.Domain;
import com.coreweb.domain.Tipo;

public class CapitacionHH extends Domain {

	private double montoAnual = 0;
	private Tipo periodo;
	private Tipo estado;
	private Tipo mesInicio;
	private Set<MovimientoTesoreria> movimientosCapitacion = new HashSet<MovimientoTesoreria>();

	public double getMontoAnual() {
		return montoAnual;
	}

	public void setMontoAnual(double montoAnual) {
		this.montoAnual = montoAnual;
	}

	public Tipo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Tipo periodo) {
		this.periodo = periodo;
	}

	public Tipo getEstado() {
		return estado;
	}

	public void setEstado(Tipo estado) {
		this.estado = estado;
	}

	public Set<MovimientoTesoreria> getMovimientosCapitacion() {
		return movimientosCapitacion;
	}

	public void setMovimientosCapitacion(Set<MovimientoTesoreria> movimientosCapitacion) {
		this.movimientosCapitacion = movimientosCapitacion;
	}

	public Tipo getMesInicio() {
		return mesInicio;
	}

	public void setMesInicio(Tipo mesInicio) {
		this.mesInicio = mesInicio;
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}

}
