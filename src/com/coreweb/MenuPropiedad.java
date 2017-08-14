package com.coreweb;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;

/**
 * Lee un archivo ini en donde se pondrá información propia del sistema Puede
 * ser extendido por cada aplicación si lo cree necesario, pero no es necesario
 * invocar nada raro, sólo hay que poner los métodos especiales de nombres de
 * valores y listo.
 * 
 * @author daniel
 *
 */
public class MenuPropiedad {

	static private Hashtable<String, String> menuPro = null;

	static private long timeFechaArchivoIni = 0;

	// por defecto busca este archivo
	static private String FILE = Config.DIRECTORIO_BASE_REAL
			+ "/WEB-INF/menu-propiedad.ini";

	// carga las propiedades
	static {
		reloadSistemaPropiedad();
	}

	public static synchronized void reloadSistemaPropiedad() {
		reloadSistemaPropiedad(FILE);
	}

	public static synchronized void reloadSistemaPropiedad(String file) {

		// solo lee si hubo un cambio en el archivo
		File fichero = new File(file);
		long ms = fichero.lastModified();
		if (ms == timeFechaArchivoIni) {
			return;
		}
		timeFechaArchivoIni = ms;

		// si hubo un cambio, entonces hace el reload

		menuPro = new Hashtable<>();

		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream(file);

			// load a properties file
			prop.load(input);
			Enumeration<Object> enu = prop.keys();
			for (; enu.hasMoreElements();) {
				String k = (String) enu.nextElement();
				String v = prop.getProperty(k);
				menuPro.put(k, v);
			}

		} catch (Exception ex) {
			System.err
					.println("==========Menu Propiedad =====================");
			ex.printStackTrace();
			System.err
					.println("=================================================");
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	public boolean isControlLoginPage(){
		boolean out = true;
		String ctrLogin = this.getPropiedad("CONTROL_LOGIN");
		if (ctrLogin != null){
			out = Boolean.parseBoolean(ctrLogin);
		}
		return out;
	}
	
	
	/**
	 * Lee una propiedad del menu-propiedad.ini, retorna null si no la
	 * encuentra
	 * 
	 * @param propiedad
	 * @return
	 */
	public String getPropiedad(String propiedad) {
		reloadSistemaPropiedad();
		String out = menuPro.get(propiedad);
		if (out != null) {
			out = out.trim();
		}
		return out;
	}

	public boolean getPiePagina() {
		boolean siPie = true;
		String strSiPie = this.getPropiedad("PIE_PAGINA");
		if (strSiPie != null){
			siPie = Boolean.parseBoolean(strSiPie);
		}
		return siPie;
	}

}
