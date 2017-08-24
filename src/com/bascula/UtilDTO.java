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

	// Tipos de movimiento
	List<Tipo> tiposMovimientos = new ArrayList<Tipo>();
	Tipo tipoMovimientoEntrada = new Tipo();
	Tipo tipoMovmientoSalida = new Tipo();

	// Tipos objeto
	List<Tipo> tiposObjectos = new ArrayList<Tipo>();
	Tipo tipoObjetoOrigenLugar = new Tipo();
	Tipo tipoObjetoDestinoLugar = new Tipo();
	Tipo tipoObjetoChapa = new Tipo();
	Tipo tipoObjetoChapaCarreta = new Tipo();
	Tipo tipoObjetoChofer = new Tipo();
	Tipo tipoObjetoTransportadora = new Tipo();
	Tipo tipoObjetoDespachante = new Tipo();

	/***************************** Get y Set **********************************/

	public List<Tipo> getTiposMovimientos() {
		return tiposMovimientos;
	}

	public void setTiposMovimientos(List<Tipo> tiposMovimientos) {
		this.tiposMovimientos = tiposMovimientos;
	}

	public Tipo getTipoMovimientoEntrada() {
		return tipoMovimientoEntrada;
	}

	public void setTipoMovimientoEntrada(Tipo tipoMovimientoEntrada) {
		this.tipoMovimientoEntrada = tipoMovimientoEntrada;
	}

	public Tipo getTipoMovmientoSalida() {
		return tipoMovmientoSalida;
	}

	public void setTipoMovmientoSalida(Tipo tipoMovmientoSalida) {
		this.tipoMovmientoSalida = tipoMovmientoSalida;
	}

	public Tipo getTipoObjetoOrigenLugar() {
		return tipoObjetoOrigenLugar;
	}

	public void setTipoObjetoOrigenLugar(Tipo tipoObjetoOrigenLugar) {
		this.tipoObjetoOrigenLugar = tipoObjetoOrigenLugar;
	}

	public Tipo getTipoObjetoDestinoLugar() {
		return tipoObjetoDestinoLugar;
	}

	public void setTipoObjetoDestinoLugar(Tipo tipoObjetoDestinoLugar) {
		this.tipoObjetoDestinoLugar = tipoObjetoDestinoLugar;
	}

	public Tipo getTipoObjetoChapa() {
		return tipoObjetoChapa;
	}

	public void setTipoObjetoChapa(Tipo tipoObjetoChapa) {
		this.tipoObjetoChapa = tipoObjetoChapa;
	}

	public Tipo getTipoObjetoChapaCarreta() {
		return tipoObjetoChapaCarreta;
	}

	public void setTipoObjetoChapaCarreta(Tipo tipoObjetoChapaCarreta) {
		this.tipoObjetoChapaCarreta = tipoObjetoChapaCarreta;
	}

	public Tipo getTipoObjetoChofer() {
		return tipoObjetoChofer;
	}

	public void setTipoObjetoChofer(Tipo tipoObjetoChofer) {
		this.tipoObjetoChofer = tipoObjetoChofer;
	}

	public Tipo getTipoObjetoTransportadora() {
		return tipoObjetoTransportadora;
	}

	public void setTipoObjetoTransportadora(Tipo tipoObjetoTransportadora) {
		this.tipoObjetoTransportadora = tipoObjetoTransportadora;
	}

	public Tipo getTipoObjetoDespachante() {
		return tipoObjetoDespachante;
	}

	public void setTipoObjetoDespachante(Tipo tipoObjetoDespachante) {
		this.tipoObjetoDespachante = tipoObjetoDespachante;
	}

	public List<Tipo> getTiposObjectos() {
		return tiposObjectos;
	}

	public void setTiposObjectos(List<Tipo> tiposObjectos) {
		this.tiposObjectos = tiposObjectos;
	}

}
