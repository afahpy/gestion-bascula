package com.logia.gestion;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Iframe;

import com.coreweb.Config;
import com.coreweb.domain.Tipo;
import com.coreweb.extras.archivos.TipoFileHtml;
import com.coreweb.util.MyPair;
import com.logia.Configuracion;
import com.logia.GenericViewModelApp;
import com.logia.domain.Mason;
import com.logia.domain.MovimientoHospitalario;
import com.logia.domain.RegisterDomain;
import com.logia.domain.Tenida;
import com.logia.domain.TenidaAsistencia;

public class TenidaVM extends GenericViewModelApp {

	private String mensajeError = "";

	private Tenida tenida = new Tenida();

	private String filtroNombreMason = "";

	List<Mason> hermanosFiltro = new ArrayList<Mason>();

	private Mason selectedHermano;

	private Tipo selectedCargo;

	private boolean modoAgregarAsistencia = false;

	private boolean asistenteInvitado = false;

	private String nombreAsistente = "";

	private boolean modoVerArchivo = false;

	@Init(superclass = true)
	public void initTallerTenidaVM(@ExecutionArgParam("TENIDA_SELECTED") Tenida tenidaSelected,
			@ExecutionArgParam("IS_NEW_TENIDA") boolean isNewTenida) throws Exception {

		// Si es una nueva tenida
		if (isNewTenida == true) {
			this.tenida = new Tenida();

			// Asignacion de valores por defecto para la tenida.
			this.tenida.setGrado(this.getUtilDto().getAprendizTipo());
			this.tenida.setTipoTenida(this.getUtilDto().getTenidaSimple());
			this.tenida.setBalaustre("Número de Balaus∴");
			this.tenida.setPeriodo(this.getPeriodoCorrienteTipo());
			this.tenida.setFecha(new Date());
		} else {
			// Si ya existe la tenida
			this.tenida = (Tenida) rr.getObject(Tenida.class.getName(), tenidaSelected.getId());
		}
	}

	@AfterCompose(superclass = true)
	public void afterComposeTenidaVM() {
	}

	@Command
	public void cambiarPantalla(@BindingParam("url") String url) {
		this.saltoDePagina(url);
	}

