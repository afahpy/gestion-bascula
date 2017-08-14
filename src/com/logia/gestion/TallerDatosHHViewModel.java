package com.logia.gestion;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import com.coreweb.util.MyPair;
import com.logia.Configuracion;
import com.logia.GenericViewModelApp;
import com.logia.domain.Mason;
import com.logia.domain.MovimientoHospitalario;
import com.logia.domain.RegisterDomain;
import com.logia.domain.Tenida;

public class TallerDatosHHViewModel extends GenericViewModelApp {

	private String ordenHH =  Configuracion.ORDEN_MAS_CARGO;
	
	private String ordenNombre = "";
	private String ordenCargo = "";
	private String ordenGrado = "";
	private String ordenEstado = "";
	
	@Init(superclass = true)
	public void initTallerDatosHHViewModel() throws Exception {
		this.setOrdenMasones(Configuracion.ORDEN_MAS_CARGO);
	}

	@AfterCompose(superclass = true)
	public void afterComposeTallerDatosHHViewModel() {
	}

	
	private void setOrdenMasones(String orden){
		// setearle el orden que se usa en el query
		ordenHH = orden;
		// para poner la marca de orden en la web
		ordenNombre = "";
		ordenCargo = "";
		ordenGrado = "";
		ordenEstado = "";
		if (orden.compareTo(Configuracion.ORDEN_MAS_NOMBRE)==0){
			ordenNombre = " (*)";
		}
		if (orden.compareTo(Configuracion.ORDEN_MAS_CARGO)==0){
			ordenCargo = " (*)";
		}
		if (orden.compareTo(Configuracion.ORDEN_MAS_GRADO)==0){
			ordenGrado = " (*)";
		}
		if (orden.compareTo(Configuracion.ORDEN_MAS_ESTADO)==0){
			ordenEstado = " (*)";
		}
	}
	
	
	public List<Mason> getHermanos() throws Exception {
		List<Mason> hermanos = new ArrayList<Mason>();
		hermanos = rr.getMasones(ordenHH);
		return hermanos;
	}

	
	@Command 
	@NotifyChange ({"hermanos","ordenNombre","ordenCargo", "ordenGrado", "ordenEstado"})
	public void orderHH(@BindingParam("orden") String orden){
		this.setOrdenMasones(orden);
	}
	
	
	@Command
	public void irDatosHermano(@BindingParam("h") Mason h){
		Hashtable<String, Object> params = new Hashtable<String, Object>();
		params.put("HERMANO", h);
		this.saltoDePagina(Configuracion.URL_DATOS_PERSONALES_EDIT, params);
		
	}

	public String getOrdenNombre() {
		return ordenNombre;
	}

	public void setOrdenNombre(String ordenNombre) {
		this.ordenNombre = ordenNombre;
	}

	public String getOrdenCargo() {
		return ordenCargo;
	}

	public void setOrdenCargo(String ordenCargo) {
		this.ordenCargo = ordenCargo;
	}

	public String getOrdenGrado() {
		return ordenGrado;
	}

	public void setOrdenGrado(String ordenGrado) {
		this.ordenGrado = ordenGrado;
	}

	public String getOrdenEstado() {
		return ordenEstado;
	}

	public void setOrdenEstado(String ordenEstado) {
		this.ordenEstado = ordenEstado;
	}
	
	
	
	
}
