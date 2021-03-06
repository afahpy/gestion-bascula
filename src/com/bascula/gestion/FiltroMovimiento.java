package com.bascula.gestion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.bascula.domain.MovimientoEntradaSalida;
import com.coreweb.extras.reporte.ReporteDefinicion;
import com.coreweb.util.Misc;

public class FiltroMovimiento implements Serializable {

	static String DATE = "fecha";
	static String NUMERO = "numero";
	static String OTRO = "otro";

	transient Misc m = new Misc();

	boolean tipoMovimiento = true;
	boolean fechaLlegada = true;
	boolean fechaSalida = true;
	boolean bruto = true;
	boolean tara = true;
	boolean neto = true;
	boolean origen = true;
	boolean diferencia = true;
	boolean origenLugar = true;
	boolean destinoLugar = true;
	boolean remito = true;
	boolean remision = true;
	boolean chapa = true;
	boolean chapaCarreta = true;
	boolean chofer = true;
	boolean transportadora = true;
	boolean despacho = true;
	boolean despachante = true;
	boolean detalles = true;

	String tipoMovimientoInt = "30px";
	String fechaLlegadaInt = "120px";
	String fechaSalidaInt = "1200px";
	String brutoInt = "65px";
	String taraInt = "65px";
	String netoInt = "65px";
	String origenInt = "65px";
	String diferenciaInt = "65px";
	String origenLugarInt = "65px";
	String destinoLugarInt = "65px";
	String remitoInt = "65px";
	String remisionInt = "65px";
	String chapaInt = "65px";
	String chapaCarretaInt = "65px";
	String choferInt = "65px";
	String transportadoraInt = "65px";
	String despachoInt = "65px";
	String despachanteInt = "65px";
	String detallesInt = "65px";

	
	private String filTipoMovimiento = "";//
	private String filLugarOrigen = "";
	private String filLugarDestino = "";
	private String filRemito = "";
	private String filRemision = "";
	private String filChapa = "";
	private String filChapaCarreta = "";
	private String filChofer = "";
	private String filDespacho = "";
	private String filTransportadora = "";
	private String filDespachante = "";
	private Date filtroFechaLlegadaDesde = null;
	private Date filtroFechaLlegadaHasta = null;
	private Date filtroFechaSalidaDesde = null;
	private Date filtroFechaSalidaHasta = null;
	
	
	
	static String DETALLES = "detalles";

	static String[][] datosCols = { { "tipoMovimiento", "E/S", ReporteDefinicion.WIDTH + "65", OTRO },
			{ "fechaLlegada", "Llegada", ReporteDefinicion.WIDTH + "65", DATE },
			{ "fechaSalida", "Salida", ReporteDefinicion.WIDTH + "65", DATE },
			{ "origenLugar", "Origen", ReporteDefinicion.WIDTH + "65", OTRO },
			{ "destinoLugar", "Destino", ReporteDefinicion.WIDTH + "65", OTRO },
			{ "remito", "Remito", ReporteDefinicion.WIDTH + "65", OTRO },
			{ "remision", "Remisión", ReporteDefinicion.WIDTH + "65", OTRO },
			{ "chofer", "Chofer", ReporteDefinicion.WIDTH + "65", OTRO },
			{ "transportadora", "Transp.", ReporteDefinicion.WIDTH + "65", OTRO },
			{ "chapa", "Chapa", ReporteDefinicion.WIDTH + "65", OTRO },
			{ "chapaCarreta", "Carreta", ReporteDefinicion.WIDTH + "65", OTRO },
			{ "bruto", "Kg.Bruto",
					ReporteDefinicion.PADDING_DER + ReporteDefinicion.DERECHA + ReporteDefinicion.WIDTH + "65",
					NUMERO },
			{ "tara", "Kg.Tara",
					ReporteDefinicion.PADDING_DER + ReporteDefinicion.DERECHA + ReporteDefinicion.WIDTH + "65",
					NUMERO },
			{ "neto", "Kg.Neto",
					ReporteDefinicion.PADDING_DER + ReporteDefinicion.DERECHA + ReporteDefinicion.WIDTH + "65",
					NUMERO },
			{ "origen", "Kg.Origen",
					ReporteDefinicion.PADDING_DER + ReporteDefinicion.DERECHA + ReporteDefinicion.WIDTH + "65",
					NUMERO },
			{ "diferencia", "Kg.Dif.",
					ReporteDefinicion.PADDING_DER + ReporteDefinicion.DERECHA + ReporteDefinicion.WIDTH + "65",
					NUMERO },
			{ "despacho", "Despacho", ReporteDefinicion.WIDTH + "65", OTRO },
			{ "despachante", "Despachante", ReporteDefinicion.WIDTH + "65", OTRO },
			{ DETALLES, "Detalles", ReporteDefinicion.WIDTH + "65", OTRO },

	};

