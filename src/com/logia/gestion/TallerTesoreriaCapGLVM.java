package com.logia.gestion;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.BindUtils;
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

import com.coreweb.util.MyArray;
import com.coreweb.util.MyPair;
import com.logia.Configuracion;
import com.logia.GenericViewModelApp;
import com.logia.domain.CapitacionGL;
import com.logia.domain.CapitacionHH;
import com.logia.domain.Mason;
import com.logia.domain.MovimientoHospitalario;
import com.logia.domain.MovimientoTesoreria;
import com.logia.domain.RegisterDomain;
import com.logia.domain.Tenida;

public class TallerTesoreriaCapGLVM extends GenericViewModelApp {

	private RegisterDomain rr = RegisterDomain.getInstance();

	private String mensajeErr = "";
	private Date fecha = new Date();
	private String descripcion = "";
	private double importeMensual = 0;
	private List<MyArray> capitaciones = new ArrayList<MyArray>();

	private boolean modoAddOtroMov = false;
	private List<MyArray> otrosPagos = new ArrayList<MyArray>();
	private String otroPagoDescripcion = "";
	private double otroPagoImporte = 0;

	@Init(superclass = true)
	public void initTallerTesoreriaCapGLVM() throws Exception {
		this.fecha = new Date();
		this.descripcion = "";
		this.importeMensual = Double
				.parseDouble(this.getSisProp().getPropiedad(Configuracion.SIS_PRO_IMPORTE_MENSUAL_CAPITACION_GL));

		this.capitaciones = this.generarListaMasonesCapitacion();

	}

	@AfterCompose(superclass = true)
	public void afterComposeTallerTesoreriaCapGLVM() {
	}

	public boolean isEditable() throws Exception {
		boolean out = false;

		if (this.operacionHabilitada(Configuracion.ALIAS_PERM_EDITAR_TALLER_TESORERIA)) {
			out = true;
		}
		return out;
	}

	@Command
	@NotifyChange({ "modoAddOtroMov", "otroPagoDescripcion", "otroPagoImporte" })
	public void actualizarModoAddOtroMov() {
		this.otroPagoDescripcion = "";
		this.otroPagoImporte = 0;
		this.modoAddOtroMov = !this.modoAddOtroMov;
	}

	public boolean validarOtroMov() {
		boolean out = true;
		this.mensajeErr = "No se puede realizar la operación debido a:";

		if (this.otroPagoDescripcion.trim().length() == 0) {
			out = false;
			this.mensajeErr += "\n-Debe asignar una descripción";
		}

		if (this.otroPagoImporte == 0) {
			out = false;
			this.mensajeErr += "\n-El importe no puede ser 0";
		}

		return out;
	}

	@Command
	@NotifyChange({ "otrosPagos", "totalOtrosMovimientos", "totalPagar", "modoAddOtroMov", "otroPagoDescripcion",
			"otroPagoImporte" })
	public void addOtroMov() {

		if (this.validarOtroMov() == false) {
			this.mensajeError(this.mensajeErr);
			return;
		}

		MyArray otroMov = new MyArray();
		otroMov.setPos1(this.getOtroPagoDescripcion());
		otroMov.setPos2(this.getOtroPagoImporte());

		this.getOtrosPagos().add(otroMov);

		this.actualizarModoAddOtroMov();
	}

	public double getTotalCapitaciones() {
		double totalPagar = 0;

		for (MyArray m : this.capitaciones) {
			double importe = (double) m.getPos4();
			totalPagar += importe;
		}

		return totalPagar;
	}

	public double getTotalOtrosMovimientos() {
		double totalPagar = 0;

		for (MyArray m : this.otrosPagos) {
			double importe = (double) m.getPos2();
			totalPagar += importe;
		}

		return totalPagar;
	}

	public double getTotalPagar() {
		return this.getTotalCapitaciones() + this.getTotalOtrosMovimientos();
	}

