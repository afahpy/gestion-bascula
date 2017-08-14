package com.logia;

import org.zkoss.zk.ui.Sessions;

import com.coreweb.Config;

public class Configuracion extends Config {

	public static String EMPRESA = "LOGIA";
	public static String URL_INICIO_VALOR_DEFAULT = "/core/inicio/login.zul";
	public static String URL_INICIO_VALOR_AFTER_LOGIN = "/logia/gestion/inicio_after_login.zul";

	/*********************************** SESION ******************************************/

	public static String ACCESO = "AccesoDTO";
	public static String SESSION_GRADO_MASON = "GradoMason";

	/************************************************************************************/
	public static String PATH_SESSION = ".";

	/***********************************
	 * El orden de la lista de HH
	 *************************************/
	public static String ORDEN_MAS_NOMBRE = "m.nombre";	
	public static String ORDEN_MAS_CARGO = "m.cargo.orden asc";	
	public static String ORDEN_MAS_GRADO = "m.grado.orden asc";	
	public static String ORDEN_MAS_ESTADO = "m.estado.orden asc";	
	
	
	
	/***********************************
	 * TIPO TIPOS
	 *************************************/

	public static final String ID_GRADO_MASON = "Gr∴";
	public static final String ID_TIPO_TENIDA = "Tipo Tenida";
	public static final String ID_TIPO_CARGO = "Cargo";
	public static final String ID_TIPO_PERIODO = "Periodo";
	public static final String ID_TIPO_MOVIMIENTO = "Tipo Movimiento";
	public static final String ID_TIPO_MOVIMIENTO_HERMANO = "Tipo Movimiento Hermano";
	public static final String ID_MES = "Mes";
	public static final String ID_TIPO_ESTADO_H = "Estado H∴";

	/************************************* TIPOS ****************************************/

	public static final String TIPO_TODO = "Todos";
	public static final String SIGLA_TIPO_TODO = "TIP-TOD";

	// Grado Hermano
	public static final String TIPO_GRADO_MASON_APRENDIZ = "Apr∴M∴";
	public static final String TIPO_GRADO_MASON_COMPANERO = "Comp∴M∴";
	public static final String TIPO_GRADO_MASON_MAESTRO = "M∴M∴";
	public static final String SIGLA_TIPO_GRADO_MASON_APRENDIZ = "GR-MA-AP";
	public static final String SIGLA_TIPO_GRADO_MASON_COMPANERO = "GR-MA-CO";
	public static final String SIGLA_TIPO_GRADO_MASON_MAESTRO = "GR-MA-MA";

	// Tipos de tenidas
	public static final String TIPO_TENIDA_SIMPLE = "Ten∴Ord∴";
	public static final String TIPO_TENIDA_ESPECIAL = "Ten∴Esp∴";
	public static final String TIPO_TENIDA_MAGNA = "Ten∴Mag∴";
	public static final String SIGLA_TIPO_TENIDA_SIMPLE = "TP-TE-SI";
	public static final String SIGLA_TIPO_TENIDA_ESPECIAL = "TP-TE-ES";
	public static final String SIGLA_TIPO_TENIDA_MAGNA = "TP-TE-MA";

	// Cargos
	public static final String TIPO_CARGO_VENERABLE_MAESTRO = "V∴M∴";
	public static final String TIPO_CARGO_PAST_VENERABLE_MAESTRO = "Past V∴M∴";
	public static final String TIPO_CARGO_PRIMER_VIGILANTE = "1Vig∴";
	public static final String TIPO_CARGO_SEGUNDO_VIGILANTE = "2Vig∴";
	public static final String TIPO_CARGO_ORADOR = "Orad∴";
	public static final String TIPO_CARGO_SECRETARIO = "Secr∴";
	public static final String TIPO_CARGO_TESORERO = "Tes∴";
	public static final String TIPO_CARGO_HOSPITALARIO = "Hosp∴";
	public static final String TIPO_CARGO_MAESTRO_DE_CEREMONIA = "M∴Cer∴";
	public static final String TIPO_CARGO_PRIMER_DIACONO = "P∴Diac∴";
	public static final String TIPO_CARGO_SEGUNDO_DIACONO = "S∴Diac∴";
	public static final String TIPO_CARGO_GUARDA_TEMPLO_INTERNO = "G∴T∴I∴";
	public static final String TIPO_CARGO_GUARDA_TEMPLO_EXTERNO = "G∴T∴E∴";
	public static final String TIPO_CARGO_MAESTRO_EXPERTO = "M∴Exp∴";
	public static final String TIPO_CARGO_MAESTRO_ARMONIA = "M∴Ar∴";
	public static final String TIPO_CARGO_MAESTRO_BANQUETERO = "M.Banq∴";
	public static final String TIPO_CARGO_PORTA_ESTANDARTE = "Porta-est";
	public static final String TIPO_CARGO_SIN_CARGO = "-Sin cargo-";