	private Misc getM() {
		if (this.m == null) {
			this.m = new Misc();
		}
		return this.m;
	}

	/**
	 * Las columnas de la tabla
	 * 
	 * @return
	 * @throws Exception
	 */
	public String[][] getColTablas() throws Exception {
		String[][] out = null;
		ArrayList<String[]> lis = new ArrayList<>();

		for (int i = 0; i < datosCols.length; i++) {
			String nCol = datosCols[i][0];
			boolean b = (boolean) this.getM().getValue(this, nCol);
			if (b == true) {
				String[] c = { datosCols[i][1], datosCols[i][2] };
				lis.add(c);
			}
		}
		out = new String[lis.size()][2];
		for (int i = 0; i < out.length; i++) {
			out[i][0] = lis.get(i)[0];
			out[i][1] = lis.get(i)[1];
		}
		return out;
	}

	/**
	 * Pasa un movimiento a una lista de objetos
	 * 
	 * @param mov
	 * @return
	 * @throws Exception
	 */
	public Object[] getMovimientoFiltrado(MovimientoEntradaSalida mov) throws Exception {
		Object[] out = null;
		ArrayList<Object> lis = new ArrayList<>();

		for (int i = 0; i < datosCols.length; i++) {
			String nCol = datosCols[i][0];
			boolean b = (boolean) this.getM().getValue(this, nCol);
			if (b == true) {
				String vStr = "";
				if (nCol.compareTo(DETALLES) == 0) {
					vStr = mov.getDetallesString();
				} else {
					Object valor = this.getM().getValue(mov, nCol);
					vStr = this.getFormato(datosCols[i][3], valor);
				}

				lis.add(vStr);
			}
		}
		out = lis.toArray();
		return out;
	}

