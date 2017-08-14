package com.logia.util.population;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.time.DateUtils;

import com.coreweb.MenuConfigParser;
import com.coreweb.domain.AutoNumero;
import com.coreweb.domain.Domain;
import com.coreweb.domain.PermisoEditar;
import com.coreweb.domain.Tipo;
import com.coreweb.domain.Usuario;
import com.coreweb.util.Misc;
import com.logia.Configuracion;
import com.logia.domain.Mason;
import com.logia.domain.RegisterDomain;

public class DBPopulation {

	private static RegisterDomain rr = RegisterDomain.getInstance();
	private static  Misc m = new Misc();
	
	private static void grabarDB(Domain d) throws Exception {
		rr.saveObject(d, Configuracion.USER_SYSTEMA);
	}

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception {
		
		/******************************** Borrar BD ************************************/

		long ceroTime = System.currentTimeMillis();
		rr.dropAllTables();
		long iniTime = System.currentTimeMillis();

		/******************************** Cargar Tipos *********************************/
		System.out.println("Cargando tipos....");
		DBPopulationTipos tt = new DBPopulationTipos();
		tt.cargaTipos();
		System.out.println("Carga de tipos completa.");
		
		/******************************** Cargar Permisos *********************************/
		System.out.println("Cargando permisos....");
		MenuConfigParser menuConfParser = new MenuConfigParser();
		menuConfParser.main(null);
		System.out.println("Carga de permisos completa.");
		
		/****************************** Datos de Prueba ********************************/
		
		Tipo estadoActivo = rr.getTipoPorSigla(Configuracion.TIPO_H_ESTADO_ACTIVO);
		
		
		for (int i = 0; i < DBPopulationDatos.datosHH.length; i++) {
			//{ "Neymar", Configuracion.SIGLA_TIPO_GRADO_MASON_MAESTRO, Configuracion.SIGLA_TIPO_CARGO_TESORERO , "tesorero"},

			String[] dato = DBPopulationDatos.datosHH[i];
			String nombre = dato[0];
			String grado = dato[1];
			String cargo = dato[2];
			String login = dato[3].trim();
			String estado = dato[4].trim();
			Tipo estadoH = estadoActivo;
			
			
			
			Mason masonU1 = new Mason();
			masonU1.setNombre(nombre);
			masonU1.setGrado(rr.getTipoPorSigla(grado));
			masonU1.setCargo(rr.getTipoPorSigla(cargo));
			if (login != null && login.length() > 0){
				Usuario usr = rr.getUsuario(login);
				masonU1.setUsuarioSistema(usr);
			}
			if (estado != null && estado.length() > 0){
				estadoH = rr.getTipoPorSigla(estado);
			}
			masonU1.setEstado(estadoH);
			
			
			grabarDB(masonU1);
		}
				
		System.out.println("-- termino ---");
		
	}
}