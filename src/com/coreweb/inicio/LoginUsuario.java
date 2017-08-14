package com.coreweb.inicio;

import java.util.Iterator;
import java.util.Set;

import com.coreweb.domain.*;
import com.coreweb.util.Misc;

public class LoginUsuario {

	private Misc m = new Misc();

	public LoginUsuarioDTO log(String login, String clave) throws Exception {
		clave = m.encriptar(clave);

		LoginUsuarioDTO lu = new LoginUsuarioDTO();
		Register rr = Register.getInstance();
		Usuario u = rr.getUsuario(login, clave);

		if (u != null) {
			lu.setLogeado(true);
			lu.setNombre(u.getNombre());
			lu.setLogin(u.getLogin());
			lu.setId(u.getId());

			//Obtiene la lista de los permisosEditar y lo asigna en memoria.
			Set<PermisoEditar> iteraPermisosEditar = u.getPermisos();
			for (PermisoEditar permisoEditar : iteraPermisosEditar) {
				lu.addPermisoEditar(permisoEditar.getAlias(), true);
			}

		}

		return lu;
	}

	public void printLog(String login, String clave) throws Exception {
		Misc m = new Misc();
		clave = m.encriptar(clave);

		Register rr = Register.getInstance();
		Usuario u = rr.getUsuario(login, clave);
		System.out.println(u.toString());
	}

	public static void main(String[] args) {
		try {
			LoginUsuario lu = new LoginUsuario();
			lu.printLog("juan", "juansa");
			System.out.println("================================");
			lu.printLog("maria", "mariasa");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