	public static final String SIGLA_TIPO_CARGO_VENERABLE_MAESTRO = "TC-VE-MA";
	public static final String SIGLA_TIPO_CARGO_PAST_VENERABLE_MAESTRO = "TC-PA-VE-MA";
	public static final String SIGLA_TIPO_CARGO_PRIMER_VIGILANTE = "TC-PR-VI";
	public static final String SIGLA_TIPO_CARGO_SEGUNDO_VIGILANTE = "TC-SE-VI";
	public static final String SIGLA_TIPO_CARGO_ORADOR = "TC-ORA";
	public static final String SIGLA_TIPO_CARGO_SECRETARIO = "TC-SECRE";
	public static final String SIGLA_TIPO_CARGO_TESORERO = "TC-TESOR";
	public static final String SIGLA_TIPO_CARGO_HOSPITALARIO = "TC-HOSPI";
	public static final String SIGLA_TIPO_CARGO_MAESTRO_DE_CEREMONIA = "TC-MA-CE";
	public static final String SIGLA_TIPO_CARGO_PRIMER_DIACONO = "TC-PR-DI";
	public static final String SIGLA_TIPO_CARGO_SEGUNDO_DIACONO = "TC-SE-DI";
	public static final String SIGLA_TIPO_CARGO_GUARDA_TEMPLO_INTERNO = "TC-GU-TE-INT";
	public static final String SIGLA_TIPO_CARGO_GUARDA_TEMPLO_EXTERNO = "TC-GU-TE-EXT";
	public static final String SIGLA_TIPO_CARGO_MAESTRO_EXPERTO = "TC-MA-EXP";
	public static final String SIGLA_TIPO_CARGO_MAESTRO_ARMONIA = "TC-MA-ARM";
	public static final String SIGLA_TIPO_CARGO_MAESTRO_BANQUETERO = "TC-MA-BANQ";
	public static final String SIGLA_TIPO_CARGO_PORTA_ESTANDARTE = "TC-POR-EST";
	public static final String SIGLA_TIPO_CARGO_SIN_CARGO = "TC-SIN-CAR";

	// Periodos
	public static final String TIPO_PERIODO_2016 = "2016";
	public static final String TIPO_PERIODO_2017 = "2017";
	public static final String TIPO_PERIODO_2018 = "2018";
	public static final String SIGLA_TIPO_PERIODO_2016 = "PE-2016";
	public static final String SIGLA_TIPO_PERIODO_2017 = "PE-2017";
	public static final String SIGLA_TIPO_PERIODO_2018 = "PE-2018";

	// Tipo Movimiento
	public static final String TIPO_MOVIMIENTO_INGRESO = "Ingreso";
	public static final String TIPO_MOVIMIENTO_EGRESO = "Egreso";
	public static final String SIGLA_TIPO_MOVIMIENTO_INGRESO = "TM-ING";
	public static final String SIGLA_TIPO_MOVIMIENTO_EGRESO = "TM-EGR";

