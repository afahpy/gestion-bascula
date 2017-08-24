package com.bascula.gestion.reportes;

import java.util.ArrayList;
import java.util.List;

import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.VerticalListBuilder;

import com.bascula.util.population.reporte.BasculaReporte;

public class ReporteMovimiento extends BasculaReporte {

	private String filtro = "";

<<<<<<< HEAD
	String[][] cols = null;
	List<Object[]> listaMovimiento = null;

	public List<Object[]> getListaMovimiento() {
		return listaMovimiento;
	}

	public void setListaMovimiento(List<Object[]> listaMovimiento) {
		this.listaMovimiento = listaMovimiento;
	}
=======
	String[][] cols = { { "", WIDTH + "65" }, { "", WIDTH + "65" }, { "", WIDTH + "65" }, { "", WIDTH + "65" },
			{ "", WIDTH + "65" }, { "", WIDTH + "65" }, { "", WIDTH + "65" }, };
>>>>>>> eade36af70e190ad323bcb33cf0a43fe3b68c077

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
<<<<<<< HEAD
		filtro.add(this.textoParValor("Filtro", this.filtro));
=======
		filtro.add(this.textoParValor("Filtro", ""));
>>>>>>> eade36af70e190ad323bcb33cf0a43fe3b68c077

		out.add(filtro);

		return out;
	}

	private ComponentBuilder datosDetalle() {
		VerticalListBuilder out = cmp.verticalList();
<<<<<<< HEAD

		out.add(this.espacioAlto(20));
		String tablaMovim = LETRA_8 + TABLA_TITULO + "";

		ComponentBuilder mov = this.getTabla(this.cols, this.listaMovimiento,
				tablaMovim, true, false, false);
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
=======

		// tabla Movimiento

		List<Object[]> listaMovimientos = this.getMovimientos();
		out.add(this.espacioAlto(20));
		String tablaMovim = LETRA_8 + TABLA_TITULO + "";

		ComponentBuilder mov = this.getTabla(cols, listaMovimientos, tablaMovim, true, false, false);
		out.add(mov);
		out.add(this.espacioAlto(20));
		return out;
>>>>>>> eade36af70e190ad323bcb33cf0a43fe3b68c077

	}

<<<<<<< HEAD
	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}
	
	//================================================================
	public static List<Object[]> getMovimientosPrueba(int nCol) {
		List<Object[]> movimientos = new ArrayList<Object[]>();
		;

		for (int i = 0; i < nCol; i++) {

			Object[] fila = new String[7];
			for (int j = 0; j < 7; j++) {
				fila[j] = "fil:" + i + " col:" + j;
			}
			movimientos.add(fila);
		}

		return movimientos;

	}
	//================================================================

	public static void main(String[] args) {
		ReporteMovimiento reporte = new ReporteMovimiento();
=======
	public List<Object[]> getMovimientos() {
		List<Object[]> movimientos = new ArrayList<Object[]>();
		return movimientos;

	}

	public static void main(String[] args) {
		ReporteMovimiento reporte = new ReporteMovimiento();

		reporte.ejecutar(true);
	}

	public String getFiltro() {
		return filtro;
	}
>>>>>>> eade36af70e190ad323bcb33cf0a43fe3b68c077

		String[][] cols = { { "Col1", WIDTH + "65" }, { "Col2", WIDTH + "65" },
				{ "Col3", WIDTH + "65" }, { "Col4", WIDTH + "65" },
				{ "Col5", WIDTH + "65" }, { "Col6", WIDTH + "65" },
				{ "Col7", WIDTH + "65" }, };

		reporte.setFiltro("123");
		reporte.setCols(cols);
		reporte.setListaMovimiento(getMovimientosPrueba(cols.length));
		reporte.ejecutar(true);
	}

<<<<<<< HEAD

=======
>>>>>>> eade36af70e190ad323bcb33cf0a43fe3b68c077
}
