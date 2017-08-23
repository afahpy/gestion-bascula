package com.bascula.gestion.reportes;

import java.util.ArrayList;
import java.util.List;

import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.VerticalListBuilder;

import com.bascula.util.population.reporte.BasculaReporte;

public class ReporteMovimiento extends BasculaReporte {
	
	private String filtro = ""; 

	String[][] cols = { { "", WIDTH + "65" },
			{ "", WIDTH + "65" },
			{ "", WIDTH + "65" },
			{ "", WIDTH + "65" },
			{ "", WIDTH + "65" },
			{ "", WIDTH + "65" },
			{ "", WIDTH + "65" },};
	
	
	
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

		
	
private ComponentBuilder cabecera(){
	
	VerticalListBuilder out = cmp.verticalList();
	 VerticalListBuilder filtro = cmp.verticalList();
	 filtro.add(this.textoParValor("Filtro", this.filtro));

	 
	 out.add(filtro);
	
	return out;
}

	
	private ComponentBuilder datosDetalle()  {
		VerticalListBuilder out = cmp.verticalList();
		
		
		// tabla Movimiento
		
				List<Object[]> listaMovimientos = this.getMovimientos();
				out.add(this.espacioAlto(20));
				String tablaMovim = LETRA_8 + TABLA_TITULO + "";

			
				ComponentBuilder mov = this.getTabla(cols, listaMovimientos,
						tablaMovim, true, false, false);
				out.add(mov);
				out.add(this.espacioAlto(20));
		return out;
		
		
		
	
	}
	
	public List<Object[]> getMovimientos()  {
		List<Object[]>movimientos = new ArrayList<Object[]>();;
		
		
		for (int i = 0; i < 5; i++) {
			
			Object[] fila = new String[7];
			for (int j = 0; j < 7; j++) {
				fila[j] = "i:"+i+" j:"+j;
			}
			movimientos.add(fila);
		}
			
		
		return movimientos ;
	
	}
	
	public static void main(String[] args) {
		ReporteMovimiento reporte = new ReporteMovimiento();
		
		reporte.setFiltro("123");
		reporte.ejecutar(true);
	}





	public String getFiltro() {
		return filtro;
	}





	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}
	
	
	
}