	// Tipo Movimiento Hermano
	public static final String TIPO_MOVIMIENTO_HH_INGRESO = "Ingreso";
	public static final String TIPO_MOVIMIENTO_HH_EGRESO = "Egreso";
	public static final String TIPO_MOVIMIENTO_HH_PAGO_CAPITACION = "Pago Cap∴";
	public static final String SIGLA_TIPO_MOVIMIENTO_HH_INGRESO = "TM-HH-ING";
	public static final String SIGLA_TIPO_MOVIMIENTO_HH_EGRESO = "TM-HH-EGR";
	public static final String SIGLA_TIPO_MOVIMIENTO_HH_PAGO_CAPITACION = "TM-HH-PC";

	// Tipo Estado Hermnao
	public static final String TIPO_H_ESTADO_ACTIVO = "Activo";
	public static final String TIPO_H_ESTADO_COTIZANTE = "Cotizante";
	public static final String TIPO_H_ESTADO_LIBRE = "Libre";
	public static final String TIPO_H_ESTADO_SUSPENDIDO = "Suspendido";
	public static final String TIPO_H_ESTADO_EXPULSADO = "Expulsado";

	/*********************************
	 * Alias Permisos
	 ************************************/

	public static final String ALIAS_PERM_EDITAR_TENIDAS = "taller_tenidas";
	public static final String ALIAS_PERM_EDITAR_TESORERO = "EDITAR_TESORERO";
	public static final String ALIAS_PERM_EDITAR_TALLER_CAMARA_APRENDIZ = "camara_aprendiz";
	public static final String ALIAS_PERM_EDITAR_TALLER_CAMARA_COMPANERO = "camara_companiero";
	public static final String ALIAS_PERM_EDITAR_TALLER_CAMARA_MAESTRO = "camara_maestro";

	public static final String ALIAS_PERM_EDITAR_BIBLIOTECA_APRENDIZ = "biblioteca_aprendiz";
	public static final String ALIAS_PERM_EDITAR_BIBLIOTECA_COMPANERO = "biblioteca_companiero";
	public static final String ALIAS_PERM_EDITAR_BIBLIOTECA_MAESTRO = "biblioteca_maestro";
	public static final String ALIAS_PERM_EDITAR_BIBLIOTECA_FILOSOFICO = "biblioteca_filosofico";

	public static final String ALIAS_PERM_EDITAR_TALLER_SECRETARIO_NOTIFICACIONES = "secretario_notificaciones";
	public static final String ALIAS_PERM_EDITAR_TALLER_SECRETARIO_DIRECTORIO = "secretario_directorio";

	public static final String ALIAS_PERM_EDITAR_TALLER_HOSPITALARIO = "taller_hospitalario";
	public static final String ALIAS_PERM_EDITAR_TALLER_TESORERIA = "taller_tesoreria";

	public static final String ALIAS_PERM_EDITAR_DATOS_PERSONALES = "datos_personales";

	/*********************************
	 * Alias Permisos
	 ************************************/

	public static final String SIS_PRO_PATH_TALLER_CAMARA_APRENDIZ = "PATH_TALLER_CAMARA_APRENDIZ";
	public static final String SIS_PRO_PATH_TALLER_CAMARA_COMPANERO = "PATH_TALLER_CAMARA_COMPANERO";
	public static final String SIS_PRO_PATH_TALLER_CAMARA_MAESTRO = "PATH_TALLER_CAMARA_MAESTRO";

	public static final String SIS_PRO_PATH_TALLER_SECRETARIO_APRENDIZ = "PATH_TALLER_SECRETARIO_APRENDIZ";
	public static final String SIS_PRO_PATH_TALLER_SECRETARIO_COMPANERO = "PATH_TALLER_SECRETARIO_COMPANERO";
	public static final String SIS_PRO_PATH_TALLER_SECRETARIO_MAESTRO = "PATH_TALLER_SECRETARIO_MAESTRO";

	public static final String SIS_PRO_PATH_TALLER_SECRETARIO_APRENDIZ_NOTAS = "PATH_TALLER_SECRETARIO_APRENDIZ_NOTAS_DIRECTORIO";
	public static final String SIS_PRO_PATH_TALLER_SECRETARIO_APRENDIZ_PLANCHAS = "PATH_TALLER_SECRETARIO_APRENDIZ_PLANCHAS_DIRECTORIO";
	public static final String SIS_PRO_PATH_TALLER_SECRETARIO_APRENDIZ_BALAUSTRES = "PATH_TALLER_SECRETARIO_APRENDIZ_BALAUSTRES_DIRECTORIO";

