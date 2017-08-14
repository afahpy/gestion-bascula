package com.logia.gestion;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListModels;

import com.coreweb.util.MyPair;
import com.logia.Configuracion;
import com.logia.GenericViewModelApp;
import com.logia.domain.CapitacionHH;
import com.logia.domain.Mason;
import com.logia.domain.MovimientoHospitalario;
import com.logia.domain.MovimientoTesoreria;

public class TallerTesoreriaCapHermVM extends GenericViewModelApp {

	private String mensajeErr = "";

	private String filtroNombreMason = "";
	private ListModel hermanosFiltro;
	private Mason selectedHermano = new Mason();
	private CapitacionHH capitacionHermano = new CapitacionHH();
	private boolean modoAgregarMovimiento = false;
	private MovimientoTesoreria movimiento = new MovimientoTesoreria();
	private String mesActual = "-no definido-";

	@Init(superclass = true)
	public void initTallerTesoreriaCapHermVM() throws Exception {
		int mesActual = Calendar.getInstance().get(Calendar.MONTH) + 0;
		this.mesActual = this.getUtilDto().getMeses().get(mesActual).getDescripcion() + ":";

	}

	@AfterCompose(superclass = true)
	public void afterComposeTallerTesoreriaCapHermVM() {
	}

	public boolean isEditable() throws Exception {
		boolean out = false;

		if (this.operacionHabilitada(Configuracion.ALIAS_PERM_EDITAR_TALLER_TESORERIA)) {
			out = true;
		}
		return out;
	}

	@Command
	@NotifyChange("hermanosFiltro")
	public void xactualizarHermanosFiltro() throws Exception {

		/*
		 * List<Mason> hermanos = new ArrayList<Mason>(); hermanos =
		 * rr.getMasonesOrderByCargo(); this.setHermanosFiltro(hermanos);
		 * 
		 * 
		 * ListModel l; return ListModels.toListSubModel(new
		 * ListModelList(hermanos), new MyComparatorHH(), 150);
		 */

	}

	public ListModel xgetHermanosFiltro() throws Exception {
		List<Mason> hermanos = new ArrayList<Mason>();
		hermanos = rr.getMasonesOrderByCargo();
		hermanosFiltro = ListModels.toListSubModel(new ListModelList(hermanos), new MyComparatorHH(), 150);
		return hermanosFiltro;
	}

	@Command
	@NotifyChange("*")
	public void onSelectedHermano() {

		boolean encontrado = false;

		if (this.selectedHermano != null) {

			this.filtroNombreMason = this.selectedHermano.getNombre();

			// Busca dentro del hermano seleccionado la capitacion
			// correspondiente
			// al periodo
			for (CapitacionHH capitacion : this.getSelectedHermano().getCapitacionesHH()) {
				if (capitacion.getPeriodo().getSigla().compareTo(this.getPeriodoCorrienteTipo().getSigla()) == 0) {
					this.capitacionHermano = capitacion;
					encontrado = true;
				}
			}
		}

		// Si no se encuentra la capitacion, se crea una nueva.
		if (encontrado == false) {
			this.capitacionHermano = new CapitacionHH();
			this.capitacionHermano.setPeriodo(this.getPeriodoCorrienteTipo());
			this.capitacionHermano.setMesInicio(this.getUtilDto().getMesEnero());
		}

	}

	@Command
	@NotifyChange("*")
	public void guardarCapitacion() throws Exception {

		if (this.validarCapitacion() == false) {
			this.mensajeError(this.mensajeErr);
			return;
		}

		if (this.capitacionHermano.esNuevo()) {
			this.capitacionHermano.setPeriodo(this.getPeriodoCorrienteTipo());
			rr.saveObject(capitacionHermano, this.getUs().getLogin());
			this.getSelectedHermano().getCapitacionesHH().add(this.capitacionHermano);
			rr.saveObject(selectedHermano, this.getUs().getLogin());
			this.mensajePopupTemporal("Guardado correctamente");

		} else {

			rr.saveObject(capitacionHermano, this.getUs().getLogin());
			this.mensajePopupTemporal("Guardado correctamente");

		}

	}

