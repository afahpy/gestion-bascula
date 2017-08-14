package com.logia.util.population;

import com.coreweb.domain.Domain;
import com.coreweb.domain.Tipo;
import com.coreweb.domain.TipoTipo;
import com.logia.Configuracion;
import com.logia.domain.RegisterDomain;

public class DBPopulationTipos {

	private RegisterDomain rr = RegisterDomain.getInstance();

	private void grabarDB(Domain d) throws Exception {
		rr.saveObject(d, "sys");
	}

	public void cargaTipos() throws Exception {

		// GRADO HERMANO
		TipoTipo gradoMason = new TipoTipo();
		gradoMason.setDescripcion(Configuracion.ID_GRADO_MASON);
		this.grabarDB(gradoMason);

		Tipo gradoAprendiz = new Tipo();
		gradoAprendiz.setSigla(Configuracion.SIGLA_TIPO_GRADO_MASON_APRENDIZ);
		gradoAprendiz.setDescripcion(Configuracion.TIPO_GRADO_MASON_APRENDIZ);
		gradoAprendiz.setOrden("03");
		gradoAprendiz.setTipoTipo(gradoMason);
		this.grabarDB(gradoAprendiz);

		Tipo gradoCompanero = new Tipo();
		gradoCompanero.setSigla(Configuracion.SIGLA_TIPO_GRADO_MASON_COMPANERO);
		gradoCompanero.setDescripcion(Configuracion.TIPO_GRADO_MASON_COMPANERO);
		gradoCompanero.setOrden("02");
		gradoCompanero.setTipoTipo(gradoMason);
		this.grabarDB(gradoCompanero);

		Tipo nivelMaestro = new Tipo();
		nivelMaestro.setSigla(Configuracion.SIGLA_TIPO_GRADO_MASON_MAESTRO);
		nivelMaestro.setDescripcion(Configuracion.TIPO_GRADO_MASON_MAESTRO);
		nivelMaestro.setOrden("01");
		nivelMaestro.setTipoTipo(gradoMason);
		this.grabarDB(nivelMaestro);

		// TIPO TENIDA
		TipoTipo tipoTenida = new TipoTipo();
		tipoTenida.setDescripcion(Configuracion.ID_TIPO_TENIDA);
		this.grabarDB(tipoTenida);

		Tipo tipoTenidaSimple = new Tipo();
		tipoTenidaSimple.setSigla(Configuracion.SIGLA_TIPO_TENIDA_SIMPLE);
		tipoTenidaSimple.setDescripcion(Configuracion.TIPO_TENIDA_SIMPLE);
		tipoTenidaSimple.setTipoTipo(tipoTenida);
		this.grabarDB(tipoTenidaSimple);

		Tipo tipoTenidaEspecial = new Tipo();
		tipoTenidaEspecial.setSigla(Configuracion.SIGLA_TIPO_TENIDA_ESPECIAL);
		tipoTenidaEspecial.setDescripcion(Configuracion.TIPO_TENIDA_ESPECIAL);
		tipoTenidaEspecial.setTipoTipo(tipoTenida);
		this.grabarDB(tipoTenidaEspecial);

		Tipo tipoTenidaMagna = new Tipo();
		tipoTenidaMagna.setSigla(Configuracion.SIGLA_TIPO_TENIDA_MAGNA);
		tipoTenidaMagna.setDescripcion(Configuracion.TIPO_TENIDA_MAGNA);
		tipoTenidaMagna.setTipoTipo(tipoTenida);
		this.grabarDB(tipoTenidaMagna);

		// CARGOS
		TipoTipo cargo = new TipoTipo();
		cargo.setDescripcion(Configuracion.ID_TIPO_CARGO);
		this.grabarDB(cargo);

		Tipo cargo1 = new Tipo();
		cargo1.setSigla(Configuracion.SIGLA_TIPO_CARGO_VENERABLE_MAESTRO);
		cargo1.setDescripcion(Configuracion.TIPO_CARGO_VENERABLE_MAESTRO);
		cargo1.setTipoTipo(cargo);
		cargo1.setOrden("01");
		this.grabarDB(cargo1);

		Tipo cargo2 = new Tipo();
		cargo2.setSigla(Configuracion.SIGLA_TIPO_CARGO_PAST_VENERABLE_MAESTRO);
		cargo2.setDescripcion(Configuracion.TIPO_CARGO_PAST_VENERABLE_MAESTRO);
		cargo2.setTipoTipo(cargo);
		cargo2.setOrden("02");
		this.grabarDB(cargo2);

		Tipo cargo3 = new Tipo();
		cargo3.setSigla(Configuracion.SIGLA_TIPO_CARGO_PRIMER_VIGILANTE);
		cargo3.setDescripcion(Configuracion.TIPO_CARGO_PRIMER_VIGILANTE);
		cargo3.setTipoTipo(cargo);
		cargo3.setOrden("03");
		this.grabarDB(cargo3);

		Tipo cargo4 = new Tipo();
		cargo4.setSigla(Configuracion.SIGLA_TIPO_CARGO_SEGUNDO_VIGILANTE);
		cargo4.setDescripcion(Configuracion.TIPO_CARGO_SEGUNDO_VIGILANTE);
		cargo4.setTipoTipo(cargo);
		cargo4.setOrden("04");
		this.grabarDB(cargo4);

		Tipo cargo5 = new Tipo();
		cargo5.setSigla(Configuracion.SIGLA_TIPO_CARGO_ORADOR);
		cargo5.setDescripcion(Configuracion.TIPO_CARGO_ORADOR);
		cargo5.setTipoTipo(cargo);
		cargo5.setOrden("05");
		this.grabarDB(cargo5);

		Tipo cargo6 = new Tipo();
		cargo6.setSigla(Configuracion.SIGLA_TIPO_CARGO_SECRETARIO);
		cargo6.setDescripcion(Configuracion.TIPO_CARGO_SECRETARIO);
		cargo6.setTipoTipo(cargo);
		cargo6.setOrden("06");
		this.grabarDB(cargo6);

		Tipo cargo7 = new Tipo();
		cargo7.setSigla(Configuracion.SIGLA_TIPO_CARGO_TESORERO);
		cargo7.setDescripcion(Configuracion.TIPO_CARGO_TESORERO);
		cargo7.setTipoTipo(cargo);
		cargo7.setOrden("07");
		this.grabarDB(cargo7);

		Tipo cargo8 = new Tipo();
		cargo8.setSigla(Configuracion.SIGLA_TIPO_CARGO_HOSPITALARIO);
		cargo8.setDescripcion(Configuracion.TIPO_CARGO_HOSPITALARIO);
		cargo8.setTipoTipo(cargo);
		cargo8.setOrden("08");
		this.grabarDB(cargo8);

		Tipo cargo9 = new Tipo();
		cargo9.setSigla(Configuracion.SIGLA_TIPO_CARGO_MAESTRO_DE_CEREMONIA);
		cargo9.setDescripcion(Configuracion.TIPO_CARGO_MAESTRO_DE_CEREMONIA);
		cargo9.setTipoTipo(cargo);
		cargo9.setOrden("09");
		this.grabarDB(cargo9);

		Tipo cargo10 = new Tipo();
		cargo10.setSigla(Configuracion.SIGLA_TIPO_CARGO_PRIMER_DIACONO);
		cargo10.setDescripcion(Configuracion.TIPO_CARGO_PRIMER_DIACONO);
		cargo10.setTipoTipo(cargo);
		cargo10.setOrden("10");
		this.grabarDB(cargo10);

		Tipo cargo11 = new Tipo();
		cargo11.setSigla(Configuracion.SIGLA_TIPO_CARGO_SEGUNDO_DIACONO);
		cargo11.setDescripcion(Configuracion.TIPO_CARGO_SEGUNDO_DIACONO);
		cargo11.setTipoTipo(cargo);
		cargo11.setOrden("11");
		this.grabarDB(cargo11);

		Tipo cargo12 = new Tipo();
		cargo12.setSigla(Configuracion.SIGLA_TIPO_CARGO_GUARDA_TEMPLO_INTERNO);
		cargo12.setDescripcion(Configuracion.TIPO_CARGO_GUARDA_TEMPLO_INTERNO);
		cargo12.setTipoTipo(cargo);
		cargo12.setOrden("12");
		this.grabarDB(cargo12);

		Tipo cargo13 = new Tipo();
		cargo13.setSigla(Configuracion.SIGLA_TIPO_CARGO_GUARDA_TEMPLO_EXTERNO);
		cargo13.setDescripcion(Configuracion.TIPO_CARGO_GUARDA_TEMPLO_EXTERNO);
		cargo13.setTipoTipo(cargo);
		cargo13.setOrden("13");
		this.grabarDB(cargo13);

		Tipo cargo14 = new Tipo();
		cargo14.setSigla(Configuracion.SIGLA_TIPO_CARGO_MAESTRO_EXPERTO);
		cargo14.setDescripcion(Configuracion.TIPO_CARGO_MAESTRO_EXPERTO);
		cargo14.setTipoTipo(cargo);
		cargo14.setOrden("14");
		this.grabarDB(cargo14);

		Tipo cargo15 = new Tipo();
		cargo15.setSigla(Configuracion.SIGLA_TIPO_CARGO_MAESTRO_ARMONIA);
		cargo15.setDescripcion(Configuracion.TIPO_CARGO_MAESTRO_ARMONIA);
		cargo15.setTipoTipo(cargo);
		cargo15.setOrden("15");
		this.grabarDB(cargo15);

		Tipo cargo16 = new Tipo();
		cargo16.setSigla(Configuracion.SIGLA_TIPO_CARGO_MAESTRO_BANQUETERO);
		cargo16.setDescripcion(Configuracion.TIPO_CARGO_MAESTRO_BANQUETERO);
		cargo16.setTipoTipo(cargo);
		cargo16.setOrden("16");
		this.grabarDB(cargo16);

		Tipo cargo17 = new Tipo();
		cargo17.setSigla(Configuracion.SIGLA_TIPO_CARGO_PORTA_ESTANDARTE);
		cargo17.setDescripcion(Configuracion.TIPO_CARGO_PORTA_ESTANDARTE);
		cargo17.setTipoTipo(cargo);
		cargo17.setOrden("17");
		this.grabarDB(cargo17);

		Tipo cargo18 = new Tipo();
		cargo18.setSigla(Configuracion.SIGLA_TIPO_CARGO_SIN_CARGO);
		cargo18.setDescripcion(Configuracion.TIPO_CARGO_SIN_CARGO);
		cargo18.setTipoTipo(cargo);
		cargo18.setOrden("18");
		this.grabarDB(cargo18);

		// Periodos
		TipoTipo periodo = new TipoTipo();
		periodo.setDescripcion(Configuracion.ID_TIPO_PERIODO);
		this.grabarDB(periodo);

		Tipo periodo1 = new Tipo();
		periodo1.setSigla(Configuracion.SIGLA_TIPO_PERIODO_2016);
		periodo1.setDescripcion(Configuracion.TIPO_PERIODO_2016);
		periodo1.setTipoTipo(periodo);
		this.grabarDB(periodo1);

		Tipo periodo2 = new Tipo();
		periodo2.setSigla(Configuracion.SIGLA_TIPO_PERIODO_2017);
		periodo2.setDescripcion(Configuracion.TIPO_PERIODO_2017);
		periodo2.setTipoTipo(periodo);
		this.grabarDB(periodo2);

		Tipo periodo3 = new Tipo();
		periodo3.setSigla(Configuracion.SIGLA_TIPO_PERIODO_2018);
		periodo3.setDescripcion(Configuracion.TIPO_PERIODO_2018);
		periodo3.setTipoTipo(periodo);
		this.grabarDB(periodo3);

		// Tipo movimiento

		TipoTipo tipoMovimiento = new TipoTipo();
		tipoMovimiento.setDescripcion(Configuracion.ID_TIPO_MOVIMIENTO);
		this.grabarDB(tipoMovimiento);

		Tipo tipoMovimientoIngreso = new Tipo();
		tipoMovimientoIngreso.setSigla(Configuracion.SIGLA_TIPO_MOVIMIENTO_INGRESO);
		tipoMovimientoIngreso.setDescripcion(Configuracion.TIPO_MOVIMIENTO_INGRESO);
		tipoMovimientoIngreso.setTipoTipo(tipoMovimiento);
		this.grabarDB(tipoMovimientoIngreso);

		Tipo tipoMovimientoEgreso = new Tipo();
		tipoMovimientoEgreso.setSigla(Configuracion.SIGLA_TIPO_MOVIMIENTO_EGRESO);
		tipoMovimientoEgreso.setDescripcion(Configuracion.TIPO_MOVIMIENTO_EGRESO);
		tipoMovimientoEgreso.setTipoTipo(tipoMovimiento);
		this.grabarDB(tipoMovimientoEgreso);

		// Tipo movimiento hh
		TipoTipo tipoMovimientohh = new TipoTipo();
		tipoMovimientohh.setDescripcion(Configuracion.ID_TIPO_MOVIMIENTO_HERMANO);
		this.grabarDB(tipoMovimientohh);

		Tipo tipoMovHermIngreso = new Tipo();
		tipoMovHermIngreso.setSigla(Configuracion.SIGLA_TIPO_MOVIMIENTO_HH_INGRESO);
		tipoMovHermIngreso.setDescripcion(Configuracion.TIPO_MOVIMIENTO_HH_INGRESO);
		tipoMovHermIngreso.setTipoTipo(tipoMovimientohh);
		this.grabarDB(tipoMovHermIngreso);

		Tipo tipoMovHermEgreso = new Tipo();
		tipoMovHermEgreso.setSigla(Configuracion.SIGLA_TIPO_MOVIMIENTO_HH_EGRESO);
		tipoMovHermEgreso.setDescripcion(Configuracion.TIPO_MOVIMIENTO_HH_EGRESO);
		tipoMovHermEgreso.setTipoTipo(tipoMovimientohh);
		this.grabarDB(tipoMovHermEgreso);

		Tipo tipoMovHermPagoCap = new Tipo();
		tipoMovHermPagoCap.setSigla(Configuracion.SIGLA_TIPO_MOVIMIENTO_HH_PAGO_CAPITACION);
		tipoMovHermPagoCap.setDescripcion(Configuracion.TIPO_MOVIMIENTO_HH_PAGO_CAPITACION);
		tipoMovHermPagoCap.setTipoTipo(tipoMovimientohh);
		this.grabarDB(tipoMovHermPagoCap);


		// Tipo de estado de los HH
		TipoTipo tipoEstadohh = new TipoTipo();
		tipoEstadohh.setDescripcion(Configuracion.ID_TIPO_ESTADO_H);
		this.grabarDB(tipoEstadohh);

		
		Tipo tipoEstadoActivo = new Tipo();
		tipoEstadoActivo.setSigla(Configuracion.TIPO_H_ESTADO_ACTIVO);
		tipoEstadoActivo.setDescripcion(Configuracion.TIPO_H_ESTADO_ACTIVO);
		tipoEstadoActivo.setOrden("01");
		tipoEstadoActivo.setTipoTipo(tipoEstadohh);
		this.grabarDB(tipoEstadoActivo);


		Tipo tipoEstadoCotizante = new Tipo();
		tipoEstadoCotizante.setSigla(Configuracion.TIPO_H_ESTADO_COTIZANTE);
		tipoEstadoCotizante.setDescripcion(Configuracion.TIPO_H_ESTADO_COTIZANTE);
		tipoEstadoCotizante.setOrden("02");
		tipoEstadoCotizante.setTipoTipo(tipoEstadohh);
		this.grabarDB(tipoEstadoCotizante);

		Tipo tipoEstadoLibre = new Tipo();
		tipoEstadoLibre.setSigla(Configuracion.TIPO_H_ESTADO_LIBRE);
		tipoEstadoLibre.setDescripcion(Configuracion.TIPO_H_ESTADO_LIBRE);
		tipoEstadoLibre.setOrden("03");
		tipoEstadoLibre.setTipoTipo(tipoEstadohh);
		this.grabarDB(tipoEstadoLibre);

		Tipo tipoEstadoSuspendido = new Tipo();
		tipoEstadoSuspendido.setSigla(Configuracion.TIPO_H_ESTADO_SUSPENDIDO);
		tipoEstadoSuspendido.setDescripcion(Configuracion.TIPO_H_ESTADO_SUSPENDIDO);
		tipoEstadoSuspendido.setOrden("04");
		tipoEstadoSuspendido.setTipoTipo(tipoEstadohh);
		this.grabarDB(tipoEstadoSuspendido);
		
		this.cargarMesesTipo();

	}

