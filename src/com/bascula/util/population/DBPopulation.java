package com.bascula.util.population;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.time.DateUtils;

import com.bascula.Configuracion;
import com.bascula.domain.MyObject;
import com.bascula.domain.RegisterDomain;
import com.coreweb.MenuConfigParser;
import com.coreweb.domain.AutoNumero;
import com.coreweb.domain.Domain;
import com.coreweb.domain.PermisoEditar;
import com.coreweb.domain.Tipo;
import com.coreweb.domain.Usuario;
import com.coreweb.util.Misc;
import com.bascula.util.population.DBPopulationDatos;
import com.bascula.util.population.DBPopulationTipos;

public class DBPopulation {

	private static RegisterDomain rr = RegisterDomain.getInstance();
	private static Misc m = new Misc();

	private static void grabarDB(Domain d) throws Exception {
		rr.saveObject(d, Configuracion.USER_SYSTEMA);
	}

	private static void cargarMyObjects(Tipo tipoObjeto, String[][] valores) throws Exception {

		for (int i = 0; i < valores.length; i++) {

			MyObject o = new MyObject();
			o.setTipoObjeto(tipoObjeto);
			o.setStrCampo1(valores[i][0]);
			o.setStrCampo1Alias(valores[i][1]);
			o.setStrCampo2(valores[i][2]);
			o.setStrCampo2Alias(valores[i][3]);

			System.out.println("--> " + tipoObjeto.getDescripcion() + "--" + o.getStrCampo1());
			grabarDB(o);
		}

	}

	public static void cargarDatosPrueba() throws Exception {
		String[][] origenLugares = { { "JUJUY", "nombre", "", "" }, { "Prueba", "nombre", "", "" },
				{ "Prueba 2", "nombre", "", "" }, { "Prueba 3", "nombre", "", "" } };
		Tipo toOrigenLugar = rr.getTipoPorSigla(Configuracion.SIGLA_TIPO_OBJETO_ORIGEN_LUGAR);
		cargarMyObjects(toOrigenLugar, origenLugares);
	}

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception {

		/********************************
		 * Borrar BD
		 ************************************/

		long ceroTime = System.currentTimeMillis();
		rr.dropAllTables();
		long iniTime = System.currentTimeMillis();

		/********************************
		 * Cargar Tipos
		 *********************************/
		System.out.println("Cargando tipos....");
		DBPopulationTipos tt = new DBPopulationTipos();
		tt.main(null);
		System.out.println("Carga de tipos completa.");

		/********************************
		 * Cargar Permisos
		 *********************************/
		System.out.println("Cargando permisos....");
		MenuConfigParser menuConfParser = new MenuConfigParser();
		menuConfParser.main(null);
		System.out.println("Carga de permisos completa.");

		/******************************
		 * Datos de Prueba
		 ********************************/
		cargarDatosPrueba();

	}
}