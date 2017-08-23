package com.bascula.util.population.reporte;

import com.bascula.Configuracion;
import com.coreweb.Config;
import com.coreweb.extras.reporte.DatosReporte;
import com.coreweb.util.Misc;


public abstract class BasculaReporte extends DatosReporte{
	public Misc m = new Misc();

	@Override
	public void setDatosReportes() {

		System.out.println(">>>>>>>>>>>>>xxxxxxxxxxx"
				+ Config.DIRECTORIO_REAL_REPORTES);
		System.out.println(">>>>>>>>>>>>>xxxxxxxxxxx"
				+ Config.DIRECTORIO_WEB_REPORTES);
		String logo = Configuracion.DIRECTORIO_BASE_REAL + "/solo_logo.png";

		this.setEmpresa(Configuracion.empresa);
		this.setLogoEmpresa(logo, 30, 20);

		this.informacionReporte();
	}

	public abstract void informacionReporte();

}
