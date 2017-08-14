package com.coreweb;

import com.coreweb.inicio.LoginUsuarioDTO;

public class UsuarioPropiedad {
	
	LoginUsuarioDTO usuario = null;
	
	public UsuarioPropiedad(LoginUsuarioDTO usuario){
		this.usuario = usuario;
	}
	
	
	public LoginUsuarioDTO getUsuario() {
		return usuario;
	}
	
}
