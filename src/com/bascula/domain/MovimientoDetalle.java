package com.bascula.domain;

import com.coreweb.domain.Domain;

public class MovimientoDetalle extends Domain {

	private static final long serialVersionUID = 4510362610678672381L;

	private int numeroBolsa = 0;
	private MyObject mercaderia;

	public int getNumeroBolsa() {
		return numeroBolsa;
	}

	public void setNumeroBolsa(int numeroBolsa) {
		this.numeroBolsa = numeroBolsa;
	}

	public MyObject getMercaderia() {
		return mercaderia;
	}

	public void setMercaderia(MyObject mercaderia) {
		this.mercaderia = mercaderia;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
