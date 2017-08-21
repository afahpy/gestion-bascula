package com.bascula.gestion;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;

import com.bascula.Configuracion;
import com.bascula.GenericViewModelApp;
import com.bascula.domain.MovimientoEntradaSalida;
import com.logia.domain.RegisterDomain;

public class MovimientoViewModel extends GenericViewModelApp {

	private MovimientoEntradaSalida movimiento = new MovimientoEntradaSalida();
	private String mensajeError = "";
	private String modoFormulario = Configuracion.MODO_FORMULARIO_VISTA;

	@Init(superclass = true)
	public void initMovimientosViewModel(@ExecutionArgParam("movimiento") MovimientoEntradaSalida movimiento,
		@ExecutionArgParam("modoFormulario") String modoFormulario) throws Exception {

	if (movimiento != null && movimiento.esNuevo() == false) {
		this.movimiento = movimiento;
	} else {
		this.movimiento = new MovimientoEntradaSalida();
	}

	if (modoFormulario != null) {
		this.modoFormulario = modoFormulario;
	}

}

	@AfterCompose(superclass = true)
	public void afterComposeMovimientosViewModel() {
	}

	@Command
	public void cambiarPantalla(@BindingParam("url") String url) {
		this.saltoDePagina(url);
	}

}
