package com.bascula.leerPeso;

import gnu.io.*;

import com.bascula.domain.MovimientoDetalle;

import com.bascula.domain.RegisterDomain;

import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.io.*;
import java.util.*;



/*
 * leer los datos de un archivo de configuración
 *  
 */

public class BasculaParser implements Runnable {

	// wiindows
	public static String NOMBRE_PUERTO = "COM1";
	// linux
	// public static String NOMBRE_PUERTO = "/dev/term/a";

	public static String NOMBRE_APP = "SimpleReadApp";
	
	public static boolean LOG = false;
	
	
	BasculaPesoWSCliente ws;

	protected long NO = -1000;
	static String file2 = "./doc/log-bascula.txt";
	static String file = "/home/daniel/datos/afah/proyectos/bascula/gestion-bascula/doc/log-bascula.txt";

	public void test() throws Exception {

		Scanner scan = new Scanner(new File(file));
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			long dato = getValue(line);
			if (dato != NO) {
				System.out.println(dato);
			}

		}
	}

	protected void print(String s){
		if (LOG == true){
			System.out.println(s);
		}
	}
	
	
	private void cargarConnfiguracion() throws Exception{
		Properties p = new Properties();
		InputStream is = new FileInputStream("./config.properties");
		p.load(is);
		
		
		NOMBRE_PUERTO = p.getProperty("NOMBRE_PUERTO");
		LOG =  Boolean.parseBoolean(p.getProperty("LOG"));
		BasculaPesoWSCliente.REST_URI = p.getProperty("WS_BASCULA");
		
		print("==============================================================="); 
		print("NOMBRE_PUERTO: "+NOMBRE_PUERTO);
		print("LOG: "+LOG);
		print("URL: "+BasculaPesoWSCliente.REST_URI);
		print("==============================================================="); 
		
		
		is.close();

	}
	
	protected long getValue(String line) {
		long out = NO;
		int p = line.indexOf('?');
		if (p >= 0) {
			String str = line.substring(p + 1).trim();
			try {
				out = Long.parseLong(str);
			} catch (Exception e) {
				// no hacer nada, sólo parsea los números
			}
		}
		return out;
	}

	public void testDB() throws Exception {
		RegisterDomain rr = RegisterDomain.getInstance();
		MovimientoDetalle m = new MovimientoDetalle();
		rr.saveObject(m, "pp");
		System.out.println("INI-----------");
		List l = rr.hql("select m from MovimientoDetalle m");
		System.out.println("l.size():" + l.size());
		System.out.println("FIN-----------");

	}


	private static void runLectorBascula() throws Exception {
		BasculaParser bp = new BasculaParser();
		bp.cargarConnfiguracion();
		bp.initcializar();
		Thread th = new Thread(bp);
		th.start();
	}

	public static void main(String[] args) {
		try {
			runLectorBascula();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	private CommPortIdentifier buscarPortId() {

		CommPortIdentifier out = null;
		Enumeration portList = CommPortIdentifier.getPortIdentifiers();

		while (portList.hasMoreElements()) {
			out = (CommPortIdentifier) portList.nextElement();
			if (out.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				if (out.getName().compareTo(NOMBRE_PUERTO) == 0) {
					return out;
				}
			}
		}
		return out;

	}

	private void initcializar() throws Exception {
		CommPortIdentifier portId = this.buscarPortId();
		SerialPort serialPort = (SerialPort) portId.open(NOMBRE_APP, 2000);
		
		BasculaPortEventListener portListener = new BasculaPortEventListener();
		
		portListener.inputStream = serialPort.getInputStream();
		
		serialPort.addEventListener(portListener);
		serialPort.notifyOnDataAvailable(true);
		serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		// conecta con el WS
		this.ws = new BasculaPesoWSCliente();
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(5 * 1000);
			} catch (InterruptedException e) {
				System.out.println(e);
			}
		}
	}

}


class BasculaPortEventListener implements SerialPortEventListener{

	public InputStream inputStream;
	public BasculaParser parser;

	@Override
	public void serialEvent(SerialPortEvent event) {
		switch (event.getEventType()) {
		case SerialPortEvent.BI:
		case SerialPortEvent.OE:
		case SerialPortEvent.FE:
		case SerialPortEvent.PE:
		case SerialPortEvent.CD:
		case SerialPortEvent.CTS:
		case SerialPortEvent.DSR:
		case SerialPortEvent.RI:
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
			break;
		case SerialPortEvent.DATA_AVAILABLE:
			byte[] readBuffer = new byte[20];

			try {
				while (this.inputStream.available() > 0) {
					int numBytes = this.inputStream.read(readBuffer);
				}
				String line = new String(readBuffer);

				long dato = parser.getValue(line);
				if (dato != parser.NO) {
					parser.print("   "+dato);
					parser.ws.enviarPeso(dato);
				}
			} catch (IOException e) {
				System.out.println(e);
			}
			break;
		}
	}

	
	
}