	@Command
	@NotifyChange("*")
	public void agregarMovimiento() throws Exception {

		if (this.validarMovimiento() == false) {
			this.mensajeError(this.mensajeErr);
			return;
		}

		MovimientoTesoreria movGuardar = new MovimientoTesoreria();
		movGuardar.setFecha(this.movimiento.getFecha());
		movGuardar.setTipoMovimiento(this.movimiento.getTipoMovimiento());

		double importe = 0;

		if (movGuardar.getTipoMovimiento().getId().longValue() == (this.getUtilDto().getTipoMovHHIngreso().getId())) {
			importe = Math.abs(this.movimiento.getImporte());
			movGuardar.setImporte(importe);
			movGuardar.setDescripcion("Ingreso del Q∴H∴ " + this.selectedHermano.getNombre() + " - "
					+ this.movimiento.getDescripcion());

		} else if (movGuardar.getTipoMovimiento().getId()
				.longValue() == (this.getUtilDto().getTipoMovHHEgreso().getId())) {
			importe = -1 * Math.abs(this.movimiento.getImporte());
			movGuardar.setImporte(importe);
			movGuardar.setDescripcion("Egreso del Q∴H∴ " + this.selectedHermano.getNombre() + " - "
					+ this.movimiento.getDescripcion());

		} else if (movGuardar.getTipoMovimiento().getId()
				.longValue() == (this.getUtilDto().getTipoMovHHPagoCap().getId())) {
			importe = Math.abs(this.movimiento.getImporte());
			movGuardar.setImporte(importe);
			movGuardar.setDescripcion("Pago capitación del Q∴H∴ " + this.selectedHermano.getNombre() + " - "
					+ this.movimiento.getDescripcion());

		}
		// Guarda el movimiento
		rr.saveObject(movGuardar, this.getUs().getLogin());

		// Guarda la asociacion a la capitacion.
		this.capitacionHermano.getMovimientosCapitacion().add(movGuardar);
		rr.saveObject(capitacionHermano, this.getUs().getLogin());
		this.mensajePopupTemporal("Movimiento agregado correctamente");
		this.actualizarModoAgregarMovimiento();
	}

	@Command
	@NotifyChange({ "modoAgregarMovimiento", "movimiento" })
	public void actualizarModoAgregarMovimiento() {

		// Limpia y carga los valores por defecto
		this.movimiento = new MovimientoTesoreria();
		this.movimiento.setFecha(new Date());
		this.movimiento.setTipoMovimiento(this.getUtilDto().getTipoMovHHPagoCap());

		// Actuaiza el modo agregar movimiento.
		this.modoAgregarMovimiento = !this.modoAgregarMovimiento;
	}

	public boolean validarMovimiento() {
		boolean out = true;
		this.mensajeErr = "No se puede realizar la operación debido a:";

		if (this.selectedHermano == null || this.selectedHermano.esNuevo() == true) {
			out = false;
			this.mensajeErr += "\n-Debe seleccionar primero un hermano.";
		}

		if (this.capitacionHermano == null || this.capitacionHermano.esNuevo() == true) {
			out = false;
			this.mensajeErr += "\n-Debe guardar primero la capitacion.";
		}

		if (this.movimiento.getFecha() == null) {
			out = false;
			this.mensajeErr += "\n-Debe asignar una fecha.";
		}

		if (this.movimiento.getDescripcion().trim().length() == 0) {
			out = false;
			this.mensajeErr += "\n-Debe introducir una descripción.";
		}

		if (this.movimiento.getImporte() == 0) {
			out = false;
			this.mensajeErr += "\n-Debe asignar un importe.";
		}

		return out;

	}

