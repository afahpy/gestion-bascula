package com.logia;

import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Init;

import com.coreweb.control.GenericViewModel;
import com.coreweb.domain.Tipo;
import com.coreweb.util.MyPair;
import com.logia.domain.Mason;
import com.logia.domain.RegisterDomain;

public class GenericViewModelApp extends GenericViewModel {

	protected RegisterDomain rr = RegisterDomain.getInstance();

	/**
	 * Retorna el nivel del usuario correspondiente (MyPair)
	 * 
	 * @return
	 */
	public MyPair getGradoUsuarioCorriente() {
		MyPair gradoUsuario;
		gradoUsuario = (MyPair) this.getAtributoSession(Configuracion.SESSION_GRADO_MASON);
		return gradoUsuario;
	}

	public UtilDTO getUtilDto() {
		return (UtilDTO) this.getDtoUtil();
	}

	@Override
	public boolean getCondicionComponenteSiempreHabilitado() {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Mason> getMasones() throws Exception {
		return rr.getMasones();
	}

	public boolean isAprendizDisabled() {
		boolean out = true;
		MyPair m = this.getGradoUsuarioCorriente();
		if (m.getId().compareTo(this.getUtilDto().getAprendiz().getId()) == 0
				|| m.getId().compareTo(this.getUtilDto().getCompanero().getId()) == 0
				|| m.getId().compareTo(this.getUtilDto().getMaestro().getId()) == 0) {
			out = false;
		}
		return out;
	}

	public boolean isCompaneroDisabled() {
		boolean out = true;
		MyPair m = this.getGradoUsuarioCorriente();
		if (m.getId().compareTo(this.getUtilDto().getCompanero().getId()) == 0
				|| m.getId().compareTo(this.getUtilDto().getMaestro().getId()) == 0) {
			out = false;
		}
		return out;
	}

	public boolean isMaestroDisabled() {
		boolean out = true;
		MyPair m = this.getGradoUsuarioCorriente();
		if (m.getId().compareTo(this.getUtilDto().getMaestro().getId()) == 0) {
			out = false;
		}
		return out;
	}

	public boolean isUserAprendiz() {
		boolean out = false;
		MyPair m = this.getGradoUsuarioCorriente();
		if (m.getId().compareTo(this.getUtilDto().getAprendiz().getId()) == 0) {
			out = true;
		}
		return out;
	}

	public boolean isUserCompanero() {
		boolean out = false;
		MyPair m = this.getGradoUsuarioCorriente();
		if (m.getId().compareTo(this.getUtilDto().getCompanero().getId()) == 0) {
			out = true;
		}
		return out;
	}

	public boolean isUserMaestro() {
		boolean out = false;
		MyPair m = this.getGradoUsuarioCorriente();
		if (m.getId().compareTo(this.getUtilDto().getMaestro().getId()) == 0) {
			out = true;
		}
		return out;
	}

	public Tipo getPeriodoCorrienteTipo() {
		String periodoCorrienteString = this.getSisProp().getPropiedad(Configuracion.SIS_PRO_PERIODO_CORRIENTE);
		List<Tipo> periodos = this.getUtilDto().getPeriodos();
		Tipo periodoCorrienteTipo = null;

		for (Tipo periodo : periodos) {
			if (periodo.getDescripcion().compareTo(periodoCorrienteString) == 0) {
				periodoCorrienteTipo = periodo;
			}
		}

		return periodoCorrienteTipo;

	}

	public Tipo getMesActualTipo() {
		List<Tipo> meses = this.getUtilDto().getMeses();
		Tipo mesActualTipo = null;
		int mesActualint = (new Date()).getMonth() + 1;
		int mesObtenido = 0;

		for (Tipo mes : meses) {

			mesObtenido = Integer.parseInt(mes.getOrden());

			if (mesActualint == mesObtenido) {
				mesActualTipo = mes;
			}
		}

		return mesActualTipo;

	}

	public int getMesActualInt() {

		return (new Date()).getMonth() + 1;

	}

	public Tipo getMesTipoByInt(int mesInt) {

		List<Tipo> meses = this.getUtilDto().getMeses();
		Tipo mesActualTipo = null;
		int mesObtenido = 0;
		
		for (Tipo mes : meses) {

			mesObtenido = Integer.parseInt(mes.getOrden());

			if (mesInt == mesObtenido) {
				mesActualTipo = mes;
			}
		}

		return mesActualTipo;

	}

}
