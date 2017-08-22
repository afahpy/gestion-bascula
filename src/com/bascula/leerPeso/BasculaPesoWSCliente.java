package com.bascula.leerPeso;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.MediaType;

public class BasculaPesoWSCliente {

	public static String REST_URI = "xxhttp://localhost:8090/wsrest/rest/bascula/peso/";

	public void enviarPeso(long valor) {
		try {
 
			Client client = Client.create();
			WebResource webResource2 = client.resource(REST_URI+valor);
			ClientResponse response2 = webResource2.accept(MediaType.TEXT_PLAIN).get(ClientResponse.class);
			if (response2.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response2.getStatus());
			}
 
			String output2 = response2.getEntity(String.class);
			System.out.println("respuesta:"+output2);
 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		BasculaPesoWSCliente bb = new BasculaPesoWSCliente();
		bb.enviarPeso(3243);
	}

}
