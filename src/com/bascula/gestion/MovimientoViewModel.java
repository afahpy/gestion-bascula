package com.bascula.gestion;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

import com.bascula.Configuracion;
import com.bascula.GenericViewModelApp;
import com.bascula.domain.MovimientoDetalle;
import com.bascula.domain.MovimientoEntradaSalida;
import com.bascula.domain.RegisterDomain;
import com.bascula.gestion.reportes.ReporteListaMovimientoEntradaSalida;
import com.bascula.leerPeso.BasculaPeso;
import com.coreweb.extras.reporte.ViewPdf;

public class MovimientoViewModel extends GenericViewModelApp {

	private MovimientoEntradaSalida movimiento = new MovimientoEntradaSalida();
	private String mensajeError = "";
	private String modoFormulario = Configuracion.MODO_FORMULARIO_VISTA;
	private BasculaPeso peso = new BasculaPeso();
	private static final String CAMPO_BRUTO = "CAMPO_BRUTO";
	private static final String CAMPO_TARA = "CAMPO_TARA";
	private List<MovimientoDetalle> selectedDetalles = new ArrayList<MovimientoDetalle>();

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

		if (this.movimiento != null && this.movimiento.esNuevo() == false) {
			MovimientoEntradaSalida movAnterior = (MovimientoEntradaSalida) rr
					.getObject(MovimientoEntradaSalida.class.getName(), this.movimiento.getId());

			if (movAnterior.getModificado().compareTo(this.movimiento.getModificado()) > 0) {
				this.mensajeError(
						"El movimiento que esta intentando guardar se encuentra desactualizado, favor vuelva a abrirlo."
								+ " Ultima modificacion: "
								+ this.m.dateToString(movAnterior.getModificado(), "dd-MM-yyyy HH:mm:ss")
								+ " por usuario: " + movAnterior.getUsuarioMod());
				return;
			}

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

	public BasculaPeso getPeso() {
		return peso;
	}

	public void setPeso(BasculaPeso peso) {
		this.peso = peso;
	}

	public boolean isCampoEditable(String aliasPermiso) throws Exception {
		boolean out = false;

		if (this.modoFormulario.compareTo(Configuracion.MODO_FORMULARIO_EDICION) == 0
				&& this.operacionHabilitada(aliasPermiso) == true) {
			out = true;
		}
		return out;
	}

	@Command
	@NotifyChange("movimiento")
	public boolean copiarPesoToCampo(@BindingParam("campo") String campo) throws Exception {

		boolean out = false;

		if (campo.compareTo(CAMPO_BRUTO) == 0) {
			this.movimiento.setBruto(this.peso.getPeso());
		} else if (campo.compareTo(CAMPO_TARA) == 0) {
			this.movimiento.setTara(this.peso.getPeso());

		}

		return out;
	}

	public boolean isFormularioModoEdicion() {

		boolean out = false;

		if (this.modoFormulario.compareTo(Configuracion.MODO_FORMULARIO_EDICION) == 0) {
			out = true;
		}
		return out;

	}

	@Command
	@NotifyChange("movimiento")
	public void nuevoDetalle() {
		MovimientoDetalle detalle = new MovimientoDetalle();
		this.movimiento.getDetalles().add(detalle);
	}

	@Command
	@NotifyChange({ "movimiento", "selectedDetalles" })
	public void borrarDetalles() throws Exception {

		if (this.selectedDetalles.size() == 0) {
			this.mensajeInfo("Debe seleccionar detalles a eliminar.");
			return;
		}

		for (MovimientoDetalle detalle : this.selectedDetalles) {
			this.movimiento.getDetalles().remove(detalle);
		}

		this.selectedDetalles = new ArrayList<MovimientoDetalle>();

	}

	public List<MovimientoDetalle> getSelectedDetalles() {
		return selectedDetalles;
	}

	public void setSelectedDetalles(List<MovimientoDetalle> selectedDetalles) {
		this.selectedDetalles = selectedDetalles;
	}

	@Command
	public void verReporte() throws Exception {

		ReporteListaMovimientoEntradaSalida reporte = new ReporteListaMovimientoEntradaSalida();

		reporte.setMovimiento(this.movimiento);
		ViewPdf vp = new ViewPdf();
		vp.showReporte(reporte, this);

	}
}
