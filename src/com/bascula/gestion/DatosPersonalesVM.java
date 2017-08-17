package com.bascula.gestion;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

import com.coreweb.domain.Usuario;
import com.bascula.Configuracion;
import com.bascula.GenericViewModelApp;
import com.bascula.domain.RegisterDomain;

public class DatosPersonalesVM extends GenericViewModelApp {

	RegisterDomain rr = RegisterDomain.getInstance();
	String msgError = "";

	// Para el cambio de contrasenhas
	private boolean modoCambioPassActi = false;
	private String passNuevo = "";
	private String passNuevo2 = "";
	private Usuario user;

	@Init(superclass = true)
	public void initDatosPersonalesVM() throws Exception {
		user = rr.getUsuario(this.getLoginNombre());
	}

	@AfterCompose(superclass = true)
	public void afterComposeDatosPersonalesVM() {

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

		if (this.verificarPass() == false) {
			this.mensajePopupTemporalWarning(this.msgError);
			return;
		}

		Usuario u = this.user;
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
		} else if (this.passNuevo.compareTo(this.passNuevo2) != 0) {
			msgError += "Las contraseñas no son iguales. \n";
			out = false;
		}

		return out;
	}

	public boolean verificarDatos() {
		boolean out = true;
		return out;
	}

	public boolean isEditable() throws Exception {
		boolean out = false;

		out = this.isEditableSensible();

		// saber si es uno el que está visitando la web
		if (this.user != null && this.user.getLogin().compareTo(this.getLoginNombre()) == 0) {
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

		if (this.isEditable() == true && this.user != null) {
			out = true;
		}
		return out;

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

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

}
