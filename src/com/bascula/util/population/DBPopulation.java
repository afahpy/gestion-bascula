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

		// LUGARES DE ORIGEN
		String[][] origenLugares = { { "JUJUY", "nombre", "", "" }, { "CLORINDA", "nombre", "", "" } };
		Tipo toOrigenLugar = rr.getTipoPorSigla(Configuracion.SIGLA_TIPO_OBJETO_ORIGEN_LUGAR);
		cargarMyObjects(toOrigenLugar, origenLugares);

		// LUGARES DE DESTINO
		String[][] destinoLugares = { { "Oleaginosa Raatz S.A", "nombre", "", "" }, { "CDE", "nombre", "", "" },
				{ "Limpio", "nombre", "", "" }, { "COPALSA", "nombre", "", "" },
				{ "Comercial Paola", "nombre", "", "" }, { "Compañía de Nutricion Animal S.A", "nombre", "", "" },
				{ "Unisal MRA", "nombre", "", "" }, { "Curuguaty", "nombre", "", "" },
				{ "FERNHEIM LTDA", "nombre", "", "" }, { "Yuty", "nombre", "", "" },
				{ "Edelira S.A", "nombre", "", "" }, { "FRIGOMERC S.A", "nombre", "", "" }, };
		Tipo toDestinoLugar = rr.getTipoPorSigla(Configuracion.SIGLA_TIPO_OBJETO_DESTINO_LUGAR);
		cargarMyObjects(toDestinoLugar, destinoLugares);

		// CHAPA
		String[][] chapas = { { "AET 708", "numero", "", "" }, { "CBA 908", "numero", "", "" },
				{ "AFO 983", "numero", "", "" }, { "OBN 358", "numero", "", "" }, { "AKT 148", "numero", "", "" },
				{ "BGA 824", "numero", "", "" }, { "XAF 003", "numero", "", "" }, { "ALL 635", "numero", "", "" },
				{ "BDE 438", "numero", "", "" }, { "BBY 485", "numero", "", "" }, };
		Tipo toChapa = rr.getTipoPorSigla(Configuracion.SIGLA_TIPO_OBJETO_CHAPA);
		cargarMyObjects(toChapa, chapas);

		// CHOFER
		String[][] choferes = { { "Ruben Esneider", "nombre", "", "" }, { "Jorge Reyes", "nombre", "", "" },
				{ "Edgar De La Cruz", "nombre", "", "" }, { "Pablo Centurion", "nombre", "", "" },
				{ "Oscar Gauto", "nombre", "", "" }, { "Diego Valenzuela", "nombre", "", "" },
				{ "Victor Sarabia", "nombre", "", "" }, { "Marcelo Yegros", "nombre", "", "" },
				{ "Miguel Gerhard", "nombre", "", "" } };
		Tipo toChofer = rr.getTipoPorSigla(Configuracion.SIGLA_TIPO_OBJETO_CHOFER);
		cargarMyObjects(toChofer, choferes);

		// Transportadora
		String[][] transportadoras = { { "ALMADA", "nombre", "", "" }, { "ALTO PARAGUAY", "nombre", "", "" },
				{ "CELCAR", "nombre", "", "" }, { "EL PROGRESO", "nombre", "", "" }, { "MARCELO", "nombre", "", "" },
				{ "NILMORI", "nombre", "", "" }, { "SAILO TRANS", "nombre", "", "" }, { "SALINAS", "nombre", "", "" },
				{ "SEGA S.R.L", "nombre", "", "" } };
		Tipo toTransportadora = rr.getTipoPorSigla(Configuracion.SIGLA_TIPO_OBJETO_TRANSPORTADORA);
		cargarMyObjects(toTransportadora, transportadoras);

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