package com.bascula.domain;

import com.coreweb.domain.Domain;
import com.coreweb.domain.Tipo;

public class MyObject extends Domain {

	private static final long serialVersionUID = 987156350052919341L;
	
	private Tipo tipoObjeto;
	private String strCampo1 = "";
	private String strCampo1Alias = "";
	private String strCampo2 = "";
	private String strCampo2Alias = "";

	public String toString(){
		String out = strCampo1;
		if (strCampo1 == null){
			out = "";
		}
		return out;
	}
	
	
	public Tipo getTipoObjeto() {
		return tipoObjeto;
	}

	public void setTipoObjeto(Tipo tipoObjeto) {
		this.tipoObjeto = tipoObjeto;
	}

	public String getStrCampo1() {
		return strCampo1;
	}

	public void setStrCampo1(String strCampo1) {
		this.strCampo1 = strCampo1;
	}

	public String getStrCampo1Alias() {
		return strCampo1Alias;
	}

	public void setStrCampo1Alias(String strCampo1Alias) {
		this.strCampo1Alias = strCampo1Alias;
	}

	public String getStrCampo2() {
		return strCampo2;
	}

	public void setStrCampo2(String strCampo2) {
		this.strCampo2 = strCampo2;
	}

	public String getStrCampo2Alias() {
		return strCampo2Alias;
	}

	public void setStrCampo2Alias(String strCampo2Alias) {
		this.strCampo2Alias = strCampo2Alias;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
