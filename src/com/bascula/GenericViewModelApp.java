package com.bascula;

import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Init;

import com.bascula.domain.RegisterDomain;
import com.bascula.gestion.FiltroMovimiento;
import com.coreweb.Config;
import com.coreweb.control.GenericViewModel;
import com.coreweb.domain.Tipo;
import com.coreweb.util.MyPair;
import com.bascula.Configuracion;
import com.bascula.UtilDTO;

public class GenericViewModelApp extends GenericViewModel {

	protected RegisterDomain rr = RegisterDomain.getInstance();

	public UtilDTO getUtilDto() {
		return (UtilDTO) this.getDtoUtil();
	}

	@Override
	public boolean getCondicionComponenteSiempreHabilitado() {
		// TODO Auto-generated method stub
		return false;
	}

	public RegisterDomain getRr() {
		return rr;
	}

	public void setRr(RegisterDomain rr) {
		this.rr = rr;
	}

	
	public  FiltroMovimiento getFiltro(String key) {

		FiltroMovimiento out = (FiltroMovimiento) this.m.leerObjetoFromDisco(this.getFileFilter(key));
		if (out == null) {
			out = new FiltroMovimiento();
		}
		return out;
	}

	public void grabaFiltro(FiltroMovimiento afm, String key) {
		this.m.grabarObjectToArchvo(afm, this.getFileFilter(key));
	}
	
	private String getFileFilter(String key) {
		String out = Config.DIRECTORIO_BASE_REAL + "/" + key +"-"+ this.getLoginNombre();
		return out;
	}

	
}
