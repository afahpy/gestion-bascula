package com.bascula;

import org.zkoss.zk.ui.Sessions;

import com.coreweb.Config;

public class Configuracion extends Config {

	public static String EMPRESA = "UNISAL";
	public static String URL_INICIO_VALOR_DEFAULT = "/core/inicio/login.zul";
	public static String URL_INICIO_VALOR_AFTER_LOGIN = "/bascula/gestion/inicio_after_login.zul";

	/*************************** SESION *******************************/

	public static String ACCESO = "AccesoDTO";
	public static String PATH_SESSION = ".";

	/************************* TIPO TIPOS *****************************/

	/*************************** TIPOS ********************************/

	/*********************** Alias Permisos **************************/
	
	public static final String ALIAS_PERM_EDITAR_DATOS_PERSONALES = "datos_personales";

	/*********************** Sistema Propiedad ************************/
	public static final String SIS_PRO_EMAIL_DESTINATARIOS_CONTACTO = "EMAIL_DESTINATARIOS_CONTACTO";
	/*************************** Mensajes *****************************/

	public static final String MENSAJE_GENERICO_GUARDAR_OK = "Guardado correctamente!";
	public static final String MENSAJE_GENERICO_GUARDAR_ERROR = "Error al guardar.";

	/******************************* URL ********************************/

}
