package com.logia.gestion;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

import com.coreweb.domain.Usuario;
import com.logia.Configuracion;
import com.logia.GenericViewModelApp;
import com.logia.domain.Mason;
import com.logia.domain.RegisterDomain;

public class DatosPersonalesVM extends GenericViewModelApp {

	RegisterDomain rr = RegisterDomain.getInstance();
	Mason mas;
	String msgError = "";

	// Para el cambio de contrasenhas
	private boolean modoCambioPassActi = false;
	private String passNuevo = "";
	private String passNuevo2 = "";

	@Init(superclass = true)
	public void initDatosPersonalesVM(@ExecutionArgParam("HERMANO") Mason m) throws Exception {
		if (m != null) {
			this.mas = m;
		} else {
			this.mas = rr.getMasonByUserLogin(this.getUs().getLogin());
		}
	}

	@AfterCompose(superclass = true)
	public void afterComposeDatosPersonalesVM() {

	}

	public boolean isEditable() throws Exception {
		boolean out = false;

		out = this.isEditableSensible();

		// saber si es uno el que está visitando la web
		if (this.mas.getUsuarioSistema() != null
				&& this.mas.getUsuarioSistema().getLogin().compareTo(this.getLoginNombre()) == 0) {
			out = true;
		}
		return out;
	}

	public boolean isEditableSensible() throws Exception {
		boolean out = false;

		if (this.operacionHabilitada(Configuracion.ALIAS_PERM_EDITAR_DATOS_PERSONALES)) {
			out = true;
		}
		return out;

	}

	public boolean isDatosUsuarioVisible() throws Exception {
		boolean out = false;

		if (this.isEditable() == true
				&& this.mas.getUsuarioSistema() != null) {
			out = true;
		}
		return out;

	}

	@Command
	@NotifyChange({ "modoCambioPassActi", "passNuevo", "passNuevo2" })
	public void cambiarModoPassActi() {
		this.modoCambioPassActi = !this.modoCambioPassActi;
		this.passNuevo = "";
		this.passNuevo2 = "";
	}

	@Command
	@NotifyChange("*")
	public void cambiarPass() throws Exception {
		
		if(this.verificarPass() == false){
			this.mensajePopupTemporalWarning(this.msgError);
			return;
		}
		
		Usuario u = this.mas.getUsuarioSistema();
		u.setClave(this.m.encriptar(this.passNuevo));
		rr.saveObject(u, this.getUs().getLogin());
		
		this.cambiarModoPassActi();
		
		this.mensajePopupTemporal("Contraseña cambiada correctamente.");
	}

	public boolean verificarPass() {
		msgError = "";
		boolean out = true;
		if (this.passNuevo.trim().length() == 0 || this.passNuevo2.trim().length() == 0) {
			msgError += "Los campos no pueden ser vacios. \n";
			out = false;
		}else if(this.passNuevo.compareTo(this.passNuevo2) != 0){
			msgError += "Las contraseñas no son iguales. \n";
			out = false;
		}

		return out;
	}

	@Command
	public void guardarDatosPersonales() throws Exception {

		if (this.verificarDatos() == false) {
			this.mensajeError(this.msgError);
			return;
		}

		this.mas.setNombre(this.mas.getApellidos() + ", " + this.mas.getNombres());

		rr.saveObject(this.mas, this.getUs().getLogin());
		this.mensajePopupTemporal("Guardado correctamente");
	}

	public boolean verificarDatos() {
		boolean out = true;
		if (this.mas.getApellidos().trim().length() == 0) {
			msgError += "El apellido no puede ser vacío. \n";
			out = false;
		}
		if (this.mas.getNombres().trim().length() == 0) {
			msgError += "El nombre no puede ser vacío. \n";
			out = false;
		}

		return out;
	}

	public Mason getMas() {
		return mas;
	}

	public void setMas(Mason mas) {
		this.mas = mas;
	}

	public boolean isModoCambioPassActi() {
		return modoCambioPassActi;
	}

	public void setModoCambioPassActi(boolean modoCambioPassActi) {
		this.modoCambioPassActi = modoCambioPassActi;
	}

	public String getPassNuevo() {
		return passNuevo;
	}

	public void setPassNuevo(String passNuevo) {
		this.passNuevo = passNuevo;
	}

	public String getPassNuevo2() {
		return passNuevo2;
	}

	public void setPassNuevo2(String passNuevo2) {
		this.passNuevo2 = passNuevo2;
	}

}
