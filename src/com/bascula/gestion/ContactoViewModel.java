package com.bascula.gestion;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;

import com.bascula.Configuracion;
import com.bascula.GenericViewModelApp;
import com.coreweb.SistemaPropiedad;
import com.coreweb.control.Control;
import com.coreweb.extras.email.EnviarCorreo;
import com.bascula.gestion.ContactoDTO;

public class ContactoViewModel extends GenericViewModelApp {

	private ContactoDTO contacto;
	private static final String ASUNTO_CONTACTO = "Solicitud de Contacto";
	private static final String ERROR_ENVIAR_CONTACTO = "Ocurrió un error al intentar la solicitud de contacto";
	private static final String URL_CONTACTO_OK = "/bascula/gestion/contacto_ok.zul";
	private String mensajeError = "";

	@Init(superclass = true)
	public void initContactoViewModel() throws Exception {
		this.contacto = new ContactoDTO();
	}

	@AfterCompose(superclass = true)
	public void afterComposeContactoViewModel() {
	}

	public ContactoDTO getContacto() {
		return contacto;
	}

	public void setContacto(ContactoDTO contacto) {
		this.contacto = contacto;
	}

	@Command
	public void enviarContacto() {
		
		if(this.validarCampos() == false){
			this.mensajeError(this.mensajeError);
			return;
		}

		EnviarCorreo enviarCorreo = new EnviarCorreo();
		String[] destinatarios = { this.contacto.getEmailContacto() };
		String[] destinatariosCopiaOculta = this.getEmailsDestinatariosContacto();
		
		try {
			enviarCorreo.sendMessage(destinatarios, destinatariosCopiaOculta, ASUNTO_CONTACTO,
					this.getMensajeFormateado());
			this.saltoDePagina(URL_CONTACTO_OK);
		} catch (Exception e) {
			this.mensajePopupTemporalWarning(ERROR_ENVIAR_CONTACTO);
			e.printStackTrace();
		}
	}

	private String[] getEmailsDestinatariosContacto() {

		SistemaPropiedad sistemaPropiedad = this.getSisProp();
		String mailsPropiedad = sistemaPropiedad.getPropiedad(Configuracion.SIS_PRO_EMAIL_DESTINATARIOS_CONTACTO);
		String[] mails = mailsPropiedad.split(",");
		for (int i = 0; i < mails.length; i++) {
			mails[i] = mails[i].trim();
		}
		return mails;
	}

	private String getMensajeFormateado() {
		String mensaje = "";
		mensaje += "Se ha recibido una solucitud de contacto de parte de:";
		mensaje += "\n" + this.contacto.getNombre();
		mensaje += "\n\nSu dirección de correo es:";
		mensaje += "\n" + this.contacto.getEmailContacto();
		mensaje += "\n\nMensaje:";
		mensaje += "\n" + this.contacto.getMensaje();
		return mensaje;
	}

	private boolean validarCampos(){
		this.mensajeError = "No se puede realizar la operacion debido a:";
		boolean out = true;
		
		if(this.contacto.getNombre().trim().length() == 0){
			out = false;
			this.mensajeError += "\n- El nombre no puede estar vacio.";
					
		}
		
		if(this.contacto.getEmailContacto().trim().length() == 0){
			out = false;
			this.mensajeError += "\n- El correo no puede estar vacio.";
			
		}else if(this.m.checkEmail(this.contacto.getEmailContacto()) == false){
			
			out = false;
			this.mensajeError += "\n- El formato de correo es inválido.";
		}
		
		if(this.contacto.getMensaje().trim().length() == 0){
			out = false;
			this.mensajeError += "\n- Debe escribir un mensaje.";
					
		}
		
		return out;
		
	}

}
