package com.bascula;

import java.util.ArrayList;
import java.util.List;

import com.bascula.domain.RegisterDomain;
import com.coreweb.domain.Domain;
import com.coreweb.domain.Ping;
import com.coreweb.domain.Tipo;
import com.coreweb.dto.AssemblerCoreUtil;
import com.coreweb.dto.DTO;
import com.coreweb.dto.UtilCoreDTO;
import com.bascula.AssemblerUtil;
import com.bascula.Configuracion;
import com.bascula.UtilDTO;

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

		dto.setTiposMovimientos(rr.getTipos(Configuracion.TIPO_TIPO_MOVIMIENTOS));
		dto.setTipoMovimientoEntrada(rr.getTipoPorSigla(Configuracion.SIGLA_TIPO_MOVIMIENTO_ENTRADA));
		dto.setTipoMovmientoSalida(rr.getTipoPorSigla(Configuracion.SIGLA_TIPO_MOVIMIENTO_SALIDA));

		dto.setTiposObjectos(rr.getTipos(Configuracion.TIPO_TIPO_OBJETOS));
		dto.setTipoObjetoOrigenLugar(rr.getTipoPorSigla(Configuracion.SIGLA_TIPO_OBJETO_ORIGEN_LUGAR));
		dto.setTipoObjetoDestinoLugar(rr.getTipoPorSigla(Configuracion.SIGLA_TIPO_OBJETO_DESTINO_LUGAR));
		dto.setTipoObjetoChapa(rr.getTipoPorSigla(Configuracion.SIGLA_TIPO_OBJETO_CHAPA));
		dto.setTipoObjetoChapaCarreta(rr.getTipoPorSigla(Configuracion.SIGLA_TIPO_OBJETO_CHAPA_CARRETA));
		dto.setTipoObjetoChofer(rr.getTipoPorSigla(Configuracion.SIGLA_TIPO_OBJETO_CHOFER));
		dto.setTipoObjetoTransportadora(rr.getTipoPorSigla(Configuracion.SIGLA_TIPO_OBJETO_TRANSPORTADORA));
		dto.setTipoObjetoDespachante(rr.getTipoPorSigla(Configuracion.SIGLA_TIPO_OBJETO_DESPACHANTE));

		return dto;
	}

	public static void main(String[] args) throws Exception {

		AssemblerUtil ass = new AssemblerUtil();
		ass.domainToDto(null);

	}

}
