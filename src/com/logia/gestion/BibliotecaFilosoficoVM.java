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

public class BibliotecaFilosoficoVM extends GenericViewModelApp {

	@Init(superclass = true)
	public void initBibliotecaFilosoficoVM() throws Exception {
	}

	@AfterCompose(superclass = true)
	public void afterBibliotecaFilosoficoVM() {
	}

	public boolean isEditable() throws Exception {
		boolean out = false;

		if (this.operacionHabilitada(Configuracion.ALIAS_PERM_EDITAR_BIBLIOTECA_FILOSOFICO)) {
			out = true;
		}
		return out;
	}

	public String getDirectorioFilosofico() {
		String path = "";
		path = this.getSisProp().getPropiedad(Configuracion.SIS_PRO_PATH_BIBLIOTECA_FILOSOFICO);
		return path;
	}
}
