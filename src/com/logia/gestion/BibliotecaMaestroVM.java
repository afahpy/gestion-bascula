package com.logia.gestion;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import com.coreweb.util.MyPair;
import com.logia.Configuracion;
import com.logia.GenericViewModelApp;

public class BibliotecaMaestroVM extends GenericViewModelApp {

	@Init(superclass = true)
	public void initBibliotecaMaestroVM() throws Exception {
	}

	@AfterCompose(superclass = true)
	public void afterComposeBibliotecaMaestroVM() {
	}

	public boolean isEditable() throws Exception {
		boolean out = false;

		if (this.operacionHabilitada(Configuracion.ALIAS_PERM_EDITAR_BIBLIOTECA_MAESTRO)) {
			out = true;
		}
		return out;
	}

	public String getDirectorioMaestro() {
		String path = "";
		path = this.getSisProp().getPropiedad(Configuracion.SIS_PRO_PATH_BIBLIOTECA_MAESTRO);
		return path;
	}
}
