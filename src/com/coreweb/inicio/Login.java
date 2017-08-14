package com.coreweb.inicio;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.DependsOn;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zhtml.Li;
import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkmax.zul.Nav;
import org.zkoss.zkmax.zul.Navitem;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Menu;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Popup;

import com.coreweb.Config;
import com.coreweb.IDCore;
import com.coreweb.control.Control;
import com.coreweb.domain.Register;
import com.coreweb.dto.Assembler;

public class Login extends Control {

	String user = "";
	String pass = "";
	String msg = "";

	private List<Object[]> users;

	@Wire
	private Combobox name;

	@Wire
	private Popup usuarios;

	public Login(Assembler ass) {
		super(ass);
	}

	public Login() {
		super(null);
	}

	@Init
	public void initLogin() {
		Session s = this.getSessionZK();
		s.setAttribute(Config.LOGEADO, new Boolean(false));
		this.loadUsers();
	}

	@AfterCompose
	public void afterComposeLogin(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}

	@Command
	@NotifyChange("*")
	public void loginOkBootstrap() throws Exception {
		this.loginOk(true);
	}

	public void loginOk(boolean bootstrap) throws Exception {

		LoginUsuario lu = new LoginUsuario();
		LoginUsuarioDTO uDto = lu.log(this.user, this.pass);

		this.setAtributoSession(Config.LOGEADO, uDto.isLogeado());
		this.setAtributoSession(Config.USUARIO, uDto);

		this.setUs(uDto);

		Component compTool = Path.getComponent("/homeWindow");
		Control vm = (Control) compTool.getAttribute("vm");
		vm.setUs(uDto);

		if (uDto.isLogeado() == true) {

			this.registrarLogin();
			System.out.println("Login Ok: " + this.user);

			this.executeAfterLogin();

			Include inc = (Include) compTool.getFellow("includeMenu");
			inc.invalidate(); // Esto hace un refresh del menu

			Object menuBar = inc.getFellow("ul-navbar-collapse");
			habilitarDeshabilitarMenuBar(menuBar);

			// Para el navbar rigth
			menuBar = inc.getFellow("ul-navbar-right");
			habilitarDeshabilitarMenuBar(menuBar);

		} else {
			System.out.println("Login Fail: " + this.user);
			this.msg = "Usuario o clave incorrecta";
		}
	}

	// ===============================================================
	private void habilitarDeshabilitarMenuBar(Object mobj) {

		AbstractComponent m = (AbstractComponent) mobj;
		List lcmps = m.getChildren();
		for (Iterator iterator = lcmps.iterator(); iterator.hasNext();) {
			Object dato = (Object) iterator.next();
			this.siMenuHabilitado(dato);

		}
	}

	private void siMenuHabilitado(Object m) {

		// Setea visibilidad de Menu Login y de Inicio Default en false despues
		// del login
		if (m instanceof Li) {
			Li mi = (Li) m;
			mi.setVisible(this.isMainMenuVisible(mi.getId()));
		}

	}

	public boolean isMainMenuVisible(String formAlias) {
		Hashtable<String, Boolean> visibilidadMenus = this.getDtoUtil().getMenusVisibilidadConLogin();
		return isMainMenuVisible(formAlias, visibilidadMenus);
	}

	// ===============================================================

	@Command
	public void openUserList() throws Exception {
		if (this.getUsers().size() > 1) {
			this.usuarios.open(this.name, "end_before");
		} else {
			this.usuarios.close();
		}
	}

	private void loadUsers() {
		Register rr = Register.getInstance();
		try {
			this.users = rr.getAllLogin();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@DependsOn("user")
	public List<String> getUsers() {
		List<String> out = new ArrayList<String>();

		if (this.user.trim().isEmpty())
			return new ArrayList<String>();

		for (Object[] user : this.users) {
			String login = (String) user[0];
			if (login.startsWith(this.user))
				out.add(login);
		}
		return out;
	}

	@DependsOn("user")
	public String getNombre() {
		for (Object[] user : this.users) {
			String login = (String) user[0];
			String name = (String) user[1];
			if (this.user.equals(login))
				return name;
		}
		return null;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * Ejecuto metodo AfterLogin
	 */
	public void executeAfterLogin() {
		try {

			System.out.println("Config.INIT_CLASE:" + Config.INIT_CLASE);
			Object o = this.m.newInstance(Config.INIT_CLASE);
			this.m.ejecutarMetoto(o, Config.INIT_AFTER_LOGIN);

		} catch (Exception e) {
			System.out.println("Error al ejecutar after login.");
			e.printStackTrace();
		}

	}

}
