package com.logia.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.coreweb.domain.Domain;
import com.coreweb.domain.Tipo;

public class Tenida extends Domain {

	private Date fecha;
	private Tipo grado;
	private String balaustre = "";
	private String balaustrePath = "";
	private Tipo periodo;
	private Tipo tipoTenida;
	private double importeSaco = 0;
	private MovimientoHospitalario movimientoHospitalario;
	private Set<TenidaAsistencia> asistencias = new HashSet<>();
	private List<TenidaAsistencia> asistenciasOrdenada = null;

	public List<TenidaAsistencia> getAsistenciasOrdenada() {
		Set<TenidaAsistencia> sta = this.getAsistencias();
		ArrayList<TenidaAsistencia> lta = new ArrayList<>();

		for (Iterator iterator = sta.iterator(); iterator.hasNext();) {
			TenidaAsistencia ta = (TenidaAsistencia) iterator.next();
			lta.add(ta);
		}

		Collections.sort(lta, new Comparator<TenidaAsistencia>() {
			@Override
			public int compare(TenidaAsistencia lhs, TenidaAsistencia rhs) {
				// -1 - less than, 1 - greater than, 0 - equal, all inversed for
				// descending
				String o1 = lhs.getCargo().getOrden();
				String o2 = rhs.getCargo().getOrden();

				return o1.compareTo(o2);
			}
		});

		return lta;

	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Tipo getGrado() {
		return grado;
	}

	public void setGrado(Tipo grado) {
		this.grado = grado;
	}

	public String getBalaustre() {
		return balaustre;
	}

	public void setBalaustre(String balaustre) {
		this.balaustre = balaustre;
	}

	public Tipo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Tipo periodo) {
		this.periodo = periodo;
	}

	public Set<TenidaAsistencia> getAsistencias() {
		return asistencias;
	}

	public void setAsistencias(Set<TenidaAsistencia> asistencias) {
		this.asistencias = asistencias;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Tipo getTipoTenida() {
		return tipoTenida;
	}

	public void setTipoTenida(Tipo tipoTenida) {
		this.tipoTenida = tipoTenida;
	}

	public String getBalaustrePath() {
		return balaustrePath;
	}

	public void setBalaustrePath(String balaustrePath) {
		this.balaustrePath = balaustrePath;
	}

	public double getImporteSaco() {
		return importeSaco;
	}

	public void setImporteSaco(double importeSaco) {
		this.importeSaco = importeSaco;
	}

	public MovimientoHospitalario getMovimientoHospitalario() {
		return movimientoHospitalario;
	}

	public void setMovimientoHospitalario(MovimientoHospitalario movimientoHospitalario) {
		this.movimientoHospitalario = movimientoHospitalario;
	}
	
	

}
