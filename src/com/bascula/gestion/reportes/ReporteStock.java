package com.bascula.gestion.reportes;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import com.bascula.domain.MyObject;
import com.bascula.domain.RegisterDomain;
import com.bascula.util.population.reporte.BasculaReporte;
import com.coreweb.util.Misc;

import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalFlowListBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.component.VerticalListBuilder;

public class ReporteStock extends BasculaReporte {

	RegisterDomain rr = RegisterDomain.getInstance();

	Date fd = new Date();
	Date fh = new Date();
	List<Long> lIdProds = null;
	List<Long> lIdLugares = null;
	// se ponen los ids separados por coma, para optimizar la búsqueda.
//	List<Long> lIdLugaresOrigen = null;
//	List<Long> lIdLugaresDestino = null;

	Hashtable<Long, List<Object[]>> hashDatos = null;
	Hashtable<Long, Long> hashSaldosIniciales = null;

	String qq = "" //
			+ "det.mercaderia.id, " // 0
			+ "det.mercaderia.strCampo1, " // 1
			+ "mov.fechaLlegada, " // 2
			+ "mov.fechaSalida, " // 3
			+ "mov.origenLugar.id, " // 4
			+ "mov.destinoLugar.id, " // 5
			+ "mov.remito, " // 6
			+ "mov.remision, " // 7
			+ "det.mercaderia.id, " // 8
			+ "det.numeroBolsa,  " // 9
			+ "mov.origenLugar.strCampo1,  " // 10
			+ "mov.destinoLugar.strCampo1  "; // 11

	String[][] cols = { { "Fecha", WIDTH + "40" }, { "Dato", WIDTH + "30" }, { "Ingreso", DERECHA+WIDTH + "40" },
			{ "Egreso", DERECHA+WIDTH + "40" }, { "saldo", DERECHA+WIDTH + "40" }, };

