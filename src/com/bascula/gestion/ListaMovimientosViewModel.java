package com.bascula.gestion;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.batik.dom.svg.ListHandler;
import org.hamcrest.core.IsInstanceOf;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import com.bascula.Configuracion;
import com.bascula.GenericViewModelApp;
import com.bascula.ID;
import com.bascula.domain.MovimientoEntradaSalida;
import com.bascula.domain.RegisterDomain;

public class ListaMovimientosViewModel extends GenericViewModelApp {

	static String KEY_FILTRO_COL = "filtroColumnas";

	private List<MovimientoEntradaSalida> movimientos = new ArrayList<MovimientoEntradaSalida>();
	private MovimientoEntradaSalida movTempSumas = new MovimientoEntradaSalida();

	private List<MovimientoEntradaSalida> selectedMovimientos = new ArrayList<MovimientoEntradaSalida>();

	private FiltroMovimiento verCol = new FiltroMovimiento();

	/*
	private String filTipoMovimiento = "";//
	private String filLugarOrigen = "";
	private String filLugarDestino = "";
	private String filRemito = "";
	private String filRemision = "";
	private String filChapa = "";
	private String filChapaCarreta = "";
	private String filChofer = "";
	private String filTransportadora = "";
	private String filDespacho = "";
	private String filDespachante = "";

	private Date filtroFechaLlegadaDesde = null;
	private Date filtroFechaLlegadaHasta = null;

	private Date filtroFechaSalidaDesde = null;
	private Date filtroFechaSalidaHasta = null;
	*/

	@Init(superclass = true)
	public void initMovimientosViewModel() throws Exception {

		this.verCol = this.getFiltro(KEY_FILTRO_COL);
		this.filtrar();

	}

	@AfterCompose(superclass = true)
	public void afterComposeMovimientosViewModel() {
	}

	@Command
	public void grabarVerColumnas(@BindingParam("header") Object dato) throws Exception {

		org.zkoss.zul.Listhead listaHead = (org.zkoss.zul.Listhead) dato;

		// busca en el header el estado de cada col, y graba eso
		for (int i = 0; i < listaHead.getChildren().size(); i++) {
			Object headCol =  listaHead.getChildren().get(i);
			if (headCol  instanceof org.zkoss.zul.Listheader) {
				org.zkoss.zul.Listheader he = (org.zkoss.zul.Listheader) headCol;
				String id = he.getId();
				// cada col que me interesa empieza con idH+[nombreAtriuto]
				if (id.indexOf("idH")==0){
					String key = id.substring(3);
					boolean visible = he.isVisible();
					String width = he.getWidth();
					this.m.setValue(this.verCol, key, visible);
					this.m.setValue(this.verCol, key+"Int", width);
					System.out.println(key+"Int:"+ width);
				}
				
			}
		}
		
		this.grabaFiltro(this.verCol, KEY_FILTRO_COL);
		this.mensajePopupTemporal("Columnas grabadas..");
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
		params.put("movimiento", movimiento);
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

	@Command
	public void crearMovimiento() {
		Hashtable<String, Object> params = new Hashtable<String, Object>();
		params.put("movimiento", new MovimientoEntradaSalida());
		params.put("modoFormulario", Configuracion.MODO_FORMULARIO_EDICION);
		this.saltoDePagina(Configuracion.URL_MOVIMIENTO_EDIT, params);

	}

	@Command
	@NotifyChange("*")
	public void eliminarMovimientos() throws Exception {

		RegisterDomain rr = RegisterDomain.getInstance();

		if (this.selectedMovimientos.size() <= 0) {
			this.mensajeError("Debe seleccionar uno o mas elementos.");
			return;
		}

		if (this.mensajeSiNo("Esta seguro que desea eliminar los elementos seleccionados?")) {
			for (MovimientoEntradaSalida mov : this.selectedMovimientos) {
				rr.deleteObject(mov);
			}

			this.mensajeInfo("Se han eliminado los elementos seleccionados");
			this.selectedMovimientos = new ArrayList<MovimientoEntradaSalida>();
			this.filtrar();
		}

	}

	@Command
	@NotifyChange({"movimientos", "movTempSumas"})
	public void filtrar() throws Exception {

		this.movimientos = rr.getMovimientos(this.verCol);

		this.movTempSumas = new MovimientoEntradaSalida();

		for (MovimientoEntradaSalida mov : movimientos) {
			this.movTempSumas.setBruto(this.movTempSumas.getBruto() + mov.getBruto());
			this.movTempSumas.setTara(this.movTempSumas.getTara() + mov.getTara());
			this.movTempSumas.setNeto(this.movTempSumas.getNeto() + mov.getNeto());
			this.movTempSumas.setOrigen(this.movTempSumas.getOrigen() + mov.getOrigen());
			this.movTempSumas.setDiferencia(this.movTempSumas.getDiferencia() + mov.getDiferencia());
		}

	}

	@Command
	@NotifyChange("*")
	public void limpiarFiltros() throws Exception {

		this.verCol.limpiarFiltro();
		this.filtrar();

	}

	@Command
	@NotifyChange("*")
	public void reporteMovimiento() throws Exception {

		Map args = new HashMap();
		args.put("vmMov", this);
		Window w = (Window) Executions.createComponents(ID.ZUL_FILTRO_MOVIMIENTOS, null, args);
		w.setPosition("center");
		w.doModal();
	}


	@Command
	@NotifyChange("*")
	public void reporteStock() throws Exception {

		Map args = new HashMap();
		args.put("vmMov", this);
		Window w = (Window) Executions.createComponents(ID.ZUL_FILTRO_MOVIMIENTOS, null, args);
		w.setPosition("center");
		w.doModal();
	}
	
	
	
	public MovimientoEntradaSalida getMovTempSumas() {
		return movTempSumas;
	}

	public void setMovTempSumas(MovimientoEntradaSalida movTempSumas) {
		this.movTempSumas = movTempSumas;
	}

	public FiltroMovimiento getVerCol() {
		return verCol;
	}

	public void setVerCol(FiltroMovimiento verCol) {
		this.verCol = verCol;
	}

	/*
	 * 
	 * @Command
	 * 
	 * @NotifyChange("verCol") public void clickTara(){
	 * this.mensajeInfo("this.verCol.isTara():"+this.verCol.isTara());
	 * this.verCol.setTara(false); this.mensajePopupTemporal("click tara"); }
	 */
}
