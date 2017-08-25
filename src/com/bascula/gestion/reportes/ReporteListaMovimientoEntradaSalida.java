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

		
		String s1 = this.m.getString(movimiento.getTipoMovimiento(), "descripcion");
		dat.add(this.textoParValor("Movimiento ", s1));		
		
		dat.add(this.textoParValor("Llegada", this.m.dateToString(
				this.movimiento.getFechaLlegada(), this.m.DD_MM_YYYY)));

		dat.add(this.textoParValor("Salida", this.m.dateToString(
				this.movimiento.getFechaSalida(), this.m.DD_MM_YYYY)));

		HorizontalListBuilder dato1 = cmp.horizontalList();

		
		String s2 = this.m.getString(movimiento.getOrigenLugar(), "strCampo1");
		dato1.add(this.textoParValor("Lugar de Origen", s2));
		
		
		String s3 = this.m.getString(movimiento.getDestinoLugar(), "strCampo1");
		dato1.add(this.textoParValor("Lugar de Destino ", s3));

		
		dato1.add(this.textoParValor("Remito ", movimiento.getRemito()));

		dato1.add(this.textoParValor("Remisión ", movimiento.getRemision()));

		HorizontalListBuilder datosCamion = cmp.horizontalList();
		datosCamion.add(this.textoNegrita("Datos del Camión"));

		HorizontalListBuilder dato2 = cmp.horizontalList();

		String s4 = this.m.getString(movimiento.getChapa(), "strCampo1");
		dato2.add(this.textoParValorVertical("Chapa", s4));

		String s5 = this.m.getString(movimiento.getChapaCarreta(), "strCampo1");
		dato2.add(this.textoParValorVertical("Chapa carreta ", s5));

		String s6 = this.m.getString(movimiento.getChofer(), "strCampo1");
		dato2.add(this.textoParValorVertical("Chofer ", s6));

		String s7 = this.m.getString(movimiento.getTransportadora(), "strCampo1");
		dato2.add(this.textoParValorVertical("Transportadora", s7));

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

		String s8 = this.m.getString(movimiento.getDespachante(), "strCampo1");
		dato4.add(this.textoParValorVertical("Despachante ",s8));


		VerticalListBuilder dato5 = cmp.verticalList();

		dato5.add(this.textoNegrita("Mercadería"));
		dato5.add(this.textoMonoSpace(movimiento.getDetallesString()));

		
		
		
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

		out.add(espacioAlto(5));
		out.add(cmp.line());
		out.add(dato5);

		return out;

	}

	public static void main(String[] args) throws Exception {
		ReporteListaMovimientoEntradaSalida reporte = new ReporteListaMovimientoEntradaSalida();
		RegisterDomain rr = RegisterDomain.getInstance();

		MovimientoEntradaSalida dato = (MovimientoEntradaSalida) rr.getObject(
				MovimientoEntradaSalida.class.getName(), 1);
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
