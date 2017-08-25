package com.bascula.gestion;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
import com.coreweb.Config;
import com.coreweb.domain.Tipo;
import com.coreweb.extras.reporte.ViewPdf;
import com.sun.mail.iap.ByteArray;

public class MovimientoVMReporte extends GenericViewModelApp {

	static String KEY_FITRO_REPORTE = "filtroReporte";

	FiltroMovimiento fm = null;
	ListaMovimientosViewModel vmMov;

	@Init(superclass = true)
	public void initMovimientoVMReporte(@ExecutionArgParam("vmMov") ListaMovimientosViewModel vmMov) {
		this.vmMov = vmMov;
		this.fm = this.getFiltro(KEY_FITRO_REPORTE);

	}

	@AfterCompose(superclass = true)
	public void afterComposeMovimientoVMReporte() {

	}

	@Command
	public void reporteMovimiento() throws Exception {

		this.grabaFiltro(this.fm, KEY_FITRO_REPORTE);

		// recorre los movimientos
		ArrayList<Object[]> datos = new ArrayList<>();
		for (int i = 0; i < this.vmMov.getMovimientos().size(); i++) {
			MovimientoEntradaSalida mov = this.vmMov.getMovimientos().get(i);
			Object[] dato = this.fm.getMovimientoFiltrado(mov);
			datos.add(dato);
		}
		// filtra el total
		MovimientoEntradaSalida movTotal = this.vmMov.getMovTempSumas();
		Object[] datoTotal = this.fm.getMovimientoFiltrado(movTotal);
		datos.add(datoTotal);

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
