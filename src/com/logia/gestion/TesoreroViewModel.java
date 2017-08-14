package com.logia.gestion;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;

import com.logia.Configuracion;
import com.logia.GenericViewModelApp;

public class TesoreroViewModel extends GenericViewModelApp {

	@Init(superclass = true)
	public void init() throws Exception {

	}

	@AfterCompose(superclass = true)
	public void afterCompose() {
	}

	public String getMostrarPermisoTesorero() throws Exception {
		String out = "";
		boolean tienePermisoEditarTesorero = false;
		tienePermisoEditarTesorero = this.operacionHabilitada(Configuracion.ALIAS_PERM_EDITAR_TESORERO);
		
		if(tienePermisoEditarTesorero){
			out = "El usuario " + this.getUs().getNombre() + " tiene permisos para editar" ;
		}else{
			out = "El usuario " + this.getUs().getNombre() + " no tiene permisos para editar" ;
		}
		
		return out;
	}

}