	public List<MyArray> generarListaMasonesCapitacion() throws Exception {

		List<MyArray> masonesCapitacion = new ArrayList<MyArray>();

		List<Mason> masones = rr.getMasones();
		List<CapitacionGL> capitacionesGlHermano = null;

		for (Mason m : masones) {

			int mes = 0;
			int ultimoPago = 0;
			String sugerenciaPagoMeses = "";
			int cantidadMesesSugeridos = 0;

			capitacionesGlHermano = rr.getCapitacionesGlByHermano(m, this.getPeriodoCorrienteTipo());

			for (CapitacionGL capGl : capitacionesGlHermano) {

				mes = Integer.parseInt(capGl.getMes().getOrden());// Convierte a
																	// mes

				if (mes > ultimoPago) {
					ultimoPago = mes;
				}

			}

			if (ultimoPago < this.getMesActualInt()) {
				for (int i = ultimoPago; i < this.getMesActualInt(); i++) {
					sugerenciaPagoMeses += (i + 1) + "-";
					cantidadMesesSugeridos += 1;
				}
			}

			MyArray masonCapitacion = new MyArray();
			masonCapitacion.setPos1(m);
			masonCapitacion.setPos2(Integer.toString(ultimoPago));
			masonCapitacion.setPos3(sugerenciaPagoMeses);
			masonCapitacion.setPos4(cantidadMesesSugeridos * this.getImporteMensual());

			masonesCapitacion.add(masonCapitacion);

		}

		return masonesCapitacion;
	}

	public boolean validarGuardar() {
		boolean out = true;
		this.mensajeErr = "No se puede realizar la operación debido a:";

		if (this.importeMensual <= 0) {
			out = false;
			this.mensajeErr += "\n- El importe mensual no puede ser menor o igual a 0";
		}

		if (this.descripcion.trim().length() == 0) {
			out = false;
			this.mensajeErr += "\n- Debe agregar una descripción";
		}

		if (this.fecha == null) {
			out = false;
			this.mensajeErr += "\n- Debe seleccionar una fecha";
		}

		return out;
	}

	@Command
	@NotifyChange("*")
	public void guardar() throws Exception {

		String textoMensajeConfir = " Una vez realizado el pago, no podra revertirse. Esta seguro que desea continuar? ";

		if (this.mensajeSiNo(textoMensajeConfir) == false) {
			return;
		}

		// Primero validar
		if (this.validarGuardar() == false) {
			this.mensajeError(this.mensajeErr);
			return;
		}

		double totalImporteCapitaciones = 0;

		// Luego guardar cada las capitaciones de cada mason
		for (MyArray capitacion : this.capitaciones) {

			// Obtiene el string a parsear
			String mesesPagar = (String) capitacion.getPos3();

			// Si el string no esta vacio intentara parsearlo a los meses
			// correspondientes
			if (mesesPagar.trim().length() != 0) {

				String[] mesesPagarSep = mesesPagar.split("-");

				for (int i = 0; i < mesesPagarSep.length; i++) {

					// Recupera cada mes a pagar del string
					int mesConverted = Integer.parseInt(mesesPagarSep[i]);

					// Guarda el mes a pagar de acuerdo a si es entre 1-12
					if (mesConverted > 0 && mesConverted <= 12) {

						CapitacionGL capitacionGl = new CapitacionGL();
						capitacionGl.setFecha(this.getFecha());
						capitacionGl.setDescripcion(this.getDescripcion());
						capitacionGl.setMason((Mason) capitacion.getPos1());
						capitacionGl.setImporte(this.getImporteMensual());
						capitacionGl.setPeriodo(getPeriodoCorrienteTipo());
						capitacionGl.setMes(this.getMesTipoByInt(mesConverted));
						rr.saveObject(capitacionGl, this.getUs().getLogin());

						totalImporteCapitaciones += capitacionGl.getImporte();

					}
				}

			}

		}

		// Guardar los otros pagos en movimiento tesoreria

		for (MyArray otroPago : this.otrosPagos) {

			MovimientoTesoreria movTesCap = new MovimientoTesoreria();
			movTesCap.setFecha(this.getFecha());
			movTesCap.setDescripcion(("Pago a la GL∴|" + otroPago.getPos1() + "|" + this.getTotalPagar() + ".- Gs"));
			movTesCap.setTipoMovimiento(this.getUtilDto().getTipoMovimientoEgreso());
			movTesCap.setImporte((double) otroPago.getPos2() * -1);

			rr.saveObject(movTesCap, this.getUs().getLogin());

		}

		// Generar los movimientos tesoreria del conjunto de capitaciones.

		MovimientoTesoreria movTesCap = new MovimientoTesoreria();
		movTesCap.setFecha(this.getFecha());
		movTesCap.setDescripcion("Pago Cap∴ a la GL∴|" + this.getDescripcion() + "|" + this.getTotalPagar() + ".- Gs");
		movTesCap.setTipoMovimiento(this.getUtilDto().getTipoMovimientoEgreso());
		movTesCap.setImporte(totalImporteCapitaciones * -1);

		rr.saveObject(movTesCap, this.getUs().getLogin());

		this.mensajePopupTemporal("Pago realizado correctamente!...");

		this.saltoDePagina(Configuracion.URL_TALLER_TESORERO);
	}

