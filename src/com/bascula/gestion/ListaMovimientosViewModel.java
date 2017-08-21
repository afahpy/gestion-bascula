package com.bascula.gestion;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;

import com.bascula.GenericViewModelApp;

public class ListaMovimientosViewModel extends GenericViewModelApp {

	@Init(superclass = true)
	public void initMovimientosViewModel() throws Exception {

	}

	@AfterCompose(superclass = true)
	public void afterComposeMovimientosViewModel() {
	}

	@Command
	public void cambiarPantalla(@BindingParam("url") String url){
		this.saltoDePagina(url);
	}

	
}