	public boolean validarCapitacion() {
		boolean out = true;
		this.mensajeErr = "No se puede realizar la operación debido a:";

		if (this.selectedHermano == null || this.selectedHermano.esNuevo() == true) {
			out = false;
			this.mensajeErr += "\n-Debe seleccionar un hermano.";
		}

		if (this.capitacionHermano.getMontoAnual() <= 0) {
			out = false;
			this.mensajeErr += "\n-El monto anual debe ser mayor que 0.";
		}

		if (this.capitacionHermano.getMesInicio() == null || this.capitacionHermano.getMesInicio().esNuevo() == true) {
			out = false;
			this.mensajeErr += "\n-Debe seleccionar el mes de inicio.";
		}

		return out;

	}

	public double getMontoPagosCapitaciones() {
		double monto = 0;

		for (MovimientoTesoreria m : this.capitacionHermano.getMovimientosCapitacion()) {
			if (m.getTipoMovimiento().getId().longValue() == this.getUtilDto().getTipoMovHHPagoCap().getId()
					.longValue()) {
				monto += m.getImporte();
			}
		}

		return monto;
	}

	public double getSaldoCapitaciones() {
		double saldo = 0;

		saldo = this.getMontoPagosCapitaciones() - this.capitacionHermano.getMontoAnual();

		return saldo;
	}

	public double getMontoProyectadoMesActual() {
		double monto = 0;

		if (this.capitacionHermano.esNuevo() == false) {
			double montoAnual = this.capitacionHermano.getMontoAnual();
			double montoPagado = this.getMontoPagosCapitaciones();
			double mesInicio = Double.parseDouble(this.capitacionHermano.getMesInicio().getOrden());
			double mesActual = Calendar.getInstance().get(Calendar.MONTH) + 1;

			monto = montoPagado - (montoAnual / (13 - mesInicio) * (mesActual - mesInicio + 1));

			System.out.println(montoPagado + "- (13 -" + mesInicio + ") * (" + mesActual + " - " + mesInicio + " )");
		}

		return monto;
	}

	public double getSaldoOtrosMovimientos() {
		double monto = 0;

		for (MovimientoTesoreria m : this.capitacionHermano.getMovimientosCapitacion()) {
			if (m.getTipoMovimiento().getId().longValue() != this.getUtilDto().getTipoMovHHPagoCap().getId()
					.longValue()) {
				monto += m.getImporte();
			}
		}

		return monto;
	}

	/******************** GET Y SET ************************/

	public boolean isModoAgregarMovimiento() {
		return modoAgregarMovimiento;
	}

	public void setModoAgregarMovimiento(boolean modoAgregarMovimiento) {
		this.modoAgregarMovimiento = modoAgregarMovimiento;
	}

	public MovimientoTesoreria getMovimiento() {
		return movimiento;
	}

	public void setMovimiento(MovimientoTesoreria movimiento) {
		this.movimiento = movimiento;
	}

	public String getFiltroNombreMason() {
		return filtroNombreMason;
	}

	public void setFiltroNombreMason(String filtroNombreMason) {
		this.filtroNombreMason = filtroNombreMason;
	}

	public Mason getSelectedHermano() {
		return selectedHermano;
	}

	@NotifyChange("*")
	public void setSelectedHermano(Mason selectedHermano) {
		this.selectedHermano = selectedHermano;
		this.onSelectedHermano();
	}

	public CapitacionHH getCapitacionHermano() {
		return capitacionHermano;
	}

	public void setCapitacionHermano(CapitacionHH capitacionHermano) {
		this.capitacionHermano = capitacionHermano;
	}

	public String getMesActual() {
		return mesActual;
	}

	public void setMesActual(String mesActual) {
		this.mesActual = mesActual;
	}

}

class MyComparatorHH implements Comparator {

	@Override
	public int compare(Object o1, Object o2) {

		String m1 = ((String) o1).toLowerCase();
		String m2 = ((Mason) o2).getNombre().toLowerCase();

		if (m2.indexOf(m1) >= 0) {
			return 0;
		}
		return -1;

	}

}
