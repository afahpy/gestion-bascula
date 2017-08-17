package com.bascula;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.coreweb.domain.Tipo;
import com.coreweb.domain.TipoTipo;
import com.coreweb.dto.UtilCoreDTO;
import com.coreweb.util.MyPair;

@SuppressWarnings("serial")
public class UtilDTO extends UtilCoreDTO {

	/***************************** Get y Set **********************************/

	/**
	 * Obtiene la direccion Ip Publica correspondiente
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getIp() throws Exception {
		URL whatismyip = new URL("http://checkip.amazonaws.com");
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
			String ip = in.readLine();
			return ip;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Obtiene la direccion IP publica.
	 * 
	 * @return
	 */
	private String getPublicIpAddress() {
		String res = null;
		try {
			String localhost = InetAddress.getLocalHost().getHostAddress();
			Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
			while (e.hasMoreElements()) {
				NetworkInterface ni = (NetworkInterface) e.nextElement();
				if (ni.isLoopback())
					continue;
				if (ni.isPointToPoint())
					continue;
				Enumeration<InetAddress> addresses = ni.getInetAddresses();
				while (addresses.hasMoreElements()) {
					InetAddress address = (InetAddress) addresses.nextElement();
					if (address instanceof Inet4Address) {
						String ip = address.getHostAddress();
						if (!ip.equals(localhost))
							res = ip;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	/******************************** Tipos ************************************/

	/***************************** Get y Set **********************************/

}
