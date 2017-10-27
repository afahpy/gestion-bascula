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

	static int TIPO_INGRESO = 1;
	static int TIPO_EGRESO = -1;
	static int TIPO_AMBOS = 0;

	static int TIPO_SI_MOSTRAR = 20;
	static int TIPO_NO_MOSTRAR = 21;

	RegisterDomain rr = RegisterDomain.getInstance();

	Date fd = new Date();
	Date fh = new Date();
	List<Long> lIdProds = null;
	List<Long> lIdLugares = null;
	// se ponen los ids separados por coma, para optimizar la búsqueda.
	// List<Long> lIdLugaresOrigen = null;
	// List<Long> lIdLugaresDestino = null;

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
			+ "mov.destinoLugar.strCampo1,  " // 11
			+ "mov.tipoMovimiento.descripcion,  " // 12
			+ "0  " // 13 usado para definir si se muestra o no
			+ " " // xx
			+ " "; // xx

	int anchoTabla = 550;
	String[][] cols = { //
			{ "Fecha", WIDTH + "60" }, // 0
			{ "Dato", WIDTH + "30" }, // 1
			{ "Ingreso", DERECHA + WIDTH + "40" }, // 2
			{ "Egreso", DERECHA + WIDTH + "40" }, // 3
			{ "saldo", DERECHA + WIDTH + "40" }, // 4
			{ "Desdde", WIDTH + "60" }, // 5
			{ "Hasta", WIDTH + "60" }, // 6
	};

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

		String tablaProp = LETRA_10 + TABLA_TITULO + "";

		for (int i = 0; i < lIdProds.size(); i++) {
			long idPro = lIdProds.get(i);

			MyObject mo = (MyObject) rr.getObject(MyObject.class.getName(), idPro);
			String nombreProducto = mo.getStrCampo1();

			List<Object[]> datos = this.getDatosTabla(idPro);

			long saldoIni = this.hashSaldosIniciales.get(idPro);

			String titulo = tablaProp + nombreProducto + "  (saldo inicial: " + saldoIni + ")";

			HorizontalListBuilder tabla = (HorizontalListBuilder) this.getTabla(this.cols, datos, titulo, false, true,
					false);

			tabla.setFixedWidth(anchoTabla);

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
			qlugOrigen += " or mov.origenLugar.id = " + idLugar + " ";
			qlugDestino += " or mov.destinoLugar.id = " + idLugar + " ";
		}
		qlugOrigen = "mov.fechaSalida between :fdesde and :fhasta and (" + qlugOrigen + ")";
		qlugDestino = "mov.fechaLlegada between :fdesde and :fhasta and (" + qlugDestino + ")";

		String qWhereMov = "(" + qlugOrigen + ") or (" + qlugDestino + ")";

		String query = "";

		query += " select " + qq + "  " //
				+ " from  MovimientoEntradaSalida mov join mov.detalles det " //
				+ " where (" + qWhereMov + ") and  (" + qpro + ") "; //

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

			long tipoMov = this.getSiMostrar(dato);
			if(tipoMov == TIPO_SI_MOSTRAR){
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
				
				long tipMov = this.getTipoMov(dato);
				saldo += (this.getCantidad(dato) * tipMov);
				this.hashSaldosIniciales.put(idProd, saldo);

			}

		}
		/*
		 * System.out.println("=========================="); for (int i = 0; i <
		 * list.size(); i++) { Object[] ff = list.get(i); String linea = ""; for
		 * (int j = 0; j < ff.length; j++) { linea += " - " + ff[j]; }
		 * System.out.println(i + ") " + linea); }
		 * System.out.println("==========================");
		 */
		return;
	}

	/**
	 * 0 = mostrar, -1 = es de salida, 1 = es de llegada
	 */
	private long getSiMostrar(Object[] dato) {

		long out = -1;
		
		Date fde = (Date) dato[3];
		Date fha = (Date) dato[2];
		long dOri = (long) dato[4];
		long dDes = (long) dato[5];
		long cantidad = (long) ((int) dato[9] + 0);

		boolean idOriOk = lIdLugares.contains(dOri);
		boolean idDesOk = lIdLugares.contains(dDes);

		out = TIPO_NO_MOSTRAR;

		if ((idOriOk == true) &&  this.siEntreFecha(fde)){ 
			out = TIPO_SI_MOSTRAR;
		}


		if ((idDesOk == true) &&  this.siEntreFecha(fha)){ 
			// dentro del rango
			out = TIPO_SI_MOSTRAR;
		}


		// no debería llegar acá, así que muestre
		System.out.println("dOri:"+dOri+"   dDes:"+dDes+"  fde:"+fde+"  fha:"+fha+"    out:"+out);

		return out;
	}

	private long getCantidad(Object[] dato) {
		long cantidad = (long) ((int) dato[9] + 0);
		return cantidad;
	}

	
	boolean siEntreFecha(Date fecha){
		return fecha.before(this.fh) && fecha.after(this.fd);
		
	}
	
	private long getTipoMov(Object[] dato) {

		Date fde = (Date) dato[3];
		Date fha = (Date) dato[2];
		long dOri = (long) dato[4];
		long dDes = (long) dato[5];
		long cantidad = (long) ((int) dato[9] + 0);

		boolean idOriOk = lIdLugares.contains(dOri);
		boolean idDesOk = lIdLugares.contains(dDes);

		if ((idOriOk == true) && (idDesOk == true)){// && (fde.after(this.fd)==true) && (fha.before(this.fh)==true)){ 
			return TIPO_AMBOS;
		}

		if ((idOriOk == true)){// && (fde.after(this.fd) == true)) {
			// dentro del rango
			return TIPO_EGRESO;
		}

		if ((idDesOk == true) ){//&& (fha.before(this.fh) == true)) {
			// dentro del rango
			return TIPO_INGRESO;
		}

		// no debería llegar acá, así que muestre
		return -1000;
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
			String tipoMov = "";

			String desdeStr = ((ff[10] + "            ")).substring(0, 15).trim();
			String hastaStr = ((ff[11] + "            ")).substring(0, 15).trim();

			
			tipoMov = desdeStr + " -> " + hastaStr;

			long cantidad = this.getCantidad(ff);
			long tMov = this.getTipoMov(ff);
			if ((tMov == TIPO_AMBOS)||(tMov == TIPO_INGRESO)){
				dIngreso = cantidad;
				dFecha = (Date)ff[2]; 
			}
			
			if ((tMov == TIPO_AMBOS)||(tMov == TIPO_EGRESO)){
				dSalida = (cantidad * -1);
				dFecha = (Date)ff[3]; 
			}
			
			dDesc = (ff[6].toString().trim() + ff[7].toString().trim());
			// dDesc = (ff[10].toString().trim() + " -
			// "+ff[11].toString().trim());

			if (dFecha == null) {
				dDesc += " err FECHA";
				dFecha = new Date();
			}

			Object[] datoTabla = new Object[7];
			datoTabla[0] = dFecha;
			datoTabla[1] = dDesc;
			datoTabla[2] = dIngreso;
			datoTabla[3] = dSalida;
			datoTabla[4] = dSaldo;
			datoTabla[5] = desdeStr;
			datoTabla[6] = hastaStr;

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
		 idsP.add((long) 170);
		 idsP.add((long) 233);
		 idsP.add((long) 343);
		 idsP.add((long) 301);
		 idsP.add((long) 211);
		 idsP.add((long) 171);

		List<Long> idsLug = new ArrayList<>();
		idsLug.add((long) 514);
		idsLug.add((long) 9);
		idsLug.add((long) 52);
		idsLug.add((long) 513);

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
