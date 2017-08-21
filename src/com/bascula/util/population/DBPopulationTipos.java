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

	public void poblarTipos() throws Exception {

		// Tipos de Movimiento
		String descTipoMovimiento = Configuracion.TIPO_TIPO_MOVIMIENTOS;
		String[][] tipoMovimientos = {
				{ Configuracion.TIPO_MOVIMIENTO_ENTRADA, Configuracion.SIGLA_TIPO_MOVIMIENTO_ENTRADA },
				{ Configuracion.TIPO_MOVIMIENTO_SALIDA, Configuracion.SIGLA_TIPO_MOVIMIENTO_SALIDA } };

		this.cargaTiposConTipoTipo(descTipoMovimiento, tipoMovimientos);

		// Tipos de Objeto

		String descTipoObjeto = Configuracion.TIPO_TIPO_OBJETOS;
		String[][] tiposObjeto = {
				{ Configuracion.TIPO_OBJETO_ORIGEN_LUGAR, Configuracion.SIGLA_TIPO_OBJETO_ORIGEN_LUGAR },
				{ Configuracion.TIPO_OBJETO_DESTINO_LUGAR, Configuracion.SIGLA_TIPO_OBJETO_DESTINO_LUGAR },
				{ Configuracion.TIPO_OBJETO_CHAPA, Configuracion.SIGLA_TIPO_OBJETO_CHAPA },
				{ Configuracion.TIPO_OBJETO_CHAPA_CARRETA, Configuracion.SIGLA_TIPO_OBJETO_CHAPA_CARRETA },
				{ Configuracion.TIPO_OBJETO_CHOFER, Configuracion.SIGLA_TIPO_OBJETO_CHOFER },
				{ Configuracion.TIPO_OBJETO_TRANSPORTADORA, Configuracion.SIGLA_TIPO_OBJETO_TRANSPORTADORA },
				{ Configuracion.TIPO_OBJETO_DESPACHANTE, Configuracion.SIGLA_TIPO_OBJETO_DESPACHANTE } };

		this.cargaTiposConTipoTipo(descTipoObjeto, tiposObjeto);

	}

	public static void main(String[] args) throws Exception {
		DBPopulationTipos db = new DBPopulationTipos();
		db.poblarTipos();

	}

}
