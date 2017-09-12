package com.bascula.leerPeso;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.rm5248.serial.*;


public class Leer3 {

	public static void leer() throws Exception {

		while (true) {

			System.out.println("---nuevo------------------------");
			SerialPort s = new SerialPort("/dev/ttyS0");
			SerialLineState state = s.getSerialLineState();
			System.out.println("---state: " + state);
			InputStream is = s.getInputStream();

			BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
			String line = "";

			while ((line = buffer.readLine()) != null) {
				System.err.println("line: " + line);
			}
			System.out.println("---cerrar------------------------");
			s.close();
		}
	}

	public static void main(String[] args) throws Exception {
		leer();
	}

}