	/**
	 * Retorna true si la tenida tiene permiso de edicion y false si no cuenta
	 * con el permiso.
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean isEditable() throws Exception {
		boolean out = false;

		if (this.operacionHabilitada(Configuracion.ALIAS_PERM_EDITAR_TENIDAS)) {
			out = true;
		}
		return out;
	}

	public boolean isEditableAfterSave() throws Exception {
		boolean out = false;

		if (this.operacionHabilitada(Configuracion.ALIAS_PERM_EDITAR_TENIDAS) && this.tenida.esNuevo() == true) {
			out = true;
		}
		return out;
	}

	@Command
	@NotifyChange("*")
	public void guardarTenida() throws Exception {

		if (this.validarCampos() == false) {
			this.mensajeError(this.mensajeError);
			return;
		}

		// si hay algún importe se graba. Puede que se haya grabado antes,
		// entonces tiene que actualizar el importe
		if ((this.tenida.getImporteSaco() > 0) || (this.tenida.getMovimientoHospitalario() != null)) {

			// saber si tiene movimiento creado, puede que no.
			if (this.tenida.getMovimientoHospitalario() == null) {
				this.tenida.setMovimientoHospitalario(new MovimientoHospitalario());
			}

			this.tenida.getMovimientoHospitalario().setFecha(this.tenida.getFecha());
			this.tenida.getMovimientoHospitalario().setTipoMovimiento(this.getUtilDto().getTipoMovimientoIngreso());
			this.tenida.getMovimientoHospitalario().setDescripcion(
					"Tronc∴Benef∴ de la Ten∴ " + this.m.dateToString(this.tenida.getFecha(), "yyyy_MM_dd"));
			this.tenida.getMovimientoHospitalario().setImporte(this.tenida.getImporteSaco());

			this.rr.saveObject(this.tenida.getMovimientoHospitalario(), this.getUs().getLogin());
		}

		this.rr.saveObject(this.tenida, this.getUs().getLogin());

		this.crearDirectoriosTenida();

		this.mensajePopupTemporal(Configuracion.MENSAJE_GENERICO_GUARDAR_OK);
	}

	private boolean validarCampos() {
		this.mensajeError = "No se puede realizar la operacion debido a:";
		boolean out = true;

		if (this.tenida.getFecha() == null) {
			out = false;
			this.mensajeError += "\n- Debe asinar una fecha.";

		}

		if (this.tenida.getGrado() == null) {
			out = false;
			this.mensajeError += "\n- Debe seleccionar un grado.";

		}

		if (this.tenida.getTipoTenida() == null) {
			out = false;
			this.mensajeError += "\n- Debe seleccionar el tipo de tenida.";

		}

		if (this.tenida.getPeriodo() == null) {
			out = false;
			this.mensajeError += "\n- Debe seleccionar el periodo.";

		}

		return out;

	}

	@Command
	@NotifyChange("adjuntoVisible")
	public void subirArchivo(@BindingParam("evt") UploadEvent event) {

		if (this.validarBalaustre() == false) {
			this.mensajeError(this.mensajeError);
			return;
		}

		String urlFile = this.getPathBalaustreByGrado();
		File file = new File(urlFile);
		String formatedFileName = event.getMedia().getName().trim();
		formatedFileName = formatedFileName.replace(" ", "_");
		String path = file.getPath() + "/" + "tenida_" + this.m.dateToString(this.tenida.getFecha(), "yyyy_MM_dd") + "_"
				+ formatedFileName;
		m.uploadFile(path, (InputStream) new ByteArrayInputStream(event.getMedia().getByteData()));
		this.tenida.setBalaustrePath(path);
		this.mensajePopupTemporal("Archivo subido correctamente");
	}

	private boolean validarBalaustre() {
		this.mensajeError = "No se puede realizar la operacion debido a:";
		boolean out = true;

		if (this.tenida.getBalaustre().trim().length() == 0) {
			out = false;
			this.mensajeError += "\n- Debe asignar un nombre al balaustre.";
		}

		return out;

	}

	private boolean validarAsistencia() {
		this.mensajeError = "No se puede realizar la operacion debido a:";
		boolean out = true;

		if (this.tenida == null || this.tenida.esNuevo() == true) {
			out = false;
			this.mensajeError += "\n- Primero debe guardar la tenida.";
		}

		if (this.asistenteInvitado == false && this.selectedHermano == null) {
			out = false;
			this.mensajeError += "\n- Debe seleccionar un hermano.";
		}

		if (this.asistenteInvitado == true && this.nombreAsistente.trim().length() == 0) {
			out = false;
			this.mensajeError += "\n- Debe ingresar el nombre del asistente.";
		}

		if (this.selectedCargo == null) {
			out = false;
			this.mensajeError += "\n- Debe seleccionar un cargo.";
		}

		return out;

	}

	@Command
	@NotifyChange("hermanosFiltro")
	public void actualizarHermanosFiltro() throws Exception {

		List<Mason> hermanos = new ArrayList<Mason>();
		hermanos = rr.getMasonesByName(this.filtroNombreMason);
		this.setHermanosFiltro(hermanos);

	}

	@Command
	@NotifyChange("modoAgregarAsistencia")
	public void cambiarModoAgregarAsistencia() {
		this.modoAgregarAsistencia = !this.modoAgregarAsistencia;
	}

	@Command
	@NotifyChange({ "tenida", "modoAgregarAsistencia", "filtroNombreMason", "selectedCargo" })
	public void agregarAsistencia() throws Exception {

		if (this.validarAsistencia() == false) {
			this.mensajeError(this.mensajeError);
			return;
		}

		TenidaAsistencia nuevaAsistencia = new TenidaAsistencia();

		nuevaAsistencia.setCargo(this.getSelectedCargo());
		if (this.asistenteInvitado == true) {
			nuevaAsistencia.setNombreH(this.getNombreAsistente().trim());
			nuevaAsistencia.setMason(null);
		} else {
			nuevaAsistencia.setMason(this.getSelectedHermano());
			nuevaAsistencia.setNombreH(this.getSelectedHermano().getNombre());
		}

		boolean existe = false;
		// verificar que no esté cargado ya
		for (TenidaAsistencia asistencia : this.tenida.getAsistencias()) {

			if ((this.asistenteInvitado == true) && (asistencia.getMason() == null)) {
				// comparar sólo si es invitado
				if (nuevaAsistencia.getNombreH().compareTo(asistencia.getNombreH()) == 0) {
					existe = true;
				}

			} else {
				// buscar el id
				if ((this.asistenteInvitado == false)&&(asistencia.getMason() != null)) {
					if (asistencia.getMason().getId().longValue() == nuevaAsistencia.getMason().getId().longValue()) {
						existe = true;
					}
				}
			}

		} // for
		if (existe == true) {
			this.mensajeError("La asistencia para " + nuevaAsistencia.getMason().getNombre() + " ya fue cargada.");
			return;
		}

		
		
		
		this.tenida.getAsistencias().add(nuevaAsistencia);

		this.setSelectedCargo(new Tipo());
		this.setSelectedHermano(null);
		this.setFiltroNombreMason("");
		this.setAsistenteInvitado(false);
		this.setNombreAsistente("");

		this.guardarTenida();
		this.cambiarModoAgregarAsistencia();

	}

	public boolean isModoVerArchivo() {
		return modoVerArchivo;
	}

	public void setModoVerArchivo(boolean modoVerArchivo) {
		this.modoVerArchivo = modoVerArchivo;
	}

	public void cambiarModoverArchivos() {
		this.modoVerArchivo = !this.modoVerArchivo;
	}

	@Command
	@NotifyChange("asistenteInvitado")
	public void refreshAsistenteInvitado() {
		System.out.println("Entra por el refresh, ahora invitado = " + this.asistenteInvitado);
	}

	@Command
	@NotifyChange("*")
	public void clickFile() throws Exception {

		File file = new File(this.tenida.getBalaustrePath());

		AMedia media = new AMedia(file, null, null);

		Component compTool = Path.getComponent(Config.ZKOSS_PATH_FULL_INCLUDE_LATERAL + "/tallerTenidasViewEdit");
		Iframe iframe = (Iframe) compTool.getFellow("idIframeArchivo");
		iframe.setContent(media);
		iframe.setWidth("100%");
		iframe.setStyle("border: none;overflow:hidden;");
		iframe.setScrolling("yes");
		iframe.setWidgetListener("onBind", "var f = document.getElementById('" + iframe.getUuid()
				+ "');f.height=f.contentWindow.document.body.scrollHeight+8;");

		this.cambiarModoverArchivos();

	}

	@Command
	@NotifyChange("*")
	public void cerrarModoverArchivos() throws FileNotFoundException {

		// borrar el contenido
		Component compTool = Path.getComponent(Config.ZKOSS_PATH_FULL_INCLUDE_LATERAL + "/tallerTenidasViewEdit");
		Iframe iframe = (Iframe) compTool.getFellow("idIframeArchivo");

		File ff = new File(Config.DIRECTORIO_BASE_REAL + "" + Config.MANEJADOR_ARCHIVOS_DESCARGA);
		AMedia mediaAux = new AMedia(ff, null, null);
		iframe.setContent(mediaAux);

		this.cambiarModoverArchivos();
	}

	public boolean isAdjuntoVisible() {
		boolean out = false;
		if (this.tenida.getBalaustrePath().trim().length() != 0) {
			out = true;
		}
		return out;
	}

	/**
	 * Full path a las notas de la tenida para el gestor de archivos.
	 * 
	 * @return
	 */
	public String getDirectorioNotas() {
		String path = "";

		if (this.tenida.esNuevo() == false) {
			path = this.getPathNotasByGrado();
			path += this.getDirectorioTenida();
		}

		return path;
	}

