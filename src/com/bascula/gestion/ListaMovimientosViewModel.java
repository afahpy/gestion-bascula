package com.bascula.gestion;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;

import com.bascula.Configuracion;
import com.bascula.GenericViewModelApp;
import com.bascula.domain.MovimientoEntradaSalida;
import com.logia.domain.RegisterDomain;

public class ListaMovimientosViewModel extends GenericViewModelApp {

	private List<MovimientoEntradaSalida> movimientos = new ArrayList<MovimientoEntradaSalida>();
	private List<MovimientoEntradaSalida> selectedMovimientos = new ArrayList<MovimientoEntradaSalida>();

	@Init(superclass = true)
	public void initMovimientosViewModel() throws Exception {
		movimientos = rr.getMovimientos();
	}

	@AfterCompose(superclass = true)
	public void afterComposeMovimientosViewModel() {
	}

	@Command
	public void cambiarPantalla(@BindingParam("url") String url) {
		this.saltoDePagina(url);
	}

	public List<MovimientoEntradaSalida> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(List<MovimientoEntradaSalida> movimientos) {
		this.movimientos = movimientos;
	}

	public List<MovimientoEntradaSalida> getSelectedMovimientos() {
		return selectedMovimientos;
	}

	public void setSelectedMovimientos(List<MovimientoEntradaSalida> selectedMovimientos) {
		this.selectedMovimientos = selectedMovimientos;
	}

	@Command
	public void verMovimiento(@BindingParam("movimiento") MovimientoEntradaSalida movimiento) {
		Hashtable<String, Object> params = new Hashtable<String, Object>();
		params.put("deuda", movimiento);
		params.put("modoFormulario", Configuracion.MODO_FORMULARIO_VISTA);
		this.saltoDePagina(Configuracion.URL_MOVIMIENTO_EDIT, params);

	}

	@Command
	public void editarMovimiento(@BindingParam("movimiento") MovimientoEntradaSalida movimiento) {
		Hashtable<String, Object> params = new Hashtable<String, Object>();
		params.put("movimiento", movimiento);
		params.put("modoFormulario", Configuracion.MODO_FORMULARIO_EDICION);
		this.saltoDePagina(Configuracion.URL_MOVIMIENTO_EDIT, params);

	}

}
