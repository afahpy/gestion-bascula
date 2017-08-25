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

	FiltroMovimiento fm = null;
	ListaMovimientosViewModel vmMov;

	@Init(superclass = true)
	public void initMovimientoVMReporte(@ExecutionArgParam("vmMov") ListaMovimientosViewModel vmMov) {
		this.vmMov = vmMov;
		this.fm = this.getFiltro();
		
	}

	@AfterCompose(superclass = true)
	public void afterComposeMovimientoVMReporte() {

	}

	@Command
	public void reporteMovimiento() throws Exception {


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
		
		this.grabaFiltro(this.fm);

	}

	private FiltroMovimiento getFiltro() {
		FiltroMovimiento out = null;
		try {
			ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(this.getFileFilter()));
			byte[] ff = new byte[entrada.available()];
			entrada.readFully(ff);
			out = (FiltroMovimiento) this.m.deSerializar(ff);
			entrada.close();
		} catch (Exception e) {
			System.out.println("No pudo leer: " + e.getMessage());
			out = new FiltroMovimiento();
		}
		return out;
	}

	private void grabaFiltro(FiltroMovimiento afm) {
		try {
			
			byte[] ff = this.m.serializar(afm);
			ObjectOutputStream fil = new ObjectOutputStream(new FileOutputStream(this.getFileFilter()));
			fil.write(ff);
			fil.close();
		} catch (Exception e) {
			System.out.println("NO pudo grabar:"+e.getMessage());
		}
	}

	private String getFileFilter() {
		String out = Config.DIRECTORIO_BASE_REAL + "/filtroMovimiento-" + this.getLoginNombre();
		System.out.println("file:"+out);
		return out;
	}

	public FiltroMovimiento getFm() {
		return fm;
	}

	public void setFm(FiltroMovimiento fm) {
		this.fm = fm;
	}

	
	public static void main(String[] args) {
		FiltroMovimiento fm = new FiltroMovimiento();
		fm.setChapa(false);
		
		MovimientoVMReporte mm = new MovimientoVMReporte();
		mm.grabaFiltro(fm);
		
		FiltroMovimiento fm2 = mm.getFiltro();
		System.out.println("fm.isChapa():"+fm.isChapa());
		System.out.println("fm2.isChapa():"+fm2.isChapa());
		
	}
}
