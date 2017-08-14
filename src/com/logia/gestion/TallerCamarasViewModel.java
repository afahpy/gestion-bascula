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

public class TallerCamarasViewModel extends GenericViewModelApp {

	@Init(superclass = true)
	public void initTallerCamarasViewModel() throws Exception {
	}

	@AfterCompose(superclass = true)
	public void afterComposeTallerCamarasViewModel() {
	}

	@Command
	public void cambiarPantalla(@BindingParam("url") String url) {
		this.saltoDePagina(url);
	}
	
}
