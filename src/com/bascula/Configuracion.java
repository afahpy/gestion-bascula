package com.bascula;

import org.zkoss.zk.ui.Sessions;

import com.coreweb.Config;

public class Configuracion extends Config {

	public static String EMPRESA = "UNISAL";
	public static String URL_INICIO_VALOR_DEFAULT = "/core/inicio/login.zul";
	public static String URL_INICIO_VALOR_AFTER_LOGIN = "/bascula/gestion/inicio_after_login.zul";

	/*************************** SESION *******************************/

	public static String empresa = "Unisal S.A.";
	
	
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
	
	public static final String TIPO_OBJETO_PRODUCTO = "Producto";
	public static final String SIGLA_TIPO_OBJETO_PRODUCTO = "TO-PRODUC";

	

	/*********************** Alias Permisos **************************/

	public static final String ALIAS_PERM_EDITAR_DATOS_PERSONALES = "datos_personales";
	public static final String ALIAS_PERM_EDITAR_CAMPO_MOV_TIPOMOVIMIENTO = "editar_campo_movimiento_tipomovimiento";
	public static final String ALIAS_PERM_EDITAR_CAMPO_MOV_FECHALLEGADA = "editar_campo_movimiento_fechallegada";
	public static final String ALIAS_PERM_EDITAR_CAMPO_MOV_FECHASALIDA= "editar_campo_movimiento_fechasalida";
	public static final String ALIAS_PERM_EDITAR_CAMPO_MOV_BRUTO = "editar_campo_movimiento_bruto";
	public static final String ALIAS_PERM_EDITAR_CAMPO_MOV_TARA = "editar_campo_movimiento_tara";
	public static final String ALIAS_PERM_EDITAR_CAMPO_MOV_NETO = "editar_campo_movimiento_neto";
	public static final String ALIAS_PERM_EDITAR_CAMPO_MOV_ORIGEN = "editar_campo_movimiento_origen";
	public static final String ALIAS_PERM_EDITAR_CAMPO_MOV_DIFERENCIA = "editar_campo_movimiento_diferencia";
	public static final String ALIAS_PERM_EDITAR_CAMPO_MOV_ORIGENLUGAR = "editar_campo_movimiento_origenlugar";
	public static final String ALIAS_PERM_EDITAR_CAMPO_MOV_DESTINOLUGAR = "editar_campo_movimiento_destinolugar";
	public static final String ALIAS_PERM_EDITAR_CAMPO_MOV_REMITO = "editar_campo_movimiento_remito";
	public static final String ALIAS_PERM_EDITAR_CAMPO_MOV_REMISION = "editar_campo_movimiento_remision";
	public static final String ALIAS_PERM_EDITAR_CAMPO_MOV_CHAPA = "editar_campo_movimiento_chapa";
	public static final String ALIAS_PERM_EDITAR_CAMPO_MOV_CHAPACARRETA = "editar_campo_movimiento_capacarreta";
	public static final String ALIAS_PERM_EDITAR_CAMPO_MOV_CHOFER = "editar_campo_movimiento_chofer";
	public static final String ALIAS_PERM_EDITAR_CAMPO_MOV_TRANSPORTADORA = "editar_campo_movimiento_transportadora";
	public static final String ALIAS_PERM_EDITAR_CAMPO_MOV_DESPACHO = "editar_campo_movimiento_despacho";
	public static final String ALIAS_PERM_EDITAR_CAMPO_MOV_DESPACHANTE = "editar_campo_movimiento_despachante";
	
	public static final String ALIAS_PERM_EDITAR_MOV = "editar_movimiento";
	public static final String ALIAS_PERM_CREAR_MOV = "crear_movimiento";
	public static final String ALIAS_PERM_ELIMINAR_MOV = "eliminar_movimiento";
	
	
	/*********************** Sistema Propiedad ************************/
	public static final String SIS_PRO_EMAIL_DESTINATARIOS_CONTACTO = "EMAIL_DESTINATARIOS_CONTACTO";
	/*************************** Mensajes *****************************/

	public static final String MENSAJE_GENERICO_GUARDAR_OK = "Guardado correctamente!";
	public static final String MENSAJE_GENERICO_GUARDAR_ERROR = "Error al guardar.";

	/******************************* URL ********************************/
	public static final String URL_MOVIMIENTO_EDIT = "/bascula/gestion/movimientos_edit.zul";
}
