package com.coreweb.inicio;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.QueryParam;
import org.zkoss.zhtml.Li;
import org.zkoss.zhtml.Ul;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.DesktopCleanup;
import org.zkoss.zkmax.zul.Nav;
import org.zkoss.zkmax.zul.Navitem;
import org.zkoss.zul.A;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;

import com.coreweb.Config;
import com.coreweb.IDCore;
import com.coreweb.control.Control;
import com.coreweb.dto.Assembler;

public class ControlInicio extends Control implements DesktopCleanup {

	private Component view;
	private String menu = "";

	@Wire
	private Include includePrincipal;

	public ControlInicio(Assembler ass) {
		super(ass);
	}

	public ControlInicio() {
		super(null);
	}

	@Init(superclass = true)
	public void init(@QueryParam("menu") String menu, @ContextParam(ContextType.VIEW) Component view) {
		
		//Al iniciarse el sistema siempre limpia la info del usuario en la sesion del ZK
		Session s = this.getSessionZK();
		s.setAttribute(Config.USUARIO, null);
		s.setAttribute(Config.INFO_USUARIO, null);
		this.setUrlInicio(Control.getUrlInicioDefault());
		this.setMenu(menu);
		this.view = view;

		// el control en la session para luego manipular las alertas
		this.setAtributoSession(Config.MI_ALERTAS, this);
		this.setAtributoSession(Config.CONTROL_INICIO, this);
	}

	@Override
	public void cleanup(Desktop arg0) throws Exception {
		System.out.println("Implementar Cleanup");

	}

	@AfterCompose
	public void afterCompose() {

	}

	public boolean getArchivoAdm() {
		return true;
	}

	/**
	 * Retorna el valor correspondiente al parametro MENU_HEADER_TEXT_PARAM
	 * configurado en el archivo sistema-propiedad
	 * 
	 * @return
	 */
	public String getMenuHeaderTitle() {
		String title = this.getSisProp().getPropiedad(Config.MENU_HEADER_TEXT_PARAM);
		if (title == null) {
			title = Config.MENU_HEADER_TEXT_PARAM_DEFAULT_VALUE;
		}
		return title;
	}

	/**
	 * Retorna la pagina a la cual se redireccionara si se hace click sobre el
	 * menu header.
	 * 
	 * @return
	 */
	public String getMenuHeaderRef() {
		String ref = this.getSisProp().getPropiedad(Config.MENU_HEADER_REF_PARAM);
		if (ref == null) {
			ref = Config.MENU_HEADER_REF_PARAM_DEFAULT_VALUE;
		}
		return ref;
	}

	public void menuItem(Object o, String formAlias, String label, String url) {
		this.menuItem(o, formAlias, "", label, url);
	}

	@SuppressWarnings("unchecked")
	public void menuItem(Object o, String formAlias, String paramsFromMenu, String label, String url) {

		boolean disabled = false;

		/*
		 * if (disabled == true) { return; }
		 */

		boolean visible = this.isMainMenuVisible(formAlias);

		if (o instanceof Ul) {
			Ul ul = (Ul) o;
			Li li = new Li();
			li.setVisible(visible);
			li.setId(formAlias);// El id que sea igual que el form alias para
								// poder utilizarlo luego
			A a = new A();

			Label la = new Label();
			
			if(formAlias.compareTo("USER_INFO") == 0){
				label = (String) this.getAtributoSession(Config.INFO_USUARIO);
			}
			
			la.setValue(label);

			if (formAlias.compareTo(IDCore.F_ALIAS_LOGOUT) == 0) {
				a.addEventListener("onClick", new MenuitemLogoutOnclick(url, formAlias, label, paramsFromMenu, this));
			} else {
				a.addEventListener("onClick", new MenuitemOnclick(url, formAlias, label, paramsFromMenu, this));
			}

			a.getChildren().add(la);
			li.getChildren().add(a);
			ul.getChildren().add(li);

		} else if (o instanceof Nav) {
			Nav nav = (Nav) o;
			Navitem navitem = new Navitem();
			navitem.setDisabled(disabled);
			navitem.setLabel(label);
			navitem.addEventListener("onClick", new MenuitemOnclick(url, formAlias, label, paramsFromMenu, this));
			nav.getChildren().add(navitem);

		} else if (o instanceof Navitem) {
			Navitem navitem = (Navitem) o;
			navitem.setDisabled(disabled);
			navitem.setLabel(label);
			navitem.addEventListener("onClick", new MenuitemOnclick(url, formAlias, label, paramsFromMenu, this));
		}
	}

	public boolean isMainMenuVisible(String formAlias) {
		Hashtable<String, Boolean> visibilidadMenus = this.getDtoUtil().getMenusVisibilidadSinLogin();
		return isMainMenuVisible(formAlias, visibilidadMenus );		
	}

