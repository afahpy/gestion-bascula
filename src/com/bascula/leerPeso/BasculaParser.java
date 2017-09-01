package com.bascula.leerPeso;

import com.rm5248.serial.SerialLineState;
import com.rm5248.serial.SerialPort;

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

//	public static String NOMBRE_APP = "SimpleReadApp";

	public static boolean LOG = true;

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

	protected void print(String s) {
		if (LOG == true) {
			System.out.println(s);
		}
	}

	private void cargarConnfiguracion() throws Exception {
		Properties p = new Properties();
		InputStream is = new FileInputStream("./config.properties");
		p.load(is);

		NOMBRE_PUERTO = p.getProperty("NOMBRE_PUERTO");
		LOG = Boolean.parseBoolean(p.getProperty("LOG"));
		BasculaPesoWSCliente.REST_URI = p.getProperty("WS_BASCULA");

		print("===============================================================");
		print("NOMBRE_PUERTO: " + NOMBRE_PUERTO);
		print("LOG: " + LOG);
		print("URL: " + BasculaPesoWSCliente.REST_URI);
		print("===============================================================");

		is.close();

	}

	protected long getValue(String line) {
		long out = NO;
		line = line.trim();

		String valor = "";
		boolean leer = true;

		while (leer == true) {
			try {
				int l = line.length();
				String s = line.substring(l - 1);
				int dig = Integer.valueOf(s);
				valor = s + valor;
				line = line.substring(0, l - 1);
			} catch (Exception e) {
				leer = false;
			}
		} // while
		try {
			out = Long.valueOf(valor);
		} catch (Exception e) {
			out = NO;
		}

		return out;
	}

	protected long getValue2(String line) {
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
/*
	public void testDB() throws Exception {
		RegisterDomain rr = RegisterDomain.getInstance();
		MovimientoDetalle m = new MovimientoDetalle();
		rr.saveObject(m, "pp");
		System.out.println("INI-----------");
		List l = rr.hql("select m from MovimientoDetalle m");
		System.out.println("l.size():" + l.size());
		System.out.println("FIN-----------");

	}
*/
	private static void runLectorBascula() throws Exception {
		BasculaParser bp = new BasculaParser();
		bp.print("--leyendo configuración..");
		bp.cargarConnfiguracion();
		bp.print("--inicializar puerto..");
		bp.initcializar();
		bp.print("--lanza thread..");
		Thread th = new Thread(bp);
		th.start();
		bp.print("--listo..");
	}

	public static void pruebaGetValue() {
		BasculaParser bp = new BasculaParser();
		System.out.println(bp.getValue("2   3a   "));
	}

	public static void main(String[] args) {
		try {
			runLectorBascula();
			// pruebaGetValue();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initcializar() throws Exception {

		// conecta con el WS
		print("        crea ws");
		this.ws = new BasculaPesoWSCliente();

	}

	@Override
	public void run() {
		int t = 5;
		long ultimoCC = 0;
		while (true) {
			SerialPort sp = null;
			try {

				System.out.println(System.currentTimeMillis()+"(conectando)");
				Thread.sleep(1000); 
				
				sp = new SerialPort(NOMBRE_PUERTO);
				SerialLineState state = sp.getSerialLineState();
				InputStream is = sp.getInputStream();

				BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
				String line = "";

				while ((line = buffer.readLine()) != null) {
					//System.err.println("line: " + line);

//					this.print("                                line:[" + line + "]");

					long dato = this.getValue(line);
					
					if (dato != ultimoCC){
						// mandar cuando hay diferencias 

						if (dato != NO) {
//							System.out.println(dato);
							this.print("                                " + dato);
							this.ws.enviarPeso(dato);
						} else {
							System.out.println("NO:" + line);
						}
					
					}
					ultimoCC = dato;

					System.out.println(System.currentTimeMillis()+"(leyendo)");
//					Thread.sleep(1000); 
					
					
				}// while

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e);
			} finally {
				if ((sp != null)&&(sp.isClosed()==false)){
					System.out.println("---cerrar------------------------");
					sp.close();
				}
			}
			
			
			
		} // while
	}

}
