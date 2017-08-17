package com.bascula.domain;

import java.util.Date;

import com.coreweb.domain.Domain;
import com.coreweb.domain.Tipo;

public class MovimientoEntradaSalida extends Domain {

	private static final long serialVersionUID = 9032366622942001572L;

	private Tipo tipoMovimiento; // Entrada, Salida
	private Date fechaLlegada;
	private Date fechaSalida;
	private double bruto = 0.0;
	private double tara = 0.0;
	private double neto = 0.0; // bruto - tara
	private double origen = 0.0;
	private double diferencia = 0.0; // origen - neto
	private MyObject chapa;
	private MyObject chapaCarreta;
	private MyObject chofer;
	private MyObject transportadora;
	private String despacho = "";
	private MyObject despachante;

	public Tipo getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(Tipo tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public Date getFechaLlegada() {
		return fechaLlegada;
	}

	public void setFechaLlegada(Date fechaLlegada) {
		this.fechaLlegada = fechaLlegada;
	}

	public Date getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public double getBruto() {
		return bruto;
	}

	public void setBruto(double bruto) {
		this.bruto = bruto;
	}

	public double getTara() {
		return tara;
	}

	public void setTara(double tara) {
		this.tara = tara;
	}

	public double getNeto() {
		return neto;
	}

	public void setNeto(double neto) {
		this.neto = neto;
	}

	public double getOrigen() {
		return origen;
	}

	public void setOrigen(double origen) {
		this.origen = origen;
	}

	public double getDiferencia() {
		return diferencia;
	}

	public void setDiferencia(double diferencia) {
		this.diferencia = diferencia;
	}

	public MyObject getChapa() {
		return chapa;
	}

	public void setChapa(MyObject chapa) {
		this.chapa = chapa;
	}

	public MyObject getChapaCarreta() {
		return chapaCarreta;
	}

	public void setChapaCarreta(MyObject chapaCarreta) {
		this.chapaCarreta = chapaCarreta;
	}

	public MyObject getChofer() {
		return chofer;
	}

	public void setChofer(MyObject chofer) {
		this.chofer = chofer;
	}

	public MyObject getTransportadora() {
		return transportadora;
	}

	public void setTransportadora(MyObject transportadora) {
		this.transportadora = transportadora;
	}

	public String getDespacho() {
		return despacho;
	}

	public void setDespacho(String despacho) {
		this.despacho = despacho;
	}

	public MyObject getDespachante() {
		return despachante;
	}

	public void setDespachante(MyObject despachante) {
		this.despachante = despachante;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
