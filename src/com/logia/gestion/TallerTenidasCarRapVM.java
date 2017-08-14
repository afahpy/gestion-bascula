package com.logia.gestion;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Iframe;

import com.coreweb.Config;
import com.coreweb.domain.Tipo;
import com.coreweb.util.MyArray;
import com.coreweb.util.MyPair;
import com.logia.Configuracion;
import com.logia.GenericViewModelApp;
import com.logia.domain.Mason;
import com.logia.domain.RegisterDomain;
import com.logia.domain.Tenida;
import com.logia.domain.TenidaAsistencia;

public class TallerTenidasCarRapVM extends GenericViewModelApp {

	RegisterDomain rr = RegisterDomain.getInstance();
	private Tenida tenida;
	private String mensajeErr = "";

	private List<MyArray> asistencias = new ArrayList<MyArray>();

	@Init(superclass = true)
	public void initTallerTenidasCarRapVM(@ExecutionArgParam("TENIDA") Tenida tenida) throws Exception {
		this.tenida = tenida;

		// Carga la lista de asistencias para carga rapida
		this.poblarLista();
		
	}

	@AfterCompose(superclass = true)
	public void afterComposeTallerTenidasCarRapVM() {
	}

	@Command
	public void cambiarPantalla(@BindingParam("url") String url) {
		this.saltoDePagina(url);
	}

	public boolean isEditable() throws Exception {
		boolean out = false;

		if (this.operacionHabilitada(Configuracion.ALIAS_PERM_EDITAR_TENIDAS)) {
			out = true;
		}
		return out;
	}

	public Tenida getTenida() {
		return tenida;
	}

	public void setTenida(Tenida tenida) {
		this.tenida = tenida;
	}

	public List<MyArray> getAsistencias() {
		return asistencias;
	}

	public void setAsistencias(List<MyArray> asistencias) {
		this.asistencias = asistencias;
	}

	public void poblarLista() throws Exception {

		List<MyArray> listaTenidasAux = new ArrayList<MyArray>();

		List<Mason> todosMasones = this.rr.getMasones();

		// Puede ser al revez tambien
		for (Mason m : todosMasones) {

			boolean yaExisteAsistencia = false;
			TenidaAsistencia copiaAsistencia = null;

			for (TenidaAsistencia asistencia : this.tenida.getAsistencias()) {

				if (asistencia.getMason() != null
						&& m.getId().longValue() == asistencia.getMason().getId().longValue()) {
					yaExisteAsistencia = true;
					copiaAsistencia = asistencia;
				}

			} // Fin for asistencias previas.

			if (yaExisteAsistencia == true) {
				MyArray asistenciaMyArray = new MyArray();
				asistenciaMyArray.setPos1(true);// Para que este seleccionado
				asistenciaMyArray.setPos2(copiaAsistencia);

				listaTenidasAux.add(asistenciaMyArray);

			} else {
				TenidaAsistencia nuevaAsistencia = new TenidaAsistencia();
				nuevaAsistencia.setMason(m);
				nuevaAsistencia.setCargo(m.getCargo());
				nuevaAsistencia.setNombreH(m.getNombre());

				MyArray asistenciaMyArray = new MyArray();
				asistenciaMyArray.setPos1(true);// Para que este seleccionado
				asistenciaMyArray.setPos2(nuevaAsistencia);
				listaTenidasAux.add(asistenciaMyArray);

			}

		} // Fin for masones

		System.out.println("Cantidad de elementos en asistencias  " + listaTenidasAux.size());
		this.setAsistencias(listaTenidasAux);

	}

	@Command
	@NotifyChange("*")
	public void guardarAsistencia() throws Exception {

		if (validarAsistencia() == false) {
			this.mensajeError(this.mensajeErr);
			return;
		}

		// Primero guardar las asistencias de forma individual.
		for (MyArray asistencia : this.asistencias) {

			boolean selected = (boolean) asistencia.getPos1();

			if (selected == true) {
				rr.saveObject((TenidaAsistencia) asistencia.getPos2(), this.getUs().getLogin());
			}

		}

		List<TenidaAsistencia> aEliminar = new ArrayList<TenidaAsistencia>();

		// Una vez guardado actualizar la lista de tenidas.
		for (Iterator<TenidaAsistencia> i = this.tenida.getAsistencias().iterator(); i.hasNext();) {
			TenidaAsistencia element = i.next();
			if (element.getMason() != null) {
				aEliminar.add(element);
				i.remove();
			}
		}

		// Elimino de la BD las tenidas asistencias que ya no estan asociadas.
		for (TenidaAsistencia eliminar : aEliminar) {
			rr.deleteObject(eliminar);
		}

		// Agregamos a la lista de tenidas las nuevas tenidas ya guardaddas.

		for (MyArray asist : this.asistencias) {
			boolean selected = (boolean) asist.getPos1();
			if (selected == true) {
				this.tenida.getAsistencias().add((TenidaAsistencia) asist.getPos2());
			}
		}

		rr.saveObject(tenida, this.getUs().getLogin());

		this.mensajePopupTemporal("Guardado correctamente...");

	}

	public boolean validarAsistencia() {
		boolean out = true;
		this.mensajeErr = "";

		Hashtable<String, TenidaAsistencia> hashAux = new Hashtable<String, TenidaAsistencia>();

		for (MyArray asistencia : asistencias) {

			boolean selected = (boolean) asistencia.getPos1();
			if (selected == true) {

				TenidaAsistencia tenidaAsistencia = (TenidaAsistencia) asistencia.getPos2();
				String siglaCargo = tenidaAsistencia.getCargo().getSigla();

				if (siglaCargo.compareTo(Configuracion.SIGLA_TIPO_CARGO_SIN_CARGO) != 0
						&& hashAux.containsKey(siglaCargo)) {

					out = false;
					this.mensajeErr += "- Error cargo para \"" + tenidaAsistencia.getNombreH() + "\", "
							+ tenidaAsistencia.getCargo().getDescripcion() + " ya esta asociado a ";
					this.mensajeErr += " \"" + hashAux.get(siglaCargo).getNombreH() + "\"\n";

				} else if (siglaCargo.compareTo(Configuracion.SIGLA_TIPO_CARGO_SIN_CARGO) != 0) {
					hashAux.put(siglaCargo, tenidaAsistencia);
				}
			}

		}

		return out;
	}

}
