package com.bascula.leerPeso;

import java.io.BufferedReader;
import java.io.InputStreamReader;
 
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class BasculaPesoWSCliente {

	public static String REST_URI = "http://localhost:8090/gestion-bascula/rest/bascula/peso/";
	// public static String REST_URI =
	// "http://192.168.2.100:8082/gestion-bascula/rest/bascula/peso/";

	static String Text_plain = "text/plain";
	static String Application_xml = "application/xml";

	public void enviarPeso(long peso) {
		try {

			// create HTTP Client
			HttpClient httpClient = HttpClientBuilder.create().build();

			// Create new getRequest with below mentioned URL
			HttpGet getRequest = new HttpGet(REST_URI+peso);

			// Add additional header to getRequest which accepts application/xml
			// data
			getRequest.addHeader("accept", Text_plain);

			// Execute your request and catch response
			HttpResponse response = httpClient.execute(getRequest);

			// Check for HTTP response code: 200 = success
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}

			// Get-Capture Complete application/xml body response
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
			String output;
			System.out.println(System.currentTimeMillis()+ " ok");

			// Simply iterate through XML response and show on console.
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
//		System.out.println(MediaType.TEXT_PLAIN);
		BasculaPesoWSCliente bb = new BasculaPesoWSCliente();
		bb.enviarPeso(1501);
	}

}