	public static final String SIS_PRO_PATH_TALLER_SECRETARIO_COMPANERO_NOTAS = "PATH_TALLER_SECRETARIO_COMPANERO_NOTAS_DIRECTORIO";
	public static final String SIS_PRO_PATH_TALLER_SECRETARIO_COMPANERO_PLANCHAS = "PATH_TALLER_SECRETARIO_COMPANERO_PLANCHAS_DIRECTORIO";
	public static final String SIS_PRO_PATH_TALLER_SECRETARIO_COMPANERO_BALAUSTRES = "PATH_TALLER_SECRETARIO_COMPANERO_BALAUSTRES_DIRECTORIO";

	public static final String SIS_PRO_PATH_TALLER_SECRETARIO_MAESTRO_NOTAS = "PATH_TALLER_SECRETARIO_MAESTRO_NOTAS_DIRECTORIO";
	public static final String SIS_PRO_PATH_TALLER_SECRETARIO_MAESTRO_PLANCHAS = "PATH_TALLER_SECRETARIO_MAESTRO_PLANCHAS_DIRECTORIO";
	public static final String SIS_PRO_PATH_TALLER_SECRETARIO_MAESTRO_BALAUSTRES = "PATH_TALLER_SECRETARIO_MAESTRO_BALAUSTRES_DIRECTORIO";

	public static final String SIS_PRO_PATH_BIBLIOTECA_APRENDIZ = "PATH_BIBLIOTECA_APRENDIZ";
	public static final String SIS_PRO_PATH_BIBLIOTECA_COMPANERO = "PATH_BIBLIOTECA_COMPANERO";
	public static final String SIS_PRO_PATH_BIBLIOTECA_MAESTRO = "PATH_BIBLIOTECA_MAESTRO";
	public static final String SIS_PRO_PATH_BIBLIOTECA_FILOSOFICO = "PATH_BIBLIOTECA_FILOSOFICO";

	public static final String SIS_PRO_PATH_TALLER_TENIDAS = "PATH_TALLER_TENIDAS";

	public static final String SIS_PRO_EMAIL_DESTINATARIOS_CONTACTO = "EMAIL_DESTINATARIOS_CONTACTO";
	public static final String SIS_PRO_EMAIL_DESTINATARIOS_NOTIFICACION = "EMAIL_DESTINATARIOS_NOTIFICACION";
	public static final String SIS_PRO_PERIODO_CORRIENTE = "PERIODO_CORRIENTE";
	public static final String SIS_PRO_IMPORTE_MENSUAL_CAPITACION_GL = "IMPORTE_MENSUAL_CAPITACION_GL";

	/*********************************
	 * Mensajes
	 ************************************/
	public static final String MENSAJE_GENERICO_GUARDAR_OK = "Guardado correctamente!..";
	public static final String MENSAJE_GENERICO_GUARDAR_ERROR = "Error al guardar.";

	/*********************************
	 * URL
	 ************************************/

	public static final String URL_TALLER_SECRETARIA_CAPITACIONES_HH = "/logia/gestion/taller_tesorero_capitaciones_hh.zul";
	public static final String URL_TALLER_SECRETARIA_CAPITACIONES_GL = "/logia/gestion/taller_tesorero_capitaciones_gl.zul";
	public static final String URL_TALLER_TENIDAS_CARGA_RAPIDA = "/logia/gestion/taller_tenidas_carga_rapida.zul";
	public static final String URL_TENIDA_VIEW_EDIT = "/logia/gestion/taller_tenidas_view_edit.zul";
	public static final String URL_DATOS_PERSONALES_EDIT = "/logia/gestion/datos_personales.zul";
	public static final String URL_TALLER_TESORERO = "/logia/gestion/taller_tesorero.zul";
	public static final String URL_TALLER_SECRETARIA = "/logia/gestion/taller_secretario.zul";

}
