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

		// Productos
		String[][] productos = { { "400 BA 525 BA - con Fosfato x 25 Kls.", "nombre", "", "" },
				{ "400 BC 454 BC - con Fosfato x 25 Kls.", "nombre", "", "" },
				{ "402 NC Unimix Nucleo Confinamiento x 25 Kls.", "nombre", "", "" },
				{ "403NL Bobino Lactacion x 20 Kls.", "nombre", "", "" },
				{ "404NT NC Bobino Unimix TMR Conf. X 25 KLS.", "nombre", "", "" },
				{ "410 AG Nucleo Anti Parasitario x 25Kls", "nombre", "", "" },
				{ "411 NC NC Bobino Unimix Conf. PAHSA", "nombre", "", "" },
				{ "525 BA Vitamin. Nutron Cres./ Confin.x 20 Kls.", "nombre", "", "" },
				{ "534 GAD Nutron Beef Grano Entero 35/80", "nombre", "", "" },
				{ "540 PGE Pre Mezcla Grano Entero x 40 Kls.", "nombre", "", "" },
				{ "541 UGE Unibeef Grano Entero x 40Kls.", "nombre", "", "" },
				{ "571 LEU Nutron Bicox Energy", "nombre", "", "" },
				{ "700 SM Unimix Engorde x 25 Kls.", "nombre", "", "" },
				{ "701 SM Unimix Crecimiento x 25 Kls.", "nombre", "", "" },
				{ "702 SM Unimix Cria x 25 Kls.", "nombre", "", "" },
				{ "703 SM Unimix Mescla 25 Kls.", "nombre", "", "" }, { "708 SM Unimix Produccion", "nombre", "", "" },
				{ "709 SM Unimix Leche AD", "nombre", "", "" },
				{ "710 PB Unimix Engorde Pasto Bom x 25 Kls.", "nombre", "", "" },
				{ "712 PB Unimix Cria Pasto Bom x 25 Kls.", "nombre", "", "" },
				{ "770 LAA PX Beef Micromin SM x 20 kls.", "nombre", "", "" },
				{ "770 LPA PX Beef Micromin Proteinado", "nombre", "", "" },
				{ "800 FS Foscalcio 18 % x 50 Kls.", "nombre", "", "" },
				{ "801 FS Fosfato 18 % x 50 Kls.", "nombre", "", "" },
				{ "802 FS Fosfato 18 % x 25 Kls.", "nombre", "", "" },
				{ "802 CA Calcareo citrico 35 - 38 %", "nombre", "", "" },
				{ "804 UP Urea x 50 Kls. ARGENTINA", "nombre", "", "" },
				{ "804 ES Expeller de Soja", "nombre", "", "" }, { "805 EG Afrecho de Trigo", "nombre", "", "" },
				{ "806 EM Expeller de Maiz (Burlanda)", "nombre", "", "" },
				{ "815 OP Optigen II x 25 Kls.", "nombre", "", "" },
				{ "816 PT Urea Liberacion lenta x 25 kls.", "nombre", "", "" },
				{ "904 SM Unimix Proteica Top Seca x 25 Kls.", "nombre", "", "" },
				{ "905 SM Unimix Proteica Seca x 25 Kls.", "nombre", "", "" },
				{ "906 SM Unimix Creep Feeding x 25 Kls.", "nombre", "", "" },
				{ "911 SM Unimix Cria Chaco x 25 Kls.", "nombre", "", "" },
				{ "933 SM Unimix Proteica Chaco", "nombre", "", "" },
				{ "940 SM Unimix Proteinado Concentrado x 25 kls", "nombre", "", "" },
				{ "950 SM Concentrado protein. Energetico 30-PB", "nombre", "", "" },
				{ "951 SM Concentrado Protein. Energetico 60 PB", "nombre", "", "" }, { "Cloruro", "nombre", "", "" },
				{ "Esencia de Naranja", "nombre", "", "" }, { "Dextrina", "nombre", "", "" },
				{ "B101 SAL GRUESA CONS. HUMANO X 50Kls.", "nombre", "", "" },
				{ "B102 SAL GRUESA CONS. HUMANO X 25 Kls.", "nombre", "", "" },
				{ "B103 SAL ENTRE FINA CONS. HUMANO X 50Kls.", "nombre", "", "" },
				{ "B104 SAL ENTREFINA YODADA HUMANO X 25 KLS.", "nombre", "", "" },
				{ "B105 SAL FINA CONS. HUMANO X 50 KlS.", "nombre", "", "" },
				{ "B106 SAL MOLIDA X 25 Kls. ", "nombre", "", "" },
				{ "B107 SAL GRUESA - INDUSTRIAL X 50 Kls.", "nombre", "", "" },
				{ "B109 SAL FINA SECA X 50 KLS.", "nombre", "", "" },
				{ "B110 SAL PARRILLERA SECA X 25 KLS.", "nombre", "", "" },
				{ "B111 SAL PARRILLERA SECA X 50 KLS.", "nombre", "", "" },
				{ "B114 SAL RECUPERADA X 50 Kls.", "nombre", "", "" },
				{ "B122 SAL ENTREFINA INDUSTRIAL X 50Kls.", "nombre", "", "" },
				{ "B125 SAL FINA INDUSTRIAL X 50Kls.", "nombre", "", "" },
				{ "B150 SAL GRUESA CONS. ANIMAL X 50Kls", "nombre", "", "" },
				{ "B151 SAL EXTRAFINA CONS. ANIMAL X 50 Kls.", "nombre", "", "" },
				{ "B152 SAL FINA CONS. ANIMAL X 50 Kls.", "nombre", "", "" },
				{ "B160 SAL GRUESA CONS. ANIMAL X 25 Kls.", "nombre", "", "" },
				{ "B161 SAL ENTREFINA CONSUMO ANIMAL X 25 Kls.", "nombre", "", "" },
				{ "B162 SAL FINA CONSUMO ANIMAL X 25 Kls.", "nombre", "", "" },
				{ "F301 SAL FINA EN FARDOS DE 30 X 1 KILO", "nombre", "", "" },
				{ "F302 SAL GRUESA EN FARDOS DE 30 X 1 KILO", "nombre", "", "" },
				{ "F308 SAL FINA MOC EN FARDOS DE 30 X 1 KILO", "nombre", "", "" },
				{ "F311 SAL FINA EN FARDOS DE 30 X 500 GRAMOS ", "nombre", "", "" },
				{ "F500 SAL FINA EN FARDOS DE 5 X 6 KILO", "nombre", "", "" },
				{ "F501 SAL GRUESA EN FARDOS DE 5 X 6 KILO", "nombre", "", "" },
				{ "F502 SAL ENTREFINA EN FARDOS DE 5 X 6 KILO", "nombre", "", "" } };
		Tipo toProducto = rr.getTipoPorSigla(Configuracion.SIGLA_TIPO_OBJETO_PRODUCTO);
		cargarMyObjects(toProducto, productos);

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