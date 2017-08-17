package com.bascula.util.population;

import com.bascula.Configuracion;
import com.bascula.domain.RegisterDomain;
import com.coreweb.domain.Domain;
import com.coreweb.domain.Tipo;
import com.coreweb.domain.TipoTipo;
import com.bascula.util.population.DBPopulationTipos;

public class DBPopulationTipos {

	private RegisterDomain rr = RegisterDomain.getInstance();

	private void grabarDB(Domain d) throws Exception {
		rr.saveObject(d, "sys");
	}

	public void cargaTipos() throws Exception {

	}

	public static void main(String[] args) throws Exception {
		DBPopulationTipos db = new DBPopulationTipos();
		db.cargaTipos();
	}

}
