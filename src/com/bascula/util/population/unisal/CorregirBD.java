package com.bascula.util.population.unisal;

import com.bascula.Configuracion;
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
	
	

	public static void main(String[] args) throws Exception {
		
		CorregirBD corregirDB = new CorregirBD();
		String descTipoMovimiento = Configuracion.TIPO_TIPO_MOVIMIENTOS;
		String[][] tipoMovimientos = {{ Configuracion.TIPO_MOVIMIENTO_CONSUMO, Configuracion.SIGLA_TIPO_MOVIMIENTO_CONSUMO }};

		corregirDB.cargaTiposConTipoTipoExistente(descTipoMovimiento, tipoMovimientos);
		
	}

}
