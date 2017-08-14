package com.coreweb.control;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.ext.Constrainted;

import com.coreweb.Config;
import com.coreweb.dto.Assembler;
import com.coreweb.util.Check;
import com.coreweb.util.MyConverter;

public abstract class GenericViewModel extends Control {


	private boolean deshabilitado = true;
	private boolean siempreHabilitado = true;
	public Check check = new Check(this);

	private DisableEnableComponent disableEnableComponent = new DisableEnableComponent(this);
	
	
	/*
	 * esto es para darle un tratamiento especial a algunos que se tienen que
	 * deshabilitar/habilitar de forma especial
	 */


	public GenericViewModel(Assembler ass) throws RemoteException {
		super(ass);
		// TODO Auto-generated constructor stub
	}

	public GenericViewModel() {
		super(null);
	}

	public Component mainComponent = null;


	@Init(superclass = true)
	public void initGenericViewModel(
			@ContextParam(ContextType.VIEW) Component view) {

		this.mainComponent = view;

	}

	@AfterCompose(superclass = true)
	public void afterComposeGenericViewModel() {
		
	}

	public void readonlyAllComponents(AbstractComponent cmp){
		this.disableEnableComponent.disableComponents(cmp);
	}

	public void restoreAllReadonlyComponents(AbstractComponent cmp) {
		this.disableEnableComponent.restoreComponents(cmp);
	}
	
	public void readonlyAllComponents() {
		this.deshabilitado = true;
		this.disableEnableComponent.disableComponents((AbstractComponent) this.mainComponent);
	}

	public void disableAllComponents() {
		this.deshabilitado = true;
		this.disableEnableComponent.disableComponents((AbstractComponent) this.mainComponent);
	}

	public void restoreAllReadonlyComponents() {
		this.deshabilitado = false;
		this.disableEnableComponent.restoreComponents((AbstractComponent) this.mainComponent);
	}

	public void restoreAllDisabledComponents() {
		this.deshabilitado = false;
		this.disableEnableComponent.restoreComponents((AbstractComponent) this.mainComponent);
	}

	public void setDeshabilitado(boolean deshabilitado){
		this.deshabilitado = deshabilitado;
	}
	
	public boolean isDeshabilitado() {
		return deshabilitado;
	}

	@GlobalCommand
	@NotifyChange("*")
	public void refreshComponentes() {
	}

	// Crea un nuevo Window recibiendo como parametro el Path del zul..
	public Window createWindow(Window window, String zulPath) {

		window = (Window) Executions.createComponents(zulPath,
				this.mainComponent, null);
		return window;
	}

	public MyConverter getCnv() {
		return new MyConverter();
	}

	public boolean isSiempreHabilitado() {
		return siempreHabilitado
				&& this.getCondicionComponenteSiempreHabilitado();
	}

	public void setSiempreHabilitado(boolean siempreHabilitado) {
		this.siempreHabilitado = siempreHabilitado;
	}

	public abstract boolean getCondicionComponenteSiempreHabilitado();

	public Check getCheck() {
		return check;
	}

	public void setCheck(Check check) {
		this.check = check;
	}
}