	@SuppressWarnings("unchecked")
	public void menuLateralItem(Object o, String formAlias, String paramsFromMenu, String label, String url) {

		boolean disabled = false;

		if (disabled == true) {
			return;
		}
		if (o instanceof Ul) {
			Ul ul = (Ul) o;
			Li li = new Li();
			A a = new A();

			Label la = new Label();
			la.setValue(label);

			a.addEventListener("onClick", new MenuLateralitemOnclick(url, formAlias, label, paramsFromMenu, this));

			a.getChildren().add(la);
			li.getChildren().add(a);
			ul.getChildren().add(li);

		} else if (o instanceof Nav) {
			Nav nav = (Nav) o;
			Navitem navitem = new Navitem();
			navitem.setDisabled(disabled);
			navitem.setLabel(label);
			navitem.addEventListener("onClick",
					new MenuLateralitemOnclick(url, formAlias, label, paramsFromMenu, this));
			nav.getChildren().add(navitem);

		} else if (o instanceof Navitem) {
			Navitem navitem = (Navitem) o;
			navitem.setDisabled(disabled);
			navitem.setLabel(label);
			navitem.addEventListener("onClick",
					new MenuLateralitemOnclick(url, formAlias, label, paramsFromMenu, this));
		}
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public Include getIncludePrincipal() {
		return includePrincipal;
	}

	public void setIncludePrincipal(Include includePrincipal) {
		this.includePrincipal = includePrincipal;
	}

	public void refreshAlertas() {
		// this.setHayAlerta();
		System.out.println("=================== Refrescando ============");
		BindUtils.postNotifyChange(null, null, this, "*");

		// EventQueue que = EventQueues.lookup("groupTest",
		// EventQueues.APPLICATION, true);
	}

}

class MenuitemOnclick implements EventListener {

	String url = "";
	String label = "";
	String aliasFormulario = "";
	String paramsFromMenu = "";
	ControlInicio ctr = null;

	public MenuitemOnclick(String url, String aliasFormulario, String label, String paramsFromMenu, ControlInicio ctr) {
		super();
		this.url = url;
		this.aliasFormulario = aliasFormulario;
		this.label = label;
		this.paramsFromMenu = paramsFromMenu;
		this.ctr = ctr;
	}

	public void onEvent(Event event) throws Exception {
		Component main = Path.getComponent(Config.ZKOSS_PATH_A_INCLUDE_PRINCIPAL);
		Include homeMainInclude = (Include) main.getFellow(Config.ZKOSS_ID_INCLUDE_PRINCIPAL);
		homeMainInclude.setSrc(url);
	}
}

class MenuitemLogoutOnclick implements EventListener {

	String url = "";
	String label = "";
	String aliasFormulario = "";
	String paramsFromMenu = "";
	ControlInicio ctr = null;

	public MenuitemLogoutOnclick(String url, String aliasFormulario, String label, String paramsFromMenu,
			ControlInicio ctr) {
		super();
		this.url = url;
		this.aliasFormulario = aliasFormulario;
		this.label = label;
		this.paramsFromMenu = paramsFromMenu;
		this.ctr = ctr;
	}

	/**
	 * Controla evento de Logout del sistema
	 */
	public void onEvent(Event event) throws Exception {
		Session ss = this.ctr.getSessionZK();
		Set set  =  ss.getAttributes().keySet();
		for (Iterator iterator = set.iterator(); iterator.hasNext();) {
			String str = (String) iterator.next();
			ss.removeAttribute(str);
		}
//		Component main = Path.getComponent(Config.ZKOSS_PATH_A_INCLUDE_PRINCIPAL);
//		Include homeMainInclude = (Include) main.getFellow(Config.ZKOSS_ID_INCLUDE_PRINCIPAL);
//		homeMainInclude.setSrc(url);

		
		Executions.sendRedirect(url);
	}
}

class MenuLateralitemOnclick implements EventListener {

	String url = "";
	String label = "";
	String aliasFormulario = "";
	String paramsFromMenu = "";
	ControlInicio ctr = null;

	public MenuLateralitemOnclick(String url, String aliasFormulario, String label, String paramsFromMenu,
			ControlInicio ctr) {
		super();
		this.url = url;
		this.aliasFormulario = aliasFormulario;
		this.label = label;
		this.paramsFromMenu = paramsFromMenu;
		this.ctr = ctr;
	}

	public void onEvent(Event event) throws Exception {
		Component main = Path.getComponent(Config.ZKOSS_PATH_A_INCLUDE_LATERAL);
		Include homeMainInclude = (Include) main.getFellow(Config.ZKOSS_ID_INCLUDE_LATERAL);
		homeMainInclude.setSrc("");
		homeMainInclude.setSrc(url);
	}

}

class AlertaEvento implements EventListener {

	ControlInicio ctr = null;

	public AlertaEvento(ControlInicio ctr) {
		this.ctr = ctr;
	}

	@Override
	public void onEvent(Event arg0) throws Exception {
		ctr.refreshAlertas();

	}
}