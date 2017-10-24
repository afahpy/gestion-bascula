package com.bascula.gestion;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;

import com.bascula.GenericViewModelApp;

public class MovimientoStockReporteVM  extends GenericViewModelApp {
	
	static String KEY_FITRO_REPORTE_STOCK = "filtroReporteStock";

	FiltroMovimiento fm = null;
	ListaMovimientosViewModel vmMov;

	@Init(superclass = true)
	public void initMovimientoStockReporteVM(@ExecutionArgParam("vmMov") ListaMovimientosViewModel vmMov) {
		this.vmMov = vmMov;
		this.fm = this.getFiltro(KEY_FITRO_REPORTE_STOCK);

	}

	@AfterCompose(superclass = true)
	public void afterComposeMovimientoStockReporteVM() {

	}


}