	@Override
	public void informacionReporte() {
		try {

			this.setTitulo("Stock de sales en patio");
			this.saldoSales();
			ComponentBuilder tbs = this.getTablas();
			this.setBody(tbs);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private ComponentBuilder getTablas() throws Exception {

		VerticalListBuilder out = cmp.verticalList();

		// datos de cabecera

		
		
		HorizontalListBuilder cab = cmp.horizontalFlowList();
		
		cab.add(this.textoParValor("Fecha desde", this.m.dateToString(this.fd, "dd-MM-yyyy")));
		cab.add(this.textoParValor("Fecha hasta", this.m.dateToString(this.fh, "dd-MM-yyyy")));

		out.add(cab);
		out.add(this.espacioAlto(10));
		
		String tablaProp =  LETRA_10 +  TABLA_TITULO + "";

		for (int i = 0; i < lIdProds.size(); i++) {
			long idPro = lIdProds.get(i);

			MyObject mo = (MyObject) rr.getObject(MyObject.class.getName(), idPro);
			String nombreProducto = mo.getStrCampo1();

			List<Object[]> datos = this.getDatosTabla(idPro);

			long saldoIni = this.hashSaldosIniciales.get(idPro);
			
			String titulo = tablaProp + nombreProducto+ "  (saldo inicial: "+saldoIni+")";
			
			HorizontalListBuilder tabla = (HorizontalListBuilder)this.getTabla(this.cols, datos, titulo, false, true, false);

			tabla.setFixedWidth(400);

			
			out.add(tabla);
			out.add(this.espacioAlto(10));

		}

		return out;
	}

	private void saldoSales() throws Exception {

		fd = m.toFecha0000(fd);
		fh = m.toFecha2400(fh);
		// siempre todos
		Date desdeInicio = m.stringToDate("2017-01-01");


		// los id de los productos
		String qpro = " 1 != 1 ";
		for (int i = 0; i < lIdProds.size(); i++) {
			long idPro = lIdProds.get(i);
			qpro += " or det.mercaderia.id = " + idPro;
		}

		// los ids de logares
		String qlugOrigen = " 1 != 1 ";
		String qlugDestino = " 1 != 1 ";
		for (int i = 0; i < lIdLugares.size(); i++) {
			long idLugar = lIdLugares.get(i);
			qlugOrigen += " or mov.origenLugar.id = "+idLugar + " ";
			qlugDestino += " or mov.destinoLugar.id = "+idLugar + " ";
		}
		qlugOrigen = "mov.fechaSalida between :fdesde and :fhasta and (" + qlugOrigen+")";
		qlugDestino = "mov.fechaLlegada between :fdesde and :fhasta and (" + qlugDestino+")";

		String qWhereMov = "("+qlugOrigen+") or ("+qlugDestino+")";
		
		String query = "";

		query += " select " + qq + "  " //
				+ " from  MovimientoEntradaSalida mov join mov.detalles det " //
				+ " where (" + qWhereMov + ") and  ("+ qpro + ") "; //

		System.out.println(query);
		
		Hashtable<String, Object> params = new Hashtable<>();
		params.put(":fdesde", desdeInicio);
		params.put(":fhasta", fh);

		double out = 0.0;
		List<Object[]> list = rr.hql(query, params);

		// calcula saldos y separa por productos
		this.iniHash();
		for (int i = 0; i < list.size(); i++) {

			Object[] dato = list.get(i);
			long idProd = this.getIdProducto(dato);

			long cantidad = this.getCantidad(dato, this.fd);
			if (cantidad == 0) {
				// guardar para mostrar
				List<Object[]> datos = hashDatos.get(idProd);
				if (datos == null) {
					datos = new ArrayList<>();
					hashDatos.put(idProd, datos);
				}
				datos.add(dato);

			} else {
				// acumular saldo
				Long saldo = this.hashSaldosIniciales.get(idProd);
				if (saldo == null) {
					saldo = new Long(0);
				}
				saldo += cantidad;
				this.hashSaldosIniciales.put(idProd, saldo);

			}

		}
/*
		System.out.println("==========================");
		for (int i = 0; i < list.size(); i++) {
			Object[] ff = list.get(i);
			String linea = "";
			for (int j = 0; j < ff.length; j++) {
				linea += " - " + ff[j];
			}
			System.out.println(i + ") " + linea);
		}
		System.out.println("==========================");
*/
		return;
	}

	/**
	 * 0 = mostrar, -1 = es de salida, 1 = es de llegada
	 */
	private long getCantidad(Object[] dato, Date fecha) {

		
		Date fde = (Date) dato[3];
		Date fha = (Date) dato[2];
		long dOri = (long) dato[4];
		long dDes = (long) dato[5];
		long cantidad = (long) ((int) dato[9] + 0);
		
		boolean idOriOk = lIdLugares.contains(dOri);
		boolean idDesOk = lIdLugares.contains(dDes);
		
		if ((idOriOk == true) && (fde.after(fecha) == true)) {
			// dentro del rango
			return 0;
		}

		if ((idDesOk==true) && (fha.after(fecha) == true)) {
			// dentro del rango
			return 0;
		}

		if ((idOriOk == true) && (fde.before(fecha) == true)) {
			// es un salida
			return (-1 * cantidad);
		}

		if ((idDesOk == true) && (fha.before(fecha) == true)) {
			// dentro del rango
			return (cantidad);
		}

		// no debería llegar acá, así que muestre
		return 0;
	}


	private long getCantidad(Object[] dato) {

		long dOri = (long) dato[4];
		long dDes = (long) dato[5];
		long cantidad = (long) ((int) dato[9] + 0);
		
		boolean idOriOk = lIdLugares.contains(dOri);
		boolean idDesOk = lIdLugares.contains(dDes);

		
		if (idOriOk  == true) {
			// es un salida
			return (-1 * cantidad);
		}

		if (idDesOk  == true) {
			// dentro del rango
			return (cantidad);
		}

		// no debería llegar acá, así que muestre
		return 0;
	}
	
	
	
	private long getIdProducto(Object[] dato) {
		return (long) dato[0];
	}

	private void iniHash() {

		hashDatos = new Hashtable<>();
		hashSaldosIniciales = new Hashtable<>();
		for (int i = 0; i < lIdProds.size(); i++) {
			long idProd = lIdProds.get(i);
			hashDatos.put(idProd, new ArrayList<>());
			hashSaldosIniciales.put(idProd, new Long(0));
		}

	}

	/**
	 * Obtiene los datos quue se muestran en una tabla
	 * 
	 * @param idProd
	 * @return
	 */
	private List<Object[]> getDatosTabla(long idProd) {

		List<Object[]> out = new ArrayList<>();

		// buscar los datos de los has
		List<Object[]> datos = this.hashDatos.get(idProd);
		long saldo = this.hashSaldosIniciales.get(idProd);

		// recorrer y armar los saldos
		for (int i = 0; i < datos.size(); i++) {
			Object[] ff = datos.get(i);

			Date dFecha = null;
			String dDesc = "";
			long dIngreso = 0;
			long dSalida = 0;
			long dSaldo = 0;

			long cantidad = this.getCantidad(ff);
			if (cantidad > 0) {
				// ingreso
				dFecha = (Date) ff[2];
				dIngreso = cantidad;
				dSalida = 0;
			} else {
				// salida
				dFecha = (Date) ff[3];
				dIngreso = 0;
				dSalida = cantidad;
			}

			dDesc = (ff[6].toString().trim() + ff[7].toString().trim());
//			dDesc = (ff[10].toString().trim() + " - "+ff[11].toString().trim());

			if (dFecha == null) {
				dDesc += " err FECHA";
				dFecha = new Date();
			}

			Object[] datoTabla = new Object[5];
			datoTabla[0] = dFecha;
			datoTabla[1] = dDesc;
			datoTabla[2] = dIngreso;
			datoTabla[3] = dSalida;
			datoTabla[4] = dSaldo;

			out.add(datoTabla);
		}

		// ordenar
		this.m.ordernar(out, "[0]");
		// formatear

		for (int i = 0; i < out.size(); i++) {
			Object[] datoTabla = out.get(i);
			// calcula el saldo
			saldo = saldo + (long) datoTabla[2] + (long) datoTabla[3];
			// formatear
			datoTabla[0] = this.m.dateToString((Date) datoTabla[0], "dd-MM-yyyy");
			// datoTabla[1] = dDesc;
			datoTabla[2] = this.m.formato(datoTabla[2], 10, false, true);
			datoTabla[3] = this.m.formato(datoTabla[3], 10, false, true);
			datoTabla[4] = saldo;
		}
		return out;
	}

	// ==================================

	public Date getFd() {
		return fd;
	}

	public void setFd(Date fd) {
		this.fd = fd;
	}

	public Date getFh() {
		return fh;
	}

	public void setFh(Date fh) {
		this.fh = fh;
	}

	public List<Long> getlIdProds() {
		return lIdProds;
	}

	public void setlIdProds(List<Long> lIdProds) {
		this.lIdProds = lIdProds;
	}

	// ==================================================================
	// ==================================================================
	// ==================================================================

	public static void main(String[] args) throws Exception {
		Misc m = new Misc();

		List<Long> idsP = new ArrayList<>();
		idsP.add((long) 219);
//		idsP.add((long) 170);
//		idsP.add((long) 233);
//		idsP.add((long) 343);
//		idsP.add((long) 301);
//		idsP.add((long) 211);
//		idsP.add((long) 171);

		List<Long> idsLug = new ArrayList<>();
		idsLug.add((long)514);
		idsLug.add((long)9);
		idsLug.add((long)52);
		idsLug.add((long)513);

		
		ReporteStock rep = new ReporteStock();
		rep.setFd(m.stringToDate("2017-09-20"));
		rep.setFh(m.stringToDate("2018-09-20"));
		rep.setlIdLugares(idsLug);
		rep.setlIdProds(idsP);

		rep.ejecutar(true);
		// rep.saldoSales();

	}

	public List<Long> getlIdLugares() {
		return lIdLugares;
	}

	public void setlIdLugares(List<Long> lIdLugares) {
		this.lIdLugares = lIdLugares;
	}

	// ==================================================================
	// ==================================================================
	// ==================================================================

}
