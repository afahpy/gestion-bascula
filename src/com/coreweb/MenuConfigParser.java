package com.coreweb;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;

import com.coreweb.domain.PermisoEditar;
import com.coreweb.domain.Register;
import com.coreweb.domain.Usuario;
import com.coreweb.util.Misc;

/**
 * Hace el parser del MenuConfig.ini
 * 
 * @author daniel
 *
 */
public class MenuConfigParser {

	private Misc misc = new Misc();
	private static String dfv = "defaultValue";
	private static String split = ";";

	static private String MENU_INI = Config.DIRECTORIO_BASE_REAL + "/WEB-INF/menu_conf.ini";

	// el menu_config
	private Properties prop = null;
	// los usuarios
	private Hashtable<String, Usuario> usuarios = null;
	// para tener un orden siempre igual de los usuarios
	private ArrayList<Usuario> usuariosList = null;

	public void parserMenuConfig() throws Exception {
		// carga el menu en el properties
		prop = new Properties();
		prop.load(new InputStreamReader(new FileInputStream(MENU_INI), "utf-8"));

		// borrar los usuarios y los PermisosEditar, 0j0, que pasa con las
		// relaciones a la clase Usuario que puede haber desde el dominio de la
		// app??
		this.deleteDatos();

		// parser usuarios
		this.parserUsuarios();

		// parser permisos
		this.parserPermisos();
		
		// grabar en la BD los datos
		this.grabarDatos();

	}

	private void deleteDatos() throws Exception {
		Register rr = Register.getInstance();
		String delTablas = "DROP TABLE IF EXISTS " +
				"usuario, " +
				"usuario_permisoeditar, " +
				"permisoeditar " +
				"CASCADE";

		rr.sql2(delTablas);
		rr.resetTables();

	}

	private void grabarDatos() throws Exception {
		System.out.println("--- grabando en la BD");
		// grabar en la BD
		Register rr = Register.getInstance();
		
		for (int i = 0; i < this.usuariosList.size(); i++) {
			Usuario ui = this.usuariosList.get(i);
			for (Iterator ite = ui.getPermisos().iterator(); ite.hasNext();) {
				PermisoEditar pei = (PermisoEditar) ite.next();
				rr.saveObject(pei, "pp");
			}
			rr.saveObject(ui, "pp");
		}
	}

	
	/**
	 * recorre el menu_config y carga los usuarios en un hashtable
	 * 
	 * @throws Exception
	 */
	private void parserUsuarios() throws Exception {

		System.out.println("----------------- Usuarios ");

		this.usuarios = new Hashtable<>();
		this.usuariosList = new ArrayList<>();
		
		String u = "u";
		Usuario usuario = new Usuario();
		for (int ui = 1; (usuario != null); ui++) {
			usuario = null;
			String un = u + ui;
			// buscar el usuario en el archivo
			String uv = prop.getProperty(un, dfv);
			if (uv.compareTo(dfv) != 0) {
				// login ; nommbre ; clave
				String[] value = misc.split(uv, split);
				usuario = new Usuario();
				usuario.setLogin(value[0]);
				usuario.setNombre(value[1]);
				usuario.setClave(misc.encriptar(value[2]));

				this.usuarios.put(un, usuario);
				this.usuariosList.add(usuario);
				System.out.println("    " + un + " - " + usuario.getLogin());
			}
		}
		System.out.println("----------------------------- ");

	}

	private void parserPermisos() throws Exception {
		
		System.out.println("----------------- Permisos ");

		String op = "op";
		PermisoEditar permiso = new PermisoEditar();
		for (int opi = 1; (permiso != null); opi++) {
			permiso = null;
			String opn = op + opi;
			// buscar el permiso en el archivo
			String opv = prop.getProperty(opn, dfv);
			if (opv.compareTo(dfv) != 0) {
				// aliasOperacion ; (lista usuarios ux ; uy)
				String[] value = misc.split(opv, split);
				permiso = new PermisoEditar();
				permiso.setAlias(value[0]);
				permiso.setNombre(value[0]);
				permiso.setDescripcion(value[0]);
				// busca los usuarios de este permiso
				for (int i = 1; i < value.length; i++) {
					String un = value[i];
					Usuario usuario = this.usuarios.get(un);
					if (usuario != null) {
						usuario.getPermisos().add(permiso);
						System.out.println("usuario '" + un + "' en el permiso '" + permiso.getAlias() + "'");
					} else {
						// error no encontró este usuario
						System.err.println(
								"No se encontró el usuario '" + un + "' en el permiso '" + permiso.getAlias() + "'");
					}

				} // usuarios
			}
		} // permisos

		System.out.println("----------------------------- ");
	}

	public static void main(String[] args) throws Exception {

		MenuConfigParser mm = new MenuConfigParser();
		mm.parserMenuConfig();

	}
}