	@Command
	@NotifyChange({"totalCapitaciones", "totalPagar"})
	public void actualizarListaCapitaciones(@BindingParam("rowSelected") MyArray rowSelected) {

		String mesesPagar = (String) rowSelected.getPos3();
		double cantidadMeses = 0;

		// Si el string no esta vacio intentara parsearlo a los meses
		// correspondientes
		if (mesesPagar.trim().length() != 0) {

			String[] mesesPagarSep = mesesPagar.split("-");

			for (int i = 0; i < mesesPagarSep.length; i++) {

				// Recupera cada mes a pagar del string
				int mesConverted = Integer.parseInt(mesesPagarSep[i]);

				// Guarda el mes a pagar de acuerdo a si es entre 1-12
				if (mesConverted > 0 && mesConverted <= 12) {
					cantidadMeses += 1;

				}
			}

		}

		rowSelected.setPos4(cantidadMeses * this.importeMensual);

		BindUtils.postNotifyChange(null, null, rowSelected, "pos4");

	}

	/**************** Get y Set ********************/

	public String getMensajeErr() {
		return mensajeErr;
	}

	public void setMensajeErr(String mensajeErr) {
		this.mensajeErr = mensajeErr;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getImporteMensual() {
		return importeMensual;
	}

	public void setImporteMensual(double importeMensual) {
		this.importeMensual = importeMensual;
	}

	public List<MyArray> getCapitaciones() {
		return capitaciones;
	}

	public void setCapitaciones(List<MyArray> capitaciones) {
		this.capitaciones = capitaciones;
	}

	public List<MyArray> getOtrosPagos() {
		return otrosPagos;
	}

	public void setOtrosPagos(List<MyArray> otrosPagos) {
		this.otrosPagos = otrosPagos;
	}

	public String getOtroPagoDescripcion() {
		return otroPagoDescripcion;
	}

	public void setOtroPagoDescripcion(String otroPagoDescripcion) {
		this.otroPagoDescripcion = otroPagoDescripcion;
	}

	public double getOtroPagoImporte() {
		return otroPagoImporte;
	}

	public void setOtroPagoImporte(double otroPagoImporte) {
		this.otroPagoImporte = otroPagoImporte;
	}

	public boolean isModoAddOtroMov() {
		return modoAddOtroMov;
	}

	public void setModoAddOtroMov(boolean modoAddOtroMov) {
		this.modoAddOtroMov = modoAddOtroMov;
	}

}
