package com.bascula.gestion;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;

import com.bascula.GenericViewModelApp;
import com.bascula.domain.MovimientoDetalle;
import com.bascula.domain.MovimientoEntradaSalida;
import com.bascula.domain.MyObject;
import com.bascula.gestion.reportes.ReporteMovimiento;
import com.coreweb.domain.Tipo;
import com.coreweb.extras.reporte.ViewPdf;

public class MovimientoVMReporte extends GenericViewModelApp{

	FiltroMovimiento fm = new FiltroMovimiento();
	ListaMovimientosViewModel vmMov;
	
	@Init(superclass = true)
	public void initMovimientoVMReporte(@ExecutionArgParam("vmMov") ListaMovimientosViewModel vmMov) {
		this.vmMov = vmMov;
	}

	@AfterCompose(superclass = true)
	public void afterComposeMovimientoVMReporte() {

	}

	
	@Command
	public void reporteMovimiento() throws Exception{
		
		ArrayList<Object[]> datos = new ArrayList<>();
		for (int i = 0; i < this.vmMov.getMovimientos().size(); i++) {
			MovimientoEntradaSalida mov = this.vmMov.getMovimientos().get(i);
			Object[] dato = this.fm.getMovimientoFiltrado(mov);
			datos.add(dato);
		}
		
		ReporteMovimiento rep = new ReporteMovimiento();
		rep.setCols(this.fm.getColTablas());
		rep.setListaMovimiento(datos);
		rep.setFiltro(" x x x x x x`");

		ViewPdf vp = new ViewPdf();
		vp.showReporte(rep, this);
		
		
	}
	
	
	public FiltroMovimiento getFm() {
		return fm;
	}

	public void setFm(FiltroMovimiento fm) {
		this.fm = fm;
	}

}

