package com.bascula.leerPeso;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

@Path("/bascula")

public class BasculaPesoWS {

	
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("/peso/{peso}")
	public Response addPeso(@PathParam("peso") String peso) {
		String out = ""+peso;
		
		try {
			long valor = Long.parseLong(peso);
			BasculaPeso.setMsg("kg");
			BasculaPeso.setPeso(valor);
		} catch (Exception e) {
			BasculaPeso.setMsg("err");
			BasculaPeso.setPeso(0);
			throw e;
		}

		return Response.status(200).entity(out).build();

	}

}
