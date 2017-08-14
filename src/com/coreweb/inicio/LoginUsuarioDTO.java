package com.coreweb.inicio;

import com.coreweb.dto.DTO;
import com.coreweb.util.MapDefault;

public class LoginUsuarioDTO extends DTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 944510060720963584L;
	private boolean logeado = false;
	private String login = "login";
	private String nombre = "nombre";
	private MapDefault permisoEditar = new MapDefault(false);

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isLogeado() {
		return logeado;
	}

	public void setLogeado(boolean logeado) {
		this.logeado = logeado;
	}

	public MapDefault getPermisoEditar() {
		return permisoEditar;
	}

	public void setPermisoEditar(MapDefault permisoEditar) {
		this.permisoEditar = permisoEditar;
	}

	/**
	 * Agrega permisos a la instancia de esta clase.
	 * 
	 * @param permisoAlias
	 *            Alias del permiso
	 * @param habilitado
	 *            Si esta o no habilitado.
	 */
	public void addPermisoEditar(String permisoAlias, boolean habilitado) {
		boolean permisoEditar = habilitado;
		this.permisoEditar.put(permisoAlias, permisoEditar);
	}
	

	public boolean tienePermisoEditar(String permisoAlias){
		boolean b = (boolean)this.permisoEditar.get(permisoAlias);		
		return b;
	}

}
