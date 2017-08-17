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
	
		return dto;
	}

	public static void main(String[] args) throws Exception {

		AssemblerUtil ass = new AssemblerUtil();
		ass.domainToDto(null);

	}

}