	/**
	 * Full path a las planchas de la tenida para el gestor de archivos.
	 * 
	 * @return
	 */
	public String getDirectorioPlanchas() {
		String path = "";

		if (this.tenida.esNuevo() == false) {
			path = this.getPathPlanchasByGrado();
			path += this.getDirectorioTenida();
		}

		return path;
	}

	/**
	 * Retorna el nombre de la carpeta para las tenida.
	 * 
	 * @return
	 */
	public String getDirectorioTenida() {
		return "/tenida_" + this.m.dateToString(this.tenida.getFecha(), "yyyy_MM_dd") + "/";
	}

	/**
	 * Crea todos los directorios relacionados a la tenida.
	 */
	public void crearDirectoriosTenida() {

		String pathNotas = this.getPathNotasByGrado();
		String pathPlanchas = this.getPathPlanchasByGrado();
		String directorioTenida = this.getDirectorioTenida();

		this.crearDirectorio(pathNotas + directorioTenida);
		this.crearDirectorio(pathPlanchas + directorioTenida);

	}

	/**
	 * Crea un nuevo directorio en el servidor.
	 * 
	 * @param directorioNuevo
	 */
	public void crearDirectorio(String directorioNuevo) {

		if (directorioNuevo.trim().length() > 0) {
			File f = new File(directorioNuevo);
			f.mkdir();
			BindUtils.postNotifyChange(null, null, this, "*");
		}

	}

	/**
	 * Utilizado para la visibilidad del directorio correspondiente a las notas
	 * 
	 * @return
	 */
	public boolean isNotasVisible() {
		boolean out = false;

		if (this.tenida.esNuevo() == false) {
			out = true;
		}

		return out;
	}

	/**
	 * Utilizado para la visibilidad del directorio correspondiente a las
	 * planchas
	 * 
	 * @return
	 */
	public boolean isPlanchasVisible() {

		boolean out = false;

		if (this.tenida.esNuevo() == false) {
			out = true;
		}

		return out;

	}

