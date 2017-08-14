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
import com.coreweb.SistemaPropiedad;
import com.coreweb.domain.Tipo;
import com.coreweb.extras.email.EnviarCorreo;
import com.coreweb.util.MyPair;
import com.logia.Configuracion;
import com.logia.GenericViewModelApp;

public class TallerSecretarioNotificacionVM extends GenericViewModelApp {

	private String asuntoNotificacion = "";
	private String textoNotificacion = "";
	private static final String ERROR_ENVIAR_CONTACTO = "Ocurrió un error al enviar la notificación";
	private String mensajeError = "";

	@Init(superclass = true)
	public void initTallerSecretarioNotificacionVM() throws Exception {
	}

	@AfterCompose(superclass = true)
	public void afterComposeTallerSecretarioNotificacionVM() {
	}

	public boolean isEditable() throws Exception {
		boolean out = false;

		if (this.operacionHabilitada(Configuracion.ALIAS_PERM_EDITAR_TALLER_SECRETARIO_NOTIFICACIONES)) {
			out = true;
		}
		return out;
	}

	public String getTextoNotificacion() {
		return textoNotificacion;
	}

	public void setTextoNotificacion(String textoNotificacion) {
		this.textoNotificacion = textoNotificacion;
	}

	@Command
	public void enviarNotificacion() {

		if (this.validarCampos() == false) {
			this.mensajeError(this.mensajeError);
			return;
		}

		EnviarCorreo enviarCorreo = new EnviarCorreo();
		String[] destinatarios = { this.getSisProp().getPropiedad("EMAIL_FROM") };
		String[] destinatariosCopiaOculta = this.getEmailsDestinatarios();

		try {
			enviarCorreo.sendMessage(destinatarios, destinatariosCopiaOculta, this.asuntoNotificacion,
					this.textoNotificacion);
			this.mensajePopupTemporal("Se ha enviado correctamente la notificación");
			this.saltoDePagina(Configuracion.URL_TALLER_SECRETARIA);
		} catch (Exception e) {
			this.mensajePopupTemporalWarning(ERROR_ENVIAR_CONTACTO);
			e.printStackTrace();
		}

	}

	private String[] getEmailsDestinatarios() {

		SistemaPropiedad sistemaPropiedad = this.getSisProp();
		String mailsPropiedad = sistemaPropiedad.getPropiedad(Configuracion.SIS_PRO_EMAIL_DESTINATARIOS_NOTIFICACION);
		String[] mails = mailsPropiedad.split(",");
		for (int i = 0; i < mails.length; i++) {
			mails[i] = mails[i].trim();
		}
		return mails;
	}

	private boolean validarCampos() {
		this.mensajeError = "No se puede realizar la operacion debido a:";
		boolean out = true;

		if (this.asuntoNotificacion.trim().length() == 0) {
			out = false;
			this.mensajeError += "\n- El asunto no puede estar vacio.";

		}

		if (this.textoNotificacion.trim().length() == 0) {
			out = false;
			this.mensajeError += "\n- El mensaje no puede estar vacio.";

		}

		return out;

	}

	public String getAsuntoNotificacion() {
		return asuntoNotificacion;
	}

	public void setAsuntoNotificacion(String asuntoNotificacion) {
		this.asuntoNotificacion = asuntoNotificacion;
	}

}
