package com.bascula.util.population.unisal;

import java.util.List;

import com.bascula.Configuracion;
import com.bascula.domain.MovimientoEntradaSalida;
import com.bascula.domain.MyObject;
import com.bascula.domain.RegisterDomain;
import com.coreweb.domain.Domain;
import com.coreweb.domain.Tipo;

public class CargaTipoSal {

	static RegisterDomain rr = RegisterDomain.getInstance();

	private static void grabarDB(Domain d) throws Exception {
		 rr.saveObject(d, Configuracion.USER_SYSTEMA);
	}

	// Productos
	static String[][] productos = { { "400 BA 525 BA - con Fosfato x 25 Kls.", "nombre", "", "" },
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
			{ "702 SM Unimix Cria x 25 Kls.", "nombre", "", "" }, { "703 SM Unimix Mescla 25 Kls.", "nombre", "", "" },
			{ "708 SM Unimix Produccion", "nombre", "", "" }, { "709 SM Unimix Leche AD", "nombre", "", "" },
			{ "710 PB Unimix Engorde Pasto Bom x 25 Kls.", "nombre", "", "" },
			{ "712 PB Unimix Cria Pasto Bom x 25 Kls.", "nombre", "", "" },
			{ "770 LAA PX Beef Micromin SM x 20 kls.", "nombre", "", "" },
			{ "770 LPA PX Beef Micromin Proteinado", "nombre", "", "" },
			{ "800 FS Foscalcio 18 % x 50 Kls.", "nombre", "", "" },
			{ "801 FS Fosfato 18 % x 50 Kls.", "nombre", "", "" },
			{ "802 FS Fosfato 18 % x 25 Kls.", "nombre", "", "" },
			{ "802 CA Calcareo citrico 35 - 38 %", "nombre", "", "" },
			{ "804 UP Urea x 50 Kls. ARGENTINA", "nombre", "", "" }, { "804 ES Expeller de Soja", "nombre", "", "" },
			{ "805 EG Afrecho de Trigo", "nombre", "", "" }, { "806 EM Expeller de Maiz (Burlanda)", "nombre", "", "" },
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
			{ "F502 SAL ENTREFINA EN FARDOS DE 5 X 6 KILO", "nombre", "", "" } 
			
			,
			{ "x1 F308 SAL FINA MOC EN FARDOS DE 30 X 1 KILO", "nombre", "", "" },
			{ "x2 F311 SAL FINA EN FARDOS DE 30 X 500 GRAMOS ", "nombre", "", "" },
			{ "x3 F500 SAL FINA EN FARDOS DE 5 X 6 KILO", "nombre", "", "" },
			{ "x4 F501 SAL GRUESA EN FARDOS DE 5 X 6 KILO", "nombre", "", "" },
			{ "x5 F502 SAL ENTREFINA EN FARDOS DE 5 X 6 KILO", "nombre", "", "" } 

			
	
	};

	private static void cargaProductos() throws Exception {
		Tipo toProducto = rr.getTipoPorSigla(Configuracion.SIGLA_TIPO_OBJETO_PRODUCTO);
		cargarMyObjects(toProducto, productos);

	}

	private static void cargarMyObjects(Tipo tipoObjeto, String[][] valores) throws Exception {

		boolean todos = true;

		List<Domain> ld = (List<Domain>) rr.getObjects(MyObject.class.getName(), "tipoObjeto.id",
				tipoObjeto.getId().longValue());

		for (int i = 0; i < ld.size(); i++) {

			if (i < valores.length) {

				MyObject o = (MyObject) ld.get(i);

				String dd = "Actualizar --> (" + o.getId() + ") " +o.getTipoObjeto().getDescripcion()+"--" +o.getStrCampo1()+"";
				System.out.println(dd);
				o.setTipoObjeto(tipoObjeto);
				o.setStrCampo1(valores[i][0]);
				o.setStrCampo1Alias(valores[i][1]);
				o.setStrCampo2(valores[i][2]);
				o.setStrCampo2Alias(valores[i][3]);

				String gg = "     nuevo --> (" + o.getId() + ") " + tipoObjeto.getDescripcion() + "--" + o.getStrCampo1();
				System.out.println(gg);
				grabarDB(o);
			}

		}

		for (int i = ld.size(); i < valores.length; i++) {

			MyObject o = new MyObject();
			o.setTipoObjeto(tipoObjeto);
			o.setStrCampo1(valores[i][0]);
			o.setStrCampo1Alias(valores[i][1]);
			o.setStrCampo2(valores[i][2]);
			o.setStrCampo2Alias(valores[i][3]);

			System.out.println("Nuevo --> " + tipoObjeto.getDescripcion() + "--" + o.getStrCampo1());
			grabarDB(o);
		}

	}

	public static void diferencia() throws Exception{
		String qq = "select m from MovimientoEntradaSalida m ";
		List<Domain> ld = rr.hql(qq);
		for (int i = 0; i < ld.size(); i++) {
			MovimientoEntradaSalida m = (MovimientoEntradaSalida) ld.get(i);
			m.setDiferencia(m.getNeto() - m.getOrigen());
			System.out.println(i+" "+m);
			rr.saveObject(m, "dife");
			
		}
	}
	
	public static void main(String[] args) throws Exception {
		//cargaProductos();
		diferencia();
	}

}