	/**
	 * Retorna el path correspondiente a las notas de acuerdo al grado de la
	 * tenida
	 * 
	 * @return
	 */
	public String getPathNotasByGrado() {

		String path = "";
		if (this.tenida.getGrado().getId().compareTo(this.getUtilDto().getAprendizTipo().getId()) == 00) {

			path = this.getSisProp().getPropiedad(Configuracion.SIS_PRO_PATH_TALLER_SECRETARIO_APRENDIZ_NOTAS);

		} else if (this.tenida.getGrado().getId().compareTo(this.getUtilDto().getCompaneroTipo().getId()) == 00) {

			path = this.getSisProp().getPropiedad(Configuracion.SIS_PRO_PATH_TALLER_SECRETARIO_COMPANERO_NOTAS);

		} else if (this.tenida.getGrado().getId().compareTo(this.getUtilDto().getMaestroTipo().getId()) == 00) {

			path = this.getSisProp().getPropiedad(Configuracion.SIS_PRO_PATH_TALLER_SECRETARIO_MAESTRO_NOTAS);

		}

		return path;
	}

	/**
	 * Retorn el path correspondiente a las planchas de acuerdo al grado de la
	 * tenida
	 * 
	 * @return
	 */
	public String getPathPlanchasByGrado() {

		String path = "";

		if (this.tenida.getGrado().getId().compareTo(this.getUtilDto().getAprendizTipo().getId()) == 00) {

			path = this.getSisProp().getPropiedad(Configuracion.SIS_PRO_PATH_TALLER_SECRETARIO_APRENDIZ_PLANCHAS);

		} else if (this.tenida.getGrado().getId().compareTo(this.getUtilDto().getCompaneroTipo().getId()) == 00) {

			path = this.getSisProp().getPropiedad(Configuracion.SIS_PRO_PATH_TALLER_SECRETARIO_COMPANERO_PLANCHAS);

		} else if (this.tenida.getGrado().getId().compareTo(this.getUtilDto().getMaestroTipo().getId()) == 00) {

			path = this.getSisProp().getPropiedad(Configuracion.SIS_PRO_PATH_TALLER_SECRETARIO_MAESTRO_PLANCHAS);

		}

		return path;
	}

	/**
	 * Retorna el directorio correspondiente a los balaustres de acuerdo al
	 * grado de la tenida.
	 * 
	 * @return
	 */
	public String getPathBalaustreByGrado() {

		String path = "";

		if (this.tenida.getGrado().getId().compareTo(this.getUtilDto().getAprendizTipo().getId()) == 00) {

			path = this.getSisProp().getPropiedad(Configuracion.SIS_PRO_PATH_TALLER_SECRETARIO_APRENDIZ_BALAUSTRES);

		} else if (this.tenida.getGrado().getId().compareTo(this.getUtilDto().getCompaneroTipo().getId()) == 00) {

			path = this.getSisProp().getPropiedad(Configuracion.SIS_PRO_PATH_TALLER_SECRETARIO_COMPANERO_BALAUSTRES);

		} else if (this.tenida.getGrado().getId().compareTo(this.getUtilDto().getMaestroTipo().getId()) == 00) {

			path = this.getSisProp().getPropiedad(Configuracion.SIS_PRO_PATH_TALLER_SECRETARIO_MAESTRO_BALAUSTRES);

		}

		return path;
	}

	public boolean isTenidaNueva() {
		boolean out = false;

		if (this.tenida.esNuevo() == true) {
			out = true;
		}
		return out;

	}
	
	@Command
	public void cargaRapidaAsistencias(){
		this.saltoDePagina(Configuracion.URL_TALLER_TENIDAS_CARGA_RAPIDA, "TENIDA", this.tenida);
	}

	/*************************** Get y Set ********************************/

	public Tenida getTenida() {
		return tenida;
	}

	public void setTenida(Tenida tenida) {
		this.tenida = tenida;
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

	public void setSelectedHermano(Mason selectedHermano) {
		if (selectedHermano != null) {
			this.setFiltroNombreMason(selectedHermano.getNombre());
		}
		this.selectedHermano = selectedHermano;
	}

	public List<Mason> getHermanosFiltro() {
		return hermanosFiltro;
	}

	public void setHermanosFiltro(List<Mason> hermanosFiltro) {
		this.hermanosFiltro = hermanosFiltro;
	}

	public boolean isModoAgregarAsistencia() {
		return modoAgregarAsistencia;
	}

	public void setModoAgregarAsistencia(boolean modoAgregarAsistencia) {
		this.modoAgregarAsistencia = modoAgregarAsistencia;
	}

	public Tipo getSelectedCargo() {
		return selectedCargo;
	}

	public void setSelectedCargo(Tipo selectedCargo) {
		this.selectedCargo = selectedCargo;
	}

	public boolean isAsistenteInvitado() {
		return asistenteInvitado;
	}

	public void setAsistenteInvitado(boolean asistenteInvitado) {
		this.asistenteInvitado = asistenteInvitado;
	}

	public String getNombreAsistente() {
		return nombreAsistente;
	}

	public void setNombreAsistente(String nombreAsistente) {
		this.nombreAsistente = nombreAsistente;
	}

}
