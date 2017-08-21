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
	
	public static final String MODO_FORMULARIO_VISTA = "MODO_FORMULARIO_VISTA";
	public static final String MODO_FORMULARIO_EDICION = "MODO_FORMULARIO_EDICION";

	/************************* TIPO TIPOS *****************************/

	public static final String TIPO_TIPO_MOVIMIENTOS = "Tipos de Movimiento";
	public static final String TIPO_TIPO_OBJETOS = "Tipos de objeto";

	
	/*************************** TIPOS ********************************/

	//Tipos movimiento
	public static final String TIPO_MOVIMIENTO_ENTRADA = "Entrada";
	public static final String SIGLA_TIPO_MOVIMIENTO_ENTRADA = "TM-EN";
	public static final String TIPO_MOVIMIENTO_SALIDA = "Salida";
	public static final String SIGLA_TIPO_MOVIMIENTO_SALIDA = "TM-SA";
	
	//Tipos objeto
	public static final String TIPO_OBJETO_ORIGEN_LUGAR = "Origen Lugar";
	public static final String SIGLA_TIPO_OBJETO_ORIGEN_LUGAR = "TO-OR-LU";
	public static final String TIPO_OBJETO_DESTINO_LUGAR = "Destino Lugar";
	public static final String SIGLA_TIPO_OBJETO_DESTINO_LUGAR = "TO-DE-LU";
	public static final String TIPO_OBJETO_CHAPA = "Chapa";
	public static final String SIGLA_TIPO_OBJETO_CHAPA = "TO-CH";
	public static final String TIPO_OBJETO_CHAPA_CARRETA = "Chapa Carreta";
	public static final String SIGLA_TIPO_OBJETO_CHAPA_CARRETA = "TO-CH-CA";
	public static final String TIPO_OBJETO_CHOFER = "Chofer";
	public static final String SIGLA_TIPO_OBJETO_CHOFER = "TO-CHO";
	public static final String TIPO_OBJETO_TRANSPORTADORA = "Transportadora";
	public static final String SIGLA_TIPO_OBJETO_TRANSPORTADORA = "TO-TRANS";
	public static final String TIPO_OBJETO_DESPACHANTE = "Despachante";
	public static final String SIGLA_TIPO_OBJETO_DESPACHANTE = "TO-DESPA";

	

	/*********************** Alias Permisos **************************/

	public static final String ALIAS_PERM_EDITAR_DATOS_PERSONALES = "datos_personales";

	/*********************** Sistema Propiedad ************************/
	public static final String SIS_PRO_EMAIL_DESTINATARIOS_CONTACTO = "EMAIL_DESTINATARIOS_CONTACTO";
	/*************************** Mensajes *****************************/

	public static final String MENSAJE_GENERICO_GUARDAR_OK = "Guardado correctamente!";
	public static final String MENSAJE_GENERICO_GUARDAR_ERROR = "Error al guardar.";

	/******************************* URL ********************************/
	public static final String URL_MOVIMIENTO_EDIT = "/bascula/gestion/movimientos_edit.zul";
}
