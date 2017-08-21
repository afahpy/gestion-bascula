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
import com.bascula.domain.RegisterDomain;

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

	@Command
	public void guardarMovimiento() throws Exception {

		if (this.validarFormulario() == false) {
			this.mensajeError(this.mensajeError);
			return;
		}

		rr.saveObject(this.movimiento, this.getUs().getLogin());
		this.mensajePopupTemporal("Movimiento guardado correctamente");
	}

	@Command
	public void cancelar() throws Exception {
		this.saltoDePagina("/bascula/gestion/movimientos_lista.zul");
	}

	@AfterCompose(superclass = true)
	public void afterComposeMovimientosViewModel() {
	}

	@Command
	public void cambiarPantalla(@BindingParam("url") String url) {
		this.saltoDePagina(url);
	}

	public MovimientoEntradaSalida getMovimiento() {
		return movimiento;
	}

	public void setMovimiento(MovimientoEntradaSalida movimiento) {
		this.movimiento = movimiento;
	}

	public boolean validarFormulario() {
		boolean out = true;
		this.mensajeError = "No se puede realizar la operación debido a:";

		return out;
	}

}
