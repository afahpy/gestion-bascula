package com.logia;

import java.util.ArrayList;
import java.util.List;

import com.coreweb.domain.Domain;
import com.coreweb.domain.Ping;
import com.coreweb.domain.Tipo;
import com.coreweb.dto.AssemblerCoreUtil;
import com.coreweb.dto.DTO;
import com.coreweb.dto.UtilCoreDTO;
import com.logia.domain.RegisterDomain;

public class AssemblerUtil extends AssemblerCoreUtil {

	public static UtilDTO getDTOUtil() {
		AssemblerUtil as = new AssemblerUtil();
		UtilCoreDTO dto = getDTOUtilCore(as);
		return (UtilDTO) dto;
	}

	@Override
	public Domain dtoToDomain(DTO dtoC) throws Exception {
		// TODO Auto-generated method stub
		Ping ping = new Ping();
		ping.setEcho("Configuracion modificada: " + System.currentTimeMillis());

		return ping;
	}

	@Override
	public DTO domainToDto(Domain domain) throws Exception {

		UtilDTO dto = new UtilDTO();
		RegisterDomain rr = RegisterDomain.getInstance();

		/********************************
		 * Carga Tipos
		 ************************************/
		// Tipo todo(Temporal para los filtros, no se guarda en BD)
		Tipo tipoTodo = new Tipo();
		tipoTodo.setSigla(Configuracion.SIGLA_TIPO_TODO);
		tipoTodo.setDescripcion(Configuracion.TIPO_TODO);
		dto.setTipoTodo(tipoTodo);

		// Se obtienen todos los grados de mason
		Tipo aprendizTipo = rr.getTipoPorSigla(Configuracion.SIGLA_TIPO_GRADO_MASON_APRENDIZ);
		Tipo companeroTipo = rr.getTipoPorSigla(Configuracion.SIGLA_TIPO_GRADO_MASON_COMPANERO);
		Tipo maestroTipo = rr.getTipoPorSigla(Configuracion.SIGLA_TIPO_GRADO_MASON_MAESTRO);

		dto.setAprendizTipo(aprendizTipo);
		dto.setCompaneroTipo(companeroTipo);
		dto.setMaestroTipo(maestroTipo);

		List<Tipo> gradosMazonTipo = new ArrayList<Tipo>();
		gradosMazonTipo.add(aprendizTipo);
		gradosMazonTipo.add(companeroTipo);
		gradosMazonTipo.add(maestroTipo);
		dto.setGradosMasonTipo(gradosMazonTipo);

		// Grados mason filtros con "Todo"
		List<Tipo> gradosMasonFiltroAprendiz = new ArrayList<Tipo>();
		gradosMasonFiltroAprendiz.add(tipoTodo);
		gradosMasonFiltroAprendiz.add(aprendizTipo);
		dto.setGradosMasonFiltroAprendiz(gradosMasonFiltroAprendiz);

		List<Tipo> gradosMasonFiltroCompanero = new ArrayList<Tipo>();
		gradosMasonFiltroCompanero.add(tipoTodo);
		gradosMasonFiltroCompanero.add(aprendizTipo);
		gradosMasonFiltroCompanero.add(companeroTipo);
		dto.setGradosMasonFiltroCompanero(gradosMasonFiltroCompanero);

		List<Tipo> gradosMasonFiltroMaestro = new ArrayList<Tipo>();
		gradosMasonFiltroMaestro.add(tipoTodo);
		gradosMasonFiltroMaestro.add(aprendizTipo);
		gradosMasonFiltroMaestro.add(companeroTipo);
		gradosMasonFiltroMaestro.add(maestroTipo);
		dto.setGradosMasonFiltroMaestro(gradosMasonFiltroMaestro);

		// Grados mason filtros sin "Todo"
		List<Tipo> gradosMasonFiltroAprendizSinTodo = new ArrayList<Tipo>();
		gradosMasonFiltroAprendizSinTodo.add(aprendizTipo);
		dto.setGradosMasonFiltroAprendizSinTodo(gradosMasonFiltroAprendizSinTodo);

		List<Tipo> gradosMasonFiltroCompaneroSinTodo = new ArrayList<Tipo>();
		gradosMasonFiltroCompaneroSinTodo.add(aprendizTipo);
		gradosMasonFiltroCompaneroSinTodo.add(companeroTipo);
		dto.setGradosMasonFiltroCompaneroSinTodo(gradosMasonFiltroCompaneroSinTodo);

		List<Tipo> gradosMasonFiltroMaestroSinTodo = new ArrayList<Tipo>();
		gradosMasonFiltroMaestroSinTodo.add(aprendizTipo);
		gradosMasonFiltroMaestroSinTodo.add(companeroTipo);
		gradosMasonFiltroMaestroSinTodo.add(maestroTipo);
		dto.setGradosMasonFiltroMaestroSinTodo(gradosMasonFiltroMaestroSinTodo);

		// Convierte a MyPair
		dto.setAprendiz(this.tipoToMyPair(aprendizTipo));
		dto.setCompanero(this.tipoToMyPair(companeroTipo));
		dto.setMaestro(this.tipoToMyPair(maestroTipo));
		dto.setGradosMason(this.listaTiposToListaMyPair(gradosMazonTipo));

		Tipo simple = rr.getTipoPorSigla(Configuracion.SIGLA_TIPO_TENIDA_SIMPLE);
		Tipo especial = rr.getTipoPorSigla(Configuracion.SIGLA_TIPO_TENIDA_ESPECIAL);
		Tipo magna = rr.getTipoPorSigla(Configuracion.SIGLA_TIPO_TENIDA_MAGNA);

		dto.setTenidaSimple(simple);
		dto.setTenidaEspecial(especial);
		dto.setTenidaMagna(magna);

		dto.getTenidasTipo().add(simple);
		dto.getTenidasTipo().add(especial);
		dto.getTenidasTipo().add(magna);

		dto.getCargos().addAll(rr.getTipos(Configuracion.ID_TIPO_CARGO));

		Tipo periodo2016 = rr.getTipoPorSigla(Configuracion.SIGLA_TIPO_PERIODO_2016);
		Tipo periodo2017 = rr.getTipoPorSigla(Configuracion.SIGLA_TIPO_PERIODO_2017);

		dto.getPeriodos().add(periodo2016);
		dto.getPeriodos().add(periodo2017);

		dto.getPeriodosFiltro().add(tipoTodo);
		dto.getPeriodosFiltro().add(periodo2016);
		dto.getPeriodosFiltro().add(periodo2017);

		dto.setPeriodo2016(periodo2016);
		dto.setPeriodo2017(periodo2017);

		dto.getTiposMovimientos().addAll(rr.getTipos(Configuracion.ID_TIPO_MOVIMIENTO));
		dto.setTipoMovimientoIngreso(rr.getTipoPorSigla(Configuracion.SIGLA_TIPO_MOVIMIENTO_INGRESO));
		dto.setTipoMovimientoEgreso(rr.getTipoPorSigla(Configuracion.SIGLA_TIPO_MOVIMIENTO_EGRESO));
		
		dto.getTiposMovimientosHH().addAll(rr.getTipos(Configuracion.ID_TIPO_MOVIMIENTO_HERMANO));
		dto.setTipoMovHHIngreso(rr.getTipoPorSigla(Configuracion.SIGLA_TIPO_MOVIMIENTO_HH_INGRESO));
		dto.setTipoMovHHEgreso(rr.getTipoPorSigla(Configuracion.SIGLA_TIPO_MOVIMIENTO_HH_EGRESO));
		dto.setTipoMovHHPagoCap(rr.getTipoPorSigla(Configuracion.SIGLA_TIPO_MOVIMIENTO_HH_PAGO_CAPITACION));
		
		
		dto.getTiposEstadosH().addAll(rr.getTipos(Configuracion.ID_TIPO_ESTADO_H));
		
		
		dto.getMeses().addAll(rr.getTipos(Configuracion.ID_MES));
		dto.setMesEnero(dto.getMeses().get(0));

		return dto;
	}

	public static void main(String[] args) throws Exception {

		AssemblerUtil ass = new AssemblerUtil();
		ass.domainToDto(null);

	}

}