	public void cargarMesesTipo() throws Exception {
		String[][] meses = { { "MES-ENE", "Enero", "01" }, { "MES-FEB", "Febrero", "02" }, { "MES-MAR", "Marzo", "03" },
				{ "MES-ABR", "Abril", "04" }, { "MES-MAY", "Mayo", "05" }, { "MES-JUN", "Junio", "06" },
				{ "MES-JUL", "Julio", "07" }, { "MES-AGO", "Agosto", "08" }, { "MES-SEP", "Septiembre", "09" },
				{ "MES-OCT", "Octubre", "10" }, { "MES-NOV", "Noviembre", "11" }, { "MES-DIC", "Diciembre", "12" } };

		TipoTipo mes = new TipoTipo();
		mes.setDescripcion(Configuracion.ID_MES);
		this.grabarDB(mes);

		for (int i = 0; i < meses.length; i++) {
			Tipo mesTipo = new Tipo();
			mesTipo.setSigla(meses[i][0]);
			mesTipo.setDescripcion(meses[i][1]);
			mesTipo.setOrden(meses[i][2]);
			mesTipo.setTipoTipo(mes);
			this.grabarDB(mesTipo);
		}
	}

	public static void main(String[] args) throws Exception {
		DBPopulationTipos db = new DBPopulationTipos();
		db.cargaTipos();
	}

}
