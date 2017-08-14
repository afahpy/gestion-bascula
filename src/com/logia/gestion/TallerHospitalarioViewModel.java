package com.logia.gestion;

import java.util.ArrayList;
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
import com.logia.domain.MovimientoHospitalario;
import com.logia.domain.RegisterDomain;

public class TallerHospitalarioViewModel extends GenericViewModelApp {

	private String mensajeErr = "";

	private boolean modoAgregarMovimiento = false;
	private MovimientoHospitalario movimiento = new MovimientoHospitalario();

	@Init(superclass = true)
	public void initTallerHospitalarioViewModel() throws Exception {
	}

	@AfterCompose(superclass = true)
	public void afterComposeTallerHospitalarioViewModel() {
	}

	@Command
	public void cambiarPantalla(@BindingParam("url") String url) {
		this.saltoDePagina(url);
		MyPair m = this.getGradoUsuarioCorriente();
		this.mensajePopupTemporalWarning("El nivel de mason es : " + m.getText());
	}

	public boolean isEditable() throws Exception {
		boolean out = false;

		if (this.operacionHabilitada(Configuracion.ALIAS_PERM_EDITAR_TALLER_HOSPITALARIO)) {
			out = true;
		}
		return out;
	}

	public List<MovimientoHospitalario> getMovimientoHospitalario() throws Exception {
		List<MovimientoHospitalario> movimientos = new ArrayList<MovimientoHospitalario>();
		movimientos = rr.getMovimientosHospitalario();
		return movimientos;
	}

	public double getSumaSaldo() throws Exception {

		double suma = 0;

		suma = rr.getSumaSaldoMovimientosHospitalario();

		return suma;

	}

	@Command
	@NotifyChange("*")
	public void agregarMovimiento() throws Exception {

		if (this.validarMovimiento() == false) {
			this.mensajeError(this.mensajeErr);
			return;
		}

		MovimientoHospitalario movGuardar = new MovimientoHospitalario();
		movGuardar.setFecha(this.movimiento.getFecha());
		movGuardar.setTipoMovimiento(this.movimiento.getTipoMovimiento());
		movGuardar.setDescripcion(this.movimiento.getDescripcion());

		double importe = 0;

		if (movGuardar.getTipoMovimiento().getId()
				.compareTo(this.getUtilDto().getTipoMovimientoIngreso().getId()) == 0) {
			importe = Math.abs(this.movimiento.getImporte());
			movGuardar.setImporte(importe);
		} else if (movGuardar.getTipoMovimiento().getId()
				.compareTo(this.getUtilDto().getTipoMovimientoEgreso().getId()) == 0) {
			importe = -1 * Math.abs(this.movimiento.getImporte());
			movGuardar.setImporte(importe);
		}

		rr.saveObject(movGuardar, this.getUs().getLogin());
		this.mensajePopupTemporal("Movimiento agregado correctamente");
		this.actualizarModoAgregarMovimiento();
	}

	@Command
	@NotifyChange({ "modoAgregarMovimiento", "movimiento" })
	public void actualizarModoAgregarMovimiento() {
		this.movimiento = new MovimientoHospitalario();
		this.movimiento.setTipoMovimiento(this.getUtilDto().getTipoMovimientoIngreso());
		this.modoAgregarMovimiento = !this.modoAgregarMovimiento;
	}

	public boolean isModoAgregarMovimiento() {
		return modoAgregarMovimiento;
	}

	public void setModoAgregarMovimiento(boolean modoAgregarMovimiento) {
		this.modoAgregarMovimiento = modoAgregarMovimiento;
	}

	public MovimientoHospitalario getMovimiento() {
		return movimiento;
	}

	public void setMovimiento(MovimientoHospitalario movimiento) {
		this.movimiento = movimiento;
	}

	public boolean validarMovimiento() {
		boolean out = true;
		this.mensajeErr = "No se puede realizar la operación debido a:";

		if (this.movimiento.getFecha() == null) {
			out = false;
			this.mensajeErr += "\n-Debe asignar una fecha.";
		}

		if (this.movimiento.getDescripcion().trim().length() == 0) {
			out = false;
			this.mensajeErr += "\n-Debe introducir una descripción.";
		}

		if (this.movimiento.getTipoMovimiento() == null) {
			out = false;
			this.mensajeErr += "\n-Debe seleccionar el tipo de movimiento.";
		}

		if (this.movimiento.getImporte() == 0) {
			out = false;
			this.mensajeErr += "\n-Debe asignar un importe.";
		}

		return out;

	}

}
