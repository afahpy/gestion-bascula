package com.logia.gestion;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Iframe;

import com.coreweb.Config;
import com.coreweb.domain.Tipo;
import com.coreweb.util.MyArray;
import com.coreweb.util.MyPair;
import com.logia.Configuracion;
import com.logia.GenericViewModelApp;
import com.logia.domain.RegisterDomain;
import com.logia.domain.Tenida;

public class TallerTenidasViewModel extends GenericViewModelApp {

	// Temporales para filtro
	private Tipo gradoHermanoFiltro = new Tipo();
	private Tipo periodoFiltro = new Tipo();
	private List<Tipo> filtrosGradoHermano ;
	
	boolean modoVerArchivo = false;

	@Init(superclass = true)
	public void initTallerTenidasViewModel() throws Exception {

		this.gradoHermanoFiltro = this.getUtilDto().getAprendizTipo();
		this.periodoFiltro = this.getPeriodoCorrienteTipo();
		
		// la lista de grados se setea una sola vez nnomas
		if (this.isUserAprendiz() == true) {
			this.filtrosGradoHermano = this.getUtilDto().getGradosMasonFiltroAprendiz();
		} else if (this.isUserCompanero() == true) {
			this.filtrosGradoHermano = this.getUtilDto().getGradosMasonFiltroCompanero();
		} else if (this.isUserMaestro() == true) {
			this.filtrosGradoHermano = this.getUtilDto().getGradosMasonFiltroMaestro();
		}

	}

	@AfterCompose(superclass = true)
	public void afterComposeTallerTenidasViewModel() {
	}

	@Command
	public void cambiarPantalla(@BindingParam("url") String url) {
		this.saltoDePagina(url);
	}

	public boolean isModoVerArchivo() {
		return modoVerArchivo;
	}

	public void setModoVerArchivo(boolean modoVerArchivo) {
		this.modoVerArchivo = modoVerArchivo;
	}

	public boolean isEditable() throws Exception {
		boolean out = false;

		if (this.operacionHabilitada(Configuracion.ALIAS_PERM_EDITAR_TENIDAS)) {
			out = true;
		}
		return out;
	}

	@Command
	@NotifyChange("tenidas")
	public void refrescarTenidas() throws Exception{
		// solo llama al que refresca la lista de tenidas (lo hace con el notifychange).
	}
	
	public List<Tenida> getTenidas() throws Exception {
		List<Tenida> lista = new ArrayList<Tenida>();
		if ((this.gradoHermanoFiltro != null)&&(this.periodoFiltro != null)){
			lista = rr.getTenidas(this.gradoHermanoFiltro, this.periodoFiltro, this.getGradoUsuarioCorriente());
		}
		return lista;
	}

	public Tipo getGradoHermanoFiltro() {
		System.out.println("-----gradoHermanoFiltro:"+gradoHermanoFiltro);
		return gradoHermanoFiltro;
	}

	public void setGradoHermanoFiltro(Tipo gradoHermanoFiltro) {
		
		if (gradoHermanoFiltro == null){
			int x = 4 / 0;
		}
		
		System.out.println("-----1 setGradoHermanoFiltro:"+gradoHermanoFiltro);
		this.gradoHermanoFiltro = gradoHermanoFiltro;
		System.out.println("-----2 setGradoHermanoFiltro:"+gradoHermanoFiltro);

	}

	public Tipo getPeriodoFiltro() {
		return periodoFiltro;
	}

	public void setPeriodoFiltro(Tipo periodoFiltro) {
		this.periodoFiltro = periodoFiltro;
	}

	public List<Tipo> getFiltrosGradoHermano() {
		
		return this.filtrosGradoHermano;
/*		
		List<Tipo> filtros = new ArrayList<Tipo>();

		if (this.isUserAprendiz() == true) {
			filtros = this.getUtilDto().getGradosMasonFiltroAprendiz();
		} else if (this.isUserCompanero() == true) {
			filtros = this.getUtilDto().getGradosMasonFiltroCompanero();
		} else if (this.isUserMaestro() == true) {
			filtros = this.getUtilDto().getGradosMasonFiltroMaestro();
		}

		System.out.println("-------------filtros grado:("+filtros.size()+") "+filtros);
		return filtros;
*/
	}

	@Command
	public void seleccionarTenida(@BindingParam("tenidaSelected") Tenida tenidaSelected) {
		Hashtable<String, Object> params = new Hashtable<String, Object>();
		params.put("TENIDA_SELECTED", tenidaSelected);
		params.put("IS_NEW_TENIDA", false);
		this.saltoDePagina(Configuracion.URL_TENIDA_VIEW_EDIT, params);
	}

	@Command
	public void agregarTenida() {
		Hashtable<String, Object> params = new Hashtable<String, Object>();
		params.put("TENIDA_SELECTED", new Tenida());
		params.put("IS_NEW_TENIDA", true);
		this.saltoDePagina(Configuracion.URL_TENIDA_VIEW_EDIT, params);
	}

	@Command
	@NotifyChange("*")
	public void clickFile(@BindingParam("tenidaArchivo") Tenida tenidaArchivo) throws Exception {

		File file = new File(tenidaArchivo.getBalaustrePath());

		AMedia media = new AMedia(file, null, null);

		Component compTool = Path.getComponent(Config.ZKOSS_PATH_FULL_INCLUDE_LATERAL + "/tallerTenidas");
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
	public void clickFileNone(){
		this.mensajePopupTemporalWarning("No existen balaustres asociados a la tenida.");
	}

	@Command
	@NotifyChange("*")
	public void cerrarModoverArchivos() throws FileNotFoundException {

		// borrar el contenido
		Component compTool = Path.getComponent(Config.ZKOSS_PATH_FULL_INCLUDE_LATERAL + "/tallerTenidas");
		Iframe iframe = (Iframe) compTool.getFellow("idIframeArchivo");

		File ff = new File(Config.DIRECTORIO_BASE_REAL + "" + Config.MANEJADOR_ARCHIVOS_DESCARGA);
		AMedia mediaAux = new AMedia(ff, null, null);
		iframe.setContent(mediaAux);

		this.cambiarModoverArchivos();
	}

	public void cambiarModoverArchivos() {
		this.modoVerArchivo = !this.modoVerArchivo;
	}

}
