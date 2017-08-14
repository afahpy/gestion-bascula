package com.logia.gestion;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Include;

import com.coreweb.Config;
import com.coreweb.domain.Tipo;
import com.coreweb.util.MyPair;
import com.logia.Configuracion;
import com.logia.GenericViewModelApp;

public class TallerSecretarioViewModel extends GenericViewModelApp {

	private Tipo gradoHermanoFiltro = new Tipo();

	@Init(superclass = true)
	public void initTallerSecretarioViewModel() throws Exception {

		this.gradoHermanoFiltro = this.getUtilDto().getAprendizTipo();

	}

	@AfterCompose(superclass = true)
	public void afterComposeTallerSecretarioViewModel() {
	}

	@Command
	public void cambiarPantalla(@BindingParam("url") String url) {
		this.saltoDePagina(url);
	}


	public boolean isEditable() throws Exception {
		boolean out = false;

		if (this.operacionHabilitada(Configuracion.ALIAS_PERM_EDITAR_TALLER_SECRETARIO_DIRECTORIO)) {
			out = true;
		}
		return out;
	}

	public boolean isEditableNotificar() throws Exception {

		boolean out = false;

		if (this.operacionHabilitada(Configuracion.ALIAS_PERM_EDITAR_TALLER_SECRETARIO_NOTIFICACIONES)) {
			out = true;
		}
		return out;
	}

	@Command
	@NotifyChange("directorioSecretario")
	public void updatePathSecretario() {

		Component main = Path.getComponent(Config.ZKOSS_PATH_FULL_INCLUDE_LATERAL + "/tallerSecretario");
		Include include = (Include) main.getFellow("includeArchivosSecretario");
		include.setSrc("");
		include.setSrc("/core/componente/manejadorArchivos.zul");

	}

	public String getDirectorioSecretario() {
		String path = "";
		path = this.getPathSecretarioByGrado();
		System.out.println("El nuevo path deberia ser: " + path);
		return path;
	}

	public List<Tipo> getFiltrosGradoHermano() {
		List<Tipo> filtros = new ArrayList<Tipo>();

		if (this.isUserAprendiz() == true) {
			filtros = this.getUtilDto().getGradosMasonFiltroAprendizSinTodo();
		} else if (this.isUserCompanero() == true) {
			filtros = this.getUtilDto().getGradosMasonFiltroCompaneroSinTodo();
		} else if (this.isUserMaestro() == true) {
			filtros = this.getUtilDto().getGradosMasonFiltroMaestroSinTodo();
		}
		return filtros;
	}

	public String getPathSecretarioByGrado() {

		String path = "";

		if (this.getGradoHermanoFiltro().getId().compareTo(this.getUtilDto().getAprendizTipo().getId()) == 00) {

			path = this.getSisProp().getPropiedad(Configuracion.SIS_PRO_PATH_TALLER_SECRETARIO_APRENDIZ);

		} else if (this.getGradoHermanoFiltro().getId().compareTo(this.getUtilDto().getCompaneroTipo().getId()) == 00) {

			path = this.getSisProp().getPropiedad(Configuracion.SIS_PRO_PATH_TALLER_SECRETARIO_COMPANERO);

		} else if (this.getGradoHermanoFiltro().getId().compareTo(this.getUtilDto().getMaestroTipo().getId()) == 00) {

			path = this.getSisProp().getPropiedad(Configuracion.SIS_PRO_PATH_TALLER_SECRETARIO_MAESTRO);

		}

		return path;
	}

	/************************ Get y Set *******************************/

	public Tipo getGradoHermanoFiltro() {
		return gradoHermanoFiltro;
	}

	public void setGradoHermanoFiltro(Tipo gradoHermanoFiltro) {
		this.gradoHermanoFiltro = gradoHermanoFiltro;
	}

}
