package com.bascula.gestion.reportes;

import java.util.ArrayList;
import java.util.List;

import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.VerticalListBuilder;

import com.bascula.util.population.reporte.BasculaReporte;

public class ReporteMovimiento extends BasculaReporte {

	private String filtro = "";

	String[][] cols = null;
	List<Object[]> listaMovimiento = null;

	public List<Object[]> getListaMovimiento() {
		return listaMovimiento;
	}

	public void setListaMovimiento(List<Object[]> listaMovimiento) {
		this.listaMovimiento = listaMovimiento;
	}

	@Override
	public void informacionReporte() {
		this.setTitulo("Movimientos");

		VerticalListBuilder out = cmp.verticalList();
		for (int i = 0; i < 1; i++) {
			out.add(cabecera());
			out.add(datosDetalle());
		}
		this.setBody(out);
	}

	private ComponentBuilder cabecera() {

		VerticalListBuilder out = cmp.verticalList();
		VerticalListBuilder filtro = cmp.verticalList();
		filtro.add(this.textoParValor("Filtro", this.filtro));

		out.add(filtro);

		return out;
	}

	private ComponentBuilder datosDetalle() {
		VerticalListBuilder out = cmp.verticalList();
		out.add(this.espacioAlto(20));
		String tablaMovim = LETRA_8 + TABLA_TITULO + "";

		if (this.cols.length > 6) {
			this.setApaisada();
		}

		ComponentBuilder mov = this.getTabla(this.cols, this.listaMovimiento, tablaMovim, true, false, false);

		out.add(mov);
		out.add(this.espacioAlto(20));
		return out;

	}

	public String[][] getCols() {
		return cols;
	}

	public void setCols(String[][] cols) {
		this.cols = cols;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	// ================================================================
	public static List<Object[]> getMovimientosPrueba(int nCol) {
		List<Object[]> movimientos = new ArrayList<Object[]>();

		for (int i = 0; i < nCol; i++) {

			Object[] fila = new String[7];
			for (int j = 0; j < 7; j++) {
				fila[j] = "fil:" + i + " col:" + j;
			}
			movimientos.add(fila);
		}

		return movimientos;

	}

	// ================================================================

	public static void main(String[] args) {
		ReporteMovimiento reporte = new ReporteMovimiento();

		String[][] cols = { { "Col1", WIDTH + "65" }, { "Col2", WIDTH + "65" }, { "Col3", WIDTH + "65" },
				{ "Col4", WIDTH + "65" }, { "Col7", WIDTH + "65" }, };

		reporte.setFiltro("123");
		reporte.setCols(cols);
		reporte.setListaMovimiento(getMovimientosPrueba(20));
		reporte.ejecutar(true);
	}
}
