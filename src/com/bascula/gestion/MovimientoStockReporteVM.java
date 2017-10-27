package com.bascula.gestion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;

import com.bascula.Configuracion;
import com.bascula.GenericViewModelApp;
import com.bascula.gestion.reportes.ReporteStock;
import com.coreweb.extras.reporte.ViewPdf;

public class MovimientoStockReporteVM  extends GenericViewModelApp {
	
	ListaMovimientosViewModel vmMov;

	Date fechaDesde = new Date();
	Date fechaHasta = new Date();
	
	@Init(superclass = true)
	public void initMovimientoStockReporteVM(@ExecutionArgParam("vmMov") ListaMovimientosViewModel vmMov) {
		this.vmMov = vmMov;

	}

	@AfterCompose(superclass = true)
	public void afterComposeMovimientoStockReporteVM() {

	}

	
	@Command
	public void reporteStock(){
	
		// leer los id de depósito
		long idOri = Long.parseLong( this.getSisProp().getPropiedad(Configuracion.SIS_PRO_UNISAL_PATIO_ORIGEN));
		long idDes = Long.parseLong( this.getSisProp().getPropiedad(Configuracion.SIS_PRO_UNISAL_PATIO_DESTINO));

		// los id de lugares
		String idsLugarStockBB = this.getSisProp().getPropiedad(Configuracion.SIS_PRO_UNISAL_STOCK_BIG_BAG);
		String[] idsLu = idsLugarStockBB.split(",");
		List<Long> lIdsLug= new ArrayList<>();
		for (int i = 0; i < idsLu.length; i++) {
			long idLug = Long.parseLong(idsLu[i].trim());
			lIdsLug.add(idLug);
		}
		
		
	
		// leer los id de productos
		String idsStr = this.getSisProp().getPropiedad(Configuracion.SIS_PRO_IDS_STOCK_SALES);
		String[] ids = idsStr.split(",");
		List<Long> lIds= new ArrayList<>();
		for (int i = 0; i < ids.length; i++) {
			long idSal = Long.parseLong(ids[i].trim());
			lIds.add(idSal);
		}
		
		ReporteStock rep = new ReporteStock();
		rep.setFd(this.fechaDesde);
		rep.setFh(this.fechaHasta);
		rep.setlIdLugares(lIdsLug);
		rep.setlIdProds(lIds);

		ViewPdf vp = new ViewPdf();
		vp.showReporte(rep, this);

	}
	
	
	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}


}
