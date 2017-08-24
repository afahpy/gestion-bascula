package com.bascula.gestion;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

import com.bascula.GenericViewModelApp;
import com.bascula.domain.MyObject;
import com.coreweb.domain.Tipo;

public class DatosMyObjectVM extends GenericViewModelApp {

	Tipo selectedTipoMyObject = new Tipo();
	List<MyObject> listaMyObject = new ArrayList<>();
	MyObject selectedMyObject = new MyObject();
	MyObject nvoMyObject = new MyObject();

	@Init(superclass = true)
	public void initDatosMyObjectVM() {
		System.out.println("--------------------------init");
	}

	@AfterCompose(superclass = true)
	public void afterComposeDatosMyObjectVM() {
		System.out.println("--------------------------after");

	}

	@Command
	@NotifyChange("listaMyObject")
	public void refrecarListaMyObject() {
	}

	@Command
	@NotifyChange({"listaMyObject","nvoMyObject"})
	public void agregarMyObject() throws Exception {
		
		if (this.selectedTipoMyObject == null || this.selectedTipoMyObject.esNuevo() == true){
			this.mensajeError("Debe seleccionar un tipo de dato");
			return;
		}
		
		if (this.nvoMyObject.getStrCampo1().trim().length() == 0){
			this.mensajeError("Debe escribir la información");
			return;
		}
		
		
		String msg = "Confirma el agregar el siguiente dato?\n\n";
		msg += this.selectedTipoMyObject.getDescripcion()+"\n";
		msg += this.nvoMyObject.getStrCampo1()+"\n";
		
		if (this.mensajeSiNo(msg)==true){
			this.nvoMyObject.setStrCampo1Alias("nombre");
			this.nvoMyObject.setTipoObjeto(this.selectedTipoMyObject);
			rr.saveObject(this.nvoMyObject, this.getLoginNombre());
			this.nvoMyObject = new MyObject();
		}

	}

	public Tipo getSelectedTipoMyObject() {
		// if (selectedTipoMyObject == null){
		// selectedTipoMyObject = new Tipo();
		// }
		return selectedTipoMyObject;
	}

	public void setSelectedTipoMyObject(Tipo selectedTipoMyObject) {
		this.selectedTipoMyObject = selectedTipoMyObject;
	}

	public List<MyObject> getListaMyObject() throws Exception {
		System.out.println("getListaMyObject: " + this.selectedTipoMyObject);
		if (this.selectedTipoMyObject == null || this.selectedTipoMyObject.esNuevo() == true) {
			// this.mensajePopupTemporal("this.selectedTipoMyObject null");
			this.selectedTipoMyObject = new Tipo();
			return new ArrayList<MyObject>();
		}
		return rr.getListMyObjects(this.selectedTipoMyObject);
	}

	public void setListaMyObject(List<MyObject> listaMyObject) {
		this.listaMyObject = listaMyObject;
	}

	public MyObject getSelectedMyObject() {
		return selectedMyObject;
	}

	public void setSelectedMyObject(MyObject selectedMyObject) {
		this.selectedMyObject = selectedMyObject;
	}

	public MyObject getNvoMyObject() {
		return nvoMyObject;
	}

	public void setNvoMyObject(MyObject nvoMyObject) {
		this.nvoMyObject = nvoMyObject;
	}

}
