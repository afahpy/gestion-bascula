package com.bascula.gestion;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;

import com.bascula.GenericViewModelApp;

public class InicioViewModel extends GenericViewModelApp {

	@Init(superclass = true)
	public void init() throws Exception {

	}

	@AfterCompose(superclass = true)
	public void afterCompose() {
	}

	@Command
	public void cambiarPantalla(@BindingParam("url") String url){
		System.out.println("Salto de pagina a: " + url);
		this.saltoDePagina(url);
		this.mensajePopupTemporalWarning("Este es un texto de prueba para determinar si el contenido"
				+ "anexado a este texto se puede visualizar desde un telefono...");
	}

	
}
