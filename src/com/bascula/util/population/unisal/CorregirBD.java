package com.bascula.util.population.unisal;

import com.bascula.Configuracion;
import com.bascula.domain.MyObject;
import com.bascula.domain.RegisterDomain;
import com.coreweb.domain.Domain;
import com.coreweb.domain.Tipo;
import com.coreweb.domain.TipoTipo;

public class CorregirBD {

	RegisterDomain rr = RegisterDomain.getInstance();

	private void grabarDB(Domain d) throws Exception {
		rr.saveObject(d, "sys");
	}

	public void cargaTiposConTipoTipo(String descripcionTipoTipo, String[][] tipos) throws Exception {

		TipoTipo tipoTipo = new TipoTipo();
		tipoTipo.setDescripcion(descripcionTipoTipo);
		this.grabarDB(tipoTipo);
		System.out.println("--> Se cargo el TipoTipo: " + descripcionTipoTipo);

		for (int i = 0; i < tipos.length; i++) {

			Tipo tipo = new Tipo();
			tipo.setDescripcion(tipos[i][0]);// Para la descripcion pos 1
			tipo.setSigla(tipos[i][1]);// Para la sigla pos 2
			tipo.setTipoTipo(tipoTipo);
			this.grabarDB(tipo);
			System.out.println("--> Se cargo el tipo: " + tipos[i][0]);

		}

	}

	public void cargaTiposConTipoTipoExistente(String descripcionTipoTipo, String[][] tipos) throws Exception {

		TipoTipo tipoTipo = new TipoTipo();
		tipoTipo = this.rr.getTipoTipoPorDescripcion(descripcionTipoTipo);
		System.out.println("--> Se obtuvo el tipotipo: " + descripcionTipoTipo);

		for (int i = 0; i < tipos.length; i++) {

			Tipo tipo = new Tipo();
			tipo.setDescripcion(tipos[i][0]);// Para la descripcion pos 1
			tipo.setSigla(tipos[i][1]);// Para la sigla pos 2
			tipo.setTipoTipo(tipoTipo);
			this.grabarDB(tipo);
			System.out.println("--> Se cargo el tipo: " + tipos[i][0]);

		}

	}

	public void cargaLugares() throws Exception {
		RegisterDomain rr = RegisterDomain.getInstance();

		String patio = "";
		String depo = "";

		MyObject uMraPatio = (MyObject) rr.getObject(MyObject.class.getName(), 9);
		uMraPatio.setStrCampo1("Patio - Unisal MRA");
		rr.saveObject(uMraPatio, "pp");
		patio += "   Destino Patio " + uMraPatio.getId();

		MyObject uMraDep = (MyObject) rr.getObject(MyObject.class.getName(), 52);
		uMraDep.setStrCampo1("Deposito - Unisal MRA");
		rr.saveObject(uMraDep, "pp");
		depo += " Deposito Origen " + uMraDep.getId();

		uMraPatio.setId((long) -1);
		uMraPatio.setStrCampo1("Deposito - Unisal MRA");
		rr.saveObject(uMraPatio, "pp");
		depo += " Deposito destino " + uMraPatio.getId();

		uMraDep.setId((long) -1);
		uMraDep.setStrCampo1("Patio - Unisal MRA");
		rr.saveObject(uMraDep, "pp");
		patio += "   Origen Patio " + uMraDep.getId();


		uMraPatio.setId((long) -1);
		uMraPatio.setStrCampo1("Producción - Unisal MRA");
		rr.saveObject(uMraPatio, "pp");
		depo += " Produccion destino " + uMraPatio.getId();
		
		
		
		System.out.println(patio);
		System.out.println(depo);

	}

	public static void main(String[] args) throws Exception {

		CorregirBD corregirDB = new CorregirBD();
		String descTipoMovimiento = Configuracion.TIPO_TIPO_MOVIMIENTOS;
		
		// para el consumo y produccionn
		String[][] tipoMovimientos = {
				{ Configuracion.TIPO_MOVIMIENTO_CONSUMO, Configuracion.SIGLA_TIPO_MOVIMIENTO_CONSUMO },
				{ Configuracion.TIPO_MOVIMIENTO_PRODUCCION, Configuracion.SIGLA_TIPO_MOVIMIENTO_PRODUCCION} };
		corregirDB.cargaTiposConTipoTipoExistente(descTipoMovimiento,tipoMovimientos);
				
		corregirDB.cargaLugares();
	}

}
