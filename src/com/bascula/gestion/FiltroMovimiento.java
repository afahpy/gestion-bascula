package com.bascula.gestion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.bascula.domain.MovimientoEntradaSalida;
import com.coreweb.extras.reporte.ReporteDefinicion;
import com.coreweb.util.Misc;

public class FiltroMovimiento implements Serializable{

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

	static String DETALLES = "detalles";
	
	static String[][] datosCols = { 
			{ "tipoMovimiento", "E/S", ReporteDefinicion.WIDTH + "65", OTRO },
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
			{ "bruto", "Kg.Bruto", ReporteDefinicion.WIDTH + "65", NUMERO },
			{ "tara", "Kg.Tara", ReporteDefinicion.WIDTH + "65", NUMERO },
			{ "neto", "Kg.Neto", ReporteDefinicion.WIDTH + "65", NUMERO },
			{ "origen", "Kg.Origen", ReporteDefinicion.WIDTH + "65", NUMERO },
			{ "diferencia", "Kg.Dif.", ReporteDefinicion.WIDTH + "65", NUMERO },
			{ "despacho", "Despacho", ReporteDefinicion.WIDTH + "65", OTRO },
			{ "despachante", "Despachante", ReporteDefinicion.WIDTH + "65", OTRO },
			{ DETALLES, "Detalles", ReporteDefinicion.WIDTH + "65", OTRO },

	};

	
	private Misc getM(){
		if (this.m == null){
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
				if (nCol.compareTo(DETALLES)==0){
					vStr = mov.getDetallesString();
				}else{
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

	// =====================================
	// =====================================
	// =====================================
	public static void main(String[] args) throws Exception {
		FiltroMovimiento fm = new FiltroMovimiento();
		fm.setBruto(false);
		fm.setChapa(false);
		fm.setChofer(false);

		String[][] ss = fm.getColTablas();
		System.out.println(ss);
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
		System.out.println("================================");
		System.out.println("isTara: "+tara);
		System.out.println("================================");
		return tara;
	}

	public void setTara(boolean tara) {
		System.out.println("================================");
		System.out.println("setTara antes: "+tara);
		this.tara = tara;
		System.out.println("setTara despues: "+tara);
		System.out.println("================================");
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

}