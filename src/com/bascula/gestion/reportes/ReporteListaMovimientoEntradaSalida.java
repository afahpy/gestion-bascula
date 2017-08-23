package com.bascula.gestion.reportes;

import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.component.VerticalListBuilder;

import com.bascula.domain.MovimientoEntradaSalida;
import com.bascula.domain.RegisterDomain;
import com.bascula.util.population.reporte.BasculaReporte;
import com.coreweb.util.Misc;

public class ReporteListaMovimientoEntradaSalida extends BasculaReporte {

	private MovimientoEntradaSalida movimiento = new MovimientoEntradaSalida();
	private Misc m = new Misc();

	@Override
	public void informacionReporte() {
		this.setTitulo("Reporte Movimiento");
		this.setBody(this.datosCabecera());
	}

	private ComponentBuilder datosCabecera() {

		VerticalListBuilder out = cmp.verticalList();

		VerticalListBuilder dato = cmp.verticalList();

		HorizontalListBuilder dat = cmp.horizontalList();

		dat.add(this.textoParValor("Movimiento ", movimiento
				.getTipoMovimiento().getDescripcion()));

		dat.add(this.textoParValor("Llegada", this.m.dateToString(
				this.movimiento.getFechaLlegada(), this.m.DD_MM_YYYY)));

		dat.add(this.textoParValor("Salida", this.m.dateToString(
				this.movimiento.getFechaSalida(), this.m.DD_MM_YYYY)));

		HorizontalListBuilder dato1 = cmp.horizontalList();

		dato1.add(this.textoParValor("Lugar de Origen", movimiento
				.getOrigenLugar().getStrCampo1()));

		dato1.add(this.textoParValor("Lugar de Destino ", movimiento
				.getDestinoLugar().getStrCampo1()));
		
		dato1.add(this.textoParValor("Remito ", movimiento.getRemito()));

		dato1.add(this.textoParValor("Remisión ", movimiento.getRemision()));

		HorizontalListBuilder datosCamion = cmp.horizontalList();
		datosCamion.add(this.textoNegrita("Datos del Camión"));

		HorizontalListBuilder dato2 = cmp.horizontalList();

		dato2.add(this.textoParValorVertical("Chapa", movimiento.getChapa()
				.getStrCampo1()));

		dato2.add(this.textoParValorVertical("Chapa carreta ",
				movimiento.getChapaCarreta()));

		dato2.add(this.textoParValorVertical("Chofer ", movimiento.getChofer()
				.getStrCampo1()));

		dato2.add(this.textoParValorVertical("Transportadora", movimiento
				.getTransportadora().getStrCampo1()));

		HorizontalListBuilder dato3 = cmp.horizontalList();

		dato3.add(this.textoParValorVertical("Bruto",
				this.m.formatoGs(this.movimiento.getBruto())));

		dato3.add(this.textoParValorVertical("Tara ",
				this.m.formatoGs(this.movimiento.getTara())));

		dato3.add(this.textoParValorVertical("Neto ",
				this.m.formatoGs(this.movimiento.getNeto())));

		dato3.add(this.textoParValorVertical("Origen ",
				this.m.formatoGs(this.movimiento.getOrigen())));

		dato3.add(this.textoParValorVertical("Diferencia ",
				this.m.formatoGs(this.movimiento.getDiferencia())));

		HorizontalListBuilder dato4 = cmp.horizontalList();

		dato4.add(this.textoParValorVertical("Despacho ",
				movimiento.getDespacho()));

		dato4.add(this.textoParValorVertical("Despachante ",
				movimiento.getDespachante()));

		out.add(espacioAlto(20));
		out.add(dat);
		
		out.add(dato);
		out.add(espacioAlto(5));
		
		out.add(dato1);
		
		out.add(espacioAlto(5));
		out.add(cmp.line());
		out.add(datosCamion);
		
		out.add(dato2);
		out.add(espacioAlto(5));
		out.add(cmp.line());
		
		out.add(dato3);
		
		out.add(espacioAlto(5));
		out.add(cmp.line());
		out.add(dato4);
		return out;

	}

	public static void main(String[] args) throws Exception {
		ReporteListaMovimientoEntradaSalida reporte = new ReporteListaMovimientoEntradaSalida();
		RegisterDomain rr = RegisterDomain.getInstance();

		MovimientoEntradaSalida dato = (MovimientoEntradaSalida) rr.getObject(
				MovimientoEntradaSalida.class.getName(), 3);
		reporte.setMovimiento(dato);
		reporte.ejecutar(true);
	}

	/******** GET Y SET *******/

	public MovimientoEntradaSalida getMovimiento() {
		return movimiento;
	}

	public void setMovimiento(MovimientoEntradaSalida movimiento) {
		this.movimiento = movimiento;
	}

}