	private String getFormato(String ff, Object dato) {
		String out = "";
		try {
			if (ff.compareTo(DATE) == 0) {
				out = this.getM().dateToString((Date) dato, "MMM dd");
			} else if (ff.compareTo(NUMERO) == 0) {
				out = this.getM().formatoGs((double) dato);
			} else {
				// otro
				out = dato.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return out;

	}

	
	public void limpiarFiltro(){
		this.filTipoMovimiento = "";
		this.filLugarOrigen = "";
		this.filLugarDestino = "";
		this.filRemito = "";
		this.filRemision = "";
		this.filChapa = "";
		this.filChapaCarreta = "";
		this.filChofer = "";
		this.filTransportadora = "";
		this.filDespacho = "";
		this.filDespachante = "";
		this.filtroFechaLlegadaDesde = null;
		this.filtroFechaLlegadaHasta = null;
		this.filtroFechaSalidaDesde = null;
		this.filtroFechaSalidaHasta = null;
	}
	
	
	public String getFiltroStr(){
		String out = "";
		out += this.getFiltroCampo(" Tipo", this.filTipoMovimiento);
		out += this.getFiltroCampo(" Origen", this.filLugarOrigen);
		out += this.getFiltroCampo(" Destino", this.filLugarDestino);

		out += this.getFiltroCampo(" Remito", this.filRemito);
		out += this.getFiltroCampo(" Remisión", this.filRemision);
		out += this.getFiltroCampo(" Chapa", this.filChapa);
		out += this.getFiltroCampo(" Chapa Carreta", this.filChapaCarreta);
		out += this.getFiltroCampo(" Destino", this.filChofer);
		out += this.getFiltroCampo(" Destino", this.filTransportadora);
		out += this.getFiltroCampo(" Destino", this.filDespacho );
		out += this.getFiltroCampo(" Destino", this.filDespachante);
		out += this.getFiltroFecha(" Llegada",this.filtroFechaLlegadaDesde, this.filtroFechaLlegadaHasta);
		out += this.getFiltroFecha(" Salida",this.filtroFechaSalidaDesde, this.filtroFechaSalidaHasta);

		return out.trim();
	}

	private String getFiltroFecha(String campo, Date desde, Date hasta){
		boolean siFecha = false;
		String out = campo + " [";
		if (desde!= null){
			out += this.getM().dateToString(desde, "dd-MM-yyyy");
			siFecha = true;
		}else{
			out += "-";
		}
		out += " / ";
		if (hasta!= null){
			out += this.getM().dateToString(hasta, "dd-MM-yyyy");
			siFecha = true;
		}else{
			out += "-";
		}
		out += "]";
		
		if (siFecha == false){
			out = "";
		}
		
		return out;
	}

	
	private String getFiltroCampo(String campo, String filtro){
		String out = "";
		if (filtro.trim().length() > 0){
			out = campo + ": '"+filtro.trim()+"'";
		}
		return out;
	}
	
	// =====================================
	// =====================================
	// =====================================
	public static void xmain(String[] args) throws Exception {
		FiltroMovimiento fm = new FiltroMovimiento();
		fm.setBruto(false);
		fm.setChapa(false);
		fm.setChofer(false);

		String[][] ss = fm.getColTablas();
		System.out.println(ss);
	}
	
	public static void main(String[] args) {
		Misc m = new Misc();
		FiltroMovimiento fm = new FiltroMovimiento();
		fm.setBruto(false);
		fm.setChapa(false);
		fm.setChofer(false);
		fm.setFilChapa("chapaaa");
		fm.setFilDespachante("pep");
		
		String file = "/home/daniel/datos/afah/proyectos/bascula/gestion-bascula/WebContent/filtroReporte-xxxxx";
		m.grabarObjectToArchvo(fm, file);
		
		
		FiltroMovimiento out = (FiltroMovimiento) m.leerObjetoFromDisco(file);
		System.out.println(out.getFilChapa());
		
		System.out.println("===================");
		System.out.println(out.getFiltroStr());
		System.out.println("===================");
		
		
		
	}
	
	// =====================================
	// =====================================
	// =====================================

	public boolean isTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(boolean tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public boolean isFechaLlegada() {
		return fechaLlegada;
	}

	public void setFechaLlegada(boolean fechaLlegada) {
		this.fechaLlegada = fechaLlegada;
	}

	public boolean isFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(boolean fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public boolean isBruto() {
		return bruto;
	}

	public void setBruto(boolean bruto) {
		this.bruto = bruto;
	}

	public boolean isTara() {
		return tara;
	}

	public void setTara(boolean tara) {
		this.tara = tara;
	}

	public boolean isNeto() {
		return neto;
	}

	public void setNeto(boolean neto) {
		this.neto = neto;
	}

	public boolean isOrigen() {
		return origen;
	}

	public void setOrigen(boolean origen) {
		this.origen = origen;
	}

	public boolean isDiferencia() {
		return diferencia;
	}

	public void setDiferencia(boolean diferencia) {
		this.diferencia = diferencia;
	}

	public boolean isOrigenLugar() {
		return origenLugar;
	}

	public void setOrigenLugar(boolean origenLugar) {
		this.origenLugar = origenLugar;
	}

	public boolean isDestinoLugar() {
		return destinoLugar;
	}

	public void setDestinoLugar(boolean destinoLugar) {
		this.destinoLugar = destinoLugar;
	}

	public boolean isRemito() {
		return remito;
	}

	public void setRemito(boolean remito) {
		this.remito = remito;
	}

	public boolean isRemision() {
		return remision;
	}

	public void setRemision(boolean remision) {
		this.remision = remision;
	}

	public boolean isChapa() {
		return chapa;
	}

	public void setChapa(boolean chapa) {
		this.chapa = chapa;
	}

	public boolean isChapaCarreta() {
		return chapaCarreta;
	}

	public void setChapaCarreta(boolean chapaCarreta) {
		this.chapaCarreta = chapaCarreta;
	}

	public boolean isChofer() {
		return chofer;
	}

	public void setChofer(boolean chofer) {
		this.chofer = chofer;
	}

	public boolean isTransportadora() {
		return transportadora;
	}

	public void setTransportadora(boolean transportadora) {
		this.transportadora = transportadora;
	}

	public boolean isDespacho() {
		return despacho;
	}

	public void setDespacho(boolean despacho) {
		this.despacho = despacho;
	}

	public boolean isDespachante() {
		return despachante;
	}

	public void setDespachante(boolean despachante) {
		this.despachante = despachante;
	}

	public boolean isDetalles() {
		return detalles;
	}

	public void setDetalles(boolean detalles) {
		this.detalles = detalles;
	}

	public String getTipoMovimientoInt() {
		return tipoMovimientoInt;
	}

	public void setTipoMovimientoInt(String tipoMovimientoInt) {
		this.tipoMovimientoInt = tipoMovimientoInt;
	}

	public String getFechaLlegadaInt() {
		return fechaLlegadaInt;
	}

	public void setFechaLlegadaInt(String fechaLlegadaInt) {
		this.fechaLlegadaInt = fechaLlegadaInt;
	}

	public String getFechaSalidaInt() {
		return fechaSalidaInt;
	}

	public void setFechaSalidaInt(String fechaSalidaInt) {
		this.fechaSalidaInt = fechaSalidaInt;
	}

	public String getBrutoInt() {
		return brutoInt;
	}

	public void setBrutoInt(String brutoInt) {
		this.brutoInt = brutoInt;
	}

	public String getTaraInt() {
		return taraInt;
	}

	public void setTaraInt(String taraInt) {
		this.taraInt = taraInt;
	}

	public String getNetoInt() {
		return netoInt;
	}

	public void setNetoInt(String netoInt) {
		this.netoInt = netoInt;
	}

	public String getOrigenInt() {
		return origenInt;
	}

	public void setOrigenInt(String origenInt) {
		this.origenInt = origenInt;
	}

	public String getDiferenciaInt() {
		return diferenciaInt;
	}

	public void setDiferenciaInt(String diferenciaInt) {
		this.diferenciaInt = diferenciaInt;
	}

	public String getOrigenLugarInt() {
		return origenLugarInt;
	}

	public void setOrigenLugarInt(String origenLugarInt) {
		this.origenLugarInt = origenLugarInt;
	}

	public String getDestinoLugarInt() {
		return destinoLugarInt;
	}

	public void setDestinoLugarInt(String destinoLugarInt) {
		this.destinoLugarInt = destinoLugarInt;
	}

	public String getRemitoInt() {
		return remitoInt;
	}

	public void setRemitoInt(String remitoInt) {
		this.remitoInt = remitoInt;
	}

	public String getRemisionInt() {
		return remisionInt;
	}

	public void setRemisionInt(String remisionInt) {
		this.remisionInt = remisionInt;
	}

	public String getChapaInt() {
		return chapaInt;
	}

	public void setChapaInt(String chapaInt) {
		this.chapaInt = chapaInt;
	}

	public String getChapaCarretaInt() {
		return chapaCarretaInt;
	}

	public void setChapaCarretaInt(String chapaCarretaInt) {
		this.chapaCarretaInt = chapaCarretaInt;
	}

	public String getChoferInt() {
		return choferInt;
	}

	public void setChoferInt(String choferInt) {
		this.choferInt = choferInt;
	}

	public String getTransportadoraInt() {
		return transportadoraInt;
	}

	public void setTransportadoraInt(String transportadoraInt) {
		this.transportadoraInt = transportadoraInt;
	}

	public String getDespachoInt() {
		return despachoInt;
	}

	public void setDespachoInt(String despachoInt) {
		this.despachoInt = despachoInt;
	}

	public String getDespachanteInt() {
		return despachanteInt;
	}

	public void setDespachanteInt(String despachanteInt) {
		this.despachanteInt = despachanteInt;
	}

	public String getDetallesInt() {
		return detallesInt;
	}

	public void setDetallesInt(String detallesInt) {
		this.detallesInt = detallesInt;
	}

	public String getFilTipoMovimiento() {
		return filTipoMovimiento;
	}

	public void setFilTipoMovimiento(String filTipoMovimiento) {
		this.filTipoMovimiento = filTipoMovimiento;
	}

	public String getFilLugarOrigen() {
		return filLugarOrigen;
	}

	public void setFilLugarOrigen(String filLugarOrigen) {
		this.filLugarOrigen = filLugarOrigen;
	}

	public String getFilLugarDestino() {
		return filLugarDestino;
	}

	public void setFilLugarDestino(String filLugarDestino) {
		this.filLugarDestino = filLugarDestino;
	}

	public String getFilRemito() {
		return filRemito;
	}

	public void setFilRemito(String filRemito) {
		this.filRemito = filRemito;
	}

	public String getFilRemision() {
		return filRemision;
	}

	public void setFilRemision(String filRemision) {
		this.filRemision = filRemision;
	}

	public String getFilChapa() {
		return filChapa;
	}

	public void setFilChapa(String filChapa) {
		this.filChapa = filChapa;
	}

	public String getFilChapaCarreta() {
		return filChapaCarreta;
	}

	public void setFilChapaCarreta(String filChapaCarreta) {
		this.filChapaCarreta = filChapaCarreta;
	}

	public String getFilChofer() {
		return filChofer;
	}

	public void setFilChofer(String filChofer) {
		this.filChofer = filChofer;
	}

	public String getFilTransportadora() {
		return filTransportadora;
	}

	public void setFilTransportadora(String filTransportadora) {
		this.filTransportadora = filTransportadora;
	}

	public String getFilDespacho() {
		return filDespacho;
	}

	public void setFilDespacho(String filDespacho) {
		this.filDespacho = filDespacho;
	}

	public String getFilDespachante() {
		return filDespachante;
	}

	public void setFilDespachante(String filDespachante) {
		this.filDespachante = filDespachante;
	}

	public Date getFiltroFechaLlegadaDesde() {
		return filtroFechaLlegadaDesde;
	}

	public void setFiltroFechaLlegadaDesde(Date filtroFechaLlegadaDesde) {
		this.filtroFechaLlegadaDesde = filtroFechaLlegadaDesde;
	}

	public Date getFiltroFechaLlegadaHasta() {
		return filtroFechaLlegadaHasta;
	}

	public void setFiltroFechaLlegadaHasta(Date filtroFechaLlegadaHasta) {
		this.filtroFechaLlegadaHasta = filtroFechaLlegadaHasta;
	}

	public Date getFiltroFechaSalidaDesde() {
		return filtroFechaSalidaDesde;
	}

	public void setFiltroFechaSalidaDesde(Date filtroFechaSalidaDesde) {
		this.filtroFechaSalidaDesde = filtroFechaSalidaDesde;
	}

	public Date getFiltroFechaSalidaHasta() {
		return filtroFechaSalidaHasta;
	}

	public void setFiltroFechaSalidaHasta(Date filtroFechaSalidaHasta) {
		this.filtroFechaSalidaHasta = filtroFechaSalidaHasta;
	}
	
	
	

}