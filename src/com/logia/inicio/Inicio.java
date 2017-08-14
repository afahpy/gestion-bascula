package com.logia.inicio;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import com.coreweb.Config;
import com.coreweb.SistemaPropiedad;
import com.coreweb.control.Control;
import com.coreweb.dto.Assembler;
import com.coreweb.inicio.LoginUsuarioDTO;
import com.coreweb.util.MyPair;
import com.logia.AssemblerUtil;
import com.logia.Configuracion;
import com.logia.ID;
import com.logia.UtilDTO;
import com.logia.domain.Mason;
import com.logia.domain.RegisterDomain;

public class Inicio {

	/**
	 * Inicializa el UtilDTO la primera vez que arranca el sistema, tambien
	 * asigna la URL de inicio por defecto asi como la configuracion de
	 * visibilidad de los menus luego del login.
	 */
	@SuppressWarnings("static-access")
	public synchronized static void init() {

		// Ver si tiene un UtilDTO seteado
		if (Control.existDtoUtil() == false) {

			// Inicializa el valor de la empresa y de la url de inicio de la
			// aplicacion.
			Control.setEmpresa(Configuracion.EMPRESA);
			Control.setUrlInicioDefault(Configuracion.URL_INICIO_VALOR_DEFAULT);

			// Inicializa UtilDto
			UtilDTO utilDto = new AssemblerUtil().getDTOUtil();
			Control.setInicialDtoUtil(utilDto);

			// Visibilidad de los menus sin login
			utilDto.getMenusVisibilidadSinLogin().put(ID.F_ALIAS_INICIO, true);
			utilDto.getMenusVisibilidadSinLogin().put(ID.F_ALIAS_CONTACTO, true);
			utilDto.getMenusVisibilidadSinLogin().put(ID.F_ALIAS_LOGIN, true);
			utilDto.getMenusVisibilidadSinLogin().put(ID.F_ALIAS_INICIO_AFTER_LOGIN, false);
			utilDto.getMenusVisibilidadSinLogin().put(ID.F_ALIAS_TALLER, false);
			utilDto.getMenusVisibilidadSinLogin().put(ID.F_ALIAS_BIBLIOTECA, false);
			utilDto.getMenusVisibilidadSinLogin().put(ID.F_ALIAS_COMUNICACIONES, false);
			utilDto.getMenusVisibilidadSinLogin().put(ID.F_ALIAS_ADMINISTRACION, false);
			utilDto.getMenusVisibilidadSinLogin().put(ID.F_ALIAS_USER_INFO, false);
			utilDto.getMenusVisibilidadSinLogin().put(ID.F_ALIAS_LOGOUT, false);

			// Visibilidad de los menus luego del login
			utilDto.getMenusVisibilidadConLogin().put(ID.F_ALIAS_INICIO, false);
			utilDto.getMenusVisibilidadConLogin().put(ID.F_ALIAS_CONTACTO, false);
			utilDto.getMenusVisibilidadConLogin().put(ID.F_ALIAS_LOGIN, false);
			utilDto.getMenusVisibilidadConLogin().put(ID.F_ALIAS_INICIO_AFTER_LOGIN, true);
			utilDto.getMenusVisibilidadConLogin().put(ID.F_ALIAS_TALLER, true);
			utilDto.getMenusVisibilidadConLogin().put(ID.F_ALIAS_BIBLIOTECA, true);
			utilDto.getMenusVisibilidadConLogin().put(ID.F_ALIAS_COMUNICACIONES, true);
			utilDto.getMenusVisibilidadConLogin().put(ID.F_ALIAS_ADMINISTRACION, true);
			utilDto.getMenusVisibilidadConLogin().put(ID.F_ALIAS_USER_INFO, true);
			utilDto.getMenusVisibilidadConLogin().put(ID.F_ALIAS_LOGOUT, true);
		}
	}

	public void afterLogin() throws Exception {

		RegisterDomain rr = RegisterDomain.getInstance();

		Session s = Sessions.getCurrent();
		// Se actualiza la pagina de inicio de la aplicacion luego del login
		s.setAttribute(Configuracion.URL_INICIO, Configuracion.URL_INICIO_VALOR_AFTER_LOGIN);

		// Se obtiene el usuario corriente y se extrae la informacion de mason.
		LoginUsuarioDTO login = (LoginUsuarioDTO) s.getAttribute(Configuracion.USUARIO);
		Mason m = rr.getMasonByUserLogin(login.getLogin());

		// Se convierte el grado del hermano a MyPair
		MyPair gradoMason = new MyPair();
		gradoMason.setId(m.getGrado().getId());
		gradoMason.setSigla(m.getGrado().getSigla());
		gradoMason.setText(m.getGrado().getDescripcion());

		// Se guarda el MyPair del grado del hermano correspondiente en sesion
		// ZK.
		s.setAttribute(Configuracion.SESSION_GRADO_MASON, gradoMason);

		System.out.println("Se ejecuta info usuario after login");
		s.setAttribute(Configuracion.INFO_USUARIO, "Q∴H∴"+ gradoMason.getText() + " " +m.getNombre() );

	}

}
