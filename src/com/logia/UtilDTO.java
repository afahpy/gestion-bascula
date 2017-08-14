package com.logia;

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

	private List<MyPair> gradosMason = new ArrayList<MyPair>();
	private MyPair aprendiz = new MyPair();
	private MyPair companero = new MyPair();
	private MyPair maestro = new MyPair();

	private Tipo tipoTodo = new Tipo();

	// Tiene todos los grados sin el "Todos"
	private List<Tipo> gradosMasonTipo = new ArrayList<Tipo>();

	// Tienen los grados especificos, si tiene "Todos". Ej: un companero puede
	// ver los filtros "aprendiz" y "companero" mas el "todos"
	private List<Tipo> gradosMasonFiltroAprendiz = new ArrayList<Tipo>();
	private List<Tipo> gradosMasonFiltroCompanero = new ArrayList<Tipo>();
	private List<Tipo> gradosMasonFiltroMaestro = new ArrayList<Tipo>();

	private List<Tipo> gradosMasonFiltroAprendizSinTodo = new ArrayList<Tipo>();
	private List<Tipo> gradosMasonFiltroCompaneroSinTodo = new ArrayList<Tipo>();
	private List<Tipo> gradosMasonFiltroMaestroSinTodo = new ArrayList<Tipo>();

	private Tipo aprendizTipo = new Tipo();
	private Tipo companeroTipo = new Tipo();
	private Tipo maestroTipo = new Tipo();

	private List<Tipo> tenidasTipo = new ArrayList<Tipo>();
	private Tipo tenidaSimple = new Tipo();
	private Tipo tenidaEspecial = new Tipo();
	private Tipo tenidaMagna = new Tipo();

	private List<Tipo> cargos = new ArrayList<Tipo>();
	private List<Tipo> periodos = new ArrayList<Tipo>();
	private List<Tipo> periodosFiltro = new ArrayList<Tipo>();

	// Se usa en MovimientoHospitalario
	private List<Tipo> tiposMovimientos = new ArrayList<Tipo>();
	private Tipo tipoMovimientoIngreso = new Tipo();
	private Tipo tipoMovimientoEgreso = new Tipo();

	private Tipo periodo2016 = new Tipo();
	private Tipo periodo2017 = new Tipo();

	private List<Tipo> meses = new ArrayList<Tipo>();
	private Tipo mesEnero = new Tipo();

	// Se usa en Movimientos HH
	private List<Tipo> tiposMovimientosHH = new ArrayList<Tipo>();
	private Tipo tipoMovHHIngreso = new Tipo();
	private Tipo tipoMovHHEgreso = new Tipo();
	private Tipo tipoMovHHPagoCap = new Tipo();


	// Estados Hermanos
	private List<Tipo> tiposEstadosH = new ArrayList<Tipo>();
	
	
	/***************************** Get y Set **********************************/

	public MyPair getAprendiz() {
		return aprendiz;
	}

	public List<MyPair> getGradosMason() {
		return gradosMason;
	}

	public void setGradosMason(List<MyPair> gradosMason) {
		this.gradosMason = gradosMason;
	}

	public void setAprendiz(MyPair aprendiz) {
		this.aprendiz = aprendiz;
	}

	public MyPair getCompanero() {
		return companero;
	}

	public void setCompanero(MyPair companero) {
		this.companero = companero;
	}

	public MyPair getMaestro() {
		return maestro;
	}

	public void setMaestro(MyPair maestro) {
		this.maestro = maestro;
	}

	public List<Tipo> getGradosMasonTipo() {
		return gradosMasonTipo;
	}

	public void setGradosMasonTipo(List<Tipo> gradosMasonTipo) {
		this.gradosMasonTipo = gradosMasonTipo;
	}

	public Tipo getAprendizTipo() {
		return aprendizTipo;
	}

	public void setAprendizTipo(Tipo aprendizTipo) {
		this.aprendizTipo = aprendizTipo;
	}

	public Tipo getCompaneroTipo() {
		return companeroTipo;
	}

	public void setCompaneroTipo(Tipo companeroTipo) {
		this.companeroTipo = companeroTipo;
	}

	public Tipo getMaestroTipo() {
		return maestroTipo;
	}

	public void setMaestroTipo(Tipo maestroTipo) {
		this.maestroTipo = maestroTipo;
	}

	public List<Tipo> getTenidasTipo() {
		return tenidasTipo;
	}

	public void setTenidasTipo(List<Tipo> tenidasTipo) {
		this.tenidasTipo = tenidasTipo;
	}

	public Tipo getTenidaSimple() {
		return tenidaSimple;
	}

	public void setTenidaSimple(Tipo tenidaSimple) {
		this.tenidaSimple = tenidaSimple;
	}

	public Tipo getTenidaEspecial() {
		return tenidaEspecial;
	}

	public void setTenidaEspecial(Tipo tenidaEspecial) {
		this.tenidaEspecial = tenidaEspecial;
	}

	public Tipo getTenidaMagna() {
		return tenidaMagna;
	}

	public void setTenidaMagna(Tipo tenidaMagna) {
		this.tenidaMagna = tenidaMagna;
	}

	public List<Tipo> getCargos() {
		return cargos;
	}

	public void setCargos(List<Tipo> cargos) {
		this.cargos = cargos;
	}

	public List<Tipo> getPeriodos() {
		return periodos;
	}

	public void setPeriodos(List<Tipo> periodos) {
		this.periodos = periodos;
	}

	public Tipo getTipoTodo() {
		return tipoTodo;
	}

	public void setTipoTodo(Tipo tipoTodo) {
		this.tipoTodo = tipoTodo;
	}

	public List<Tipo> getGradosMasonFiltroAprendiz() {
		return gradosMasonFiltroAprendiz;
	}

	public void setGradosMasonFiltroAprendiz(List<Tipo> gradosMasonFiltroAprendiz) {
		this.gradosMasonFiltroAprendiz = gradosMasonFiltroAprendiz;
	}

	public List<Tipo> getGradosMasonFiltroCompanero() {
		return gradosMasonFiltroCompanero;
	}

	public void setGradosMasonFiltroCompanero(List<Tipo> gradosMasonFiltroCompanero) {
		this.gradosMasonFiltroCompanero = gradosMasonFiltroCompanero;
	}

	public List<Tipo> getGradosMasonFiltroMaestro() {
		return gradosMasonFiltroMaestro;
	}

	public void setGradosMasonFiltroMaestro(List<Tipo> gradosMasonFiltroMaestro) {
		this.gradosMasonFiltroMaestro = gradosMasonFiltroMaestro;
	}

	public List<Tipo> getPeriodosFiltro() {
		return periodosFiltro;
	}

	public void setPeriodosFiltro(List<Tipo> periodosFiltro) {
		this.periodosFiltro = periodosFiltro;
	}

	public List<Tipo> getTiposMovimientos() {
		return tiposMovimientos;
	}

	public void setTiposMovimientos(List<Tipo> tiposMovimientos) {
		this.tiposMovimientos = tiposMovimientos;
	}

	public Tipo getTipoMovimientoIngreso() {
		return tipoMovimientoIngreso;
	}

	public void setTipoMovimientoIngreso(Tipo tipoMovimientoIngreso) {
		this.tipoMovimientoIngreso = tipoMovimientoIngreso;
	}

	public Tipo getTipoMovimientoEgreso() {
		return tipoMovimientoEgreso;
	}

	public void setTipoMovimientoEgreso(Tipo tipoMovimientoEgreso) {
		this.tipoMovimientoEgreso = tipoMovimientoEgreso;
	}

	public Tipo getPeriodo2016() {
		return periodo2016;
	}

	public void setPeriodo2016(Tipo periodo2016) {
		this.periodo2016 = periodo2016;
	}

	public Tipo getPeriodo2017() {
		return periodo2017;
	}

	public void setPeriodo2017(Tipo periodo2017) {
		this.periodo2017 = periodo2017;
	}

	public List<Tipo> getGradosMasonFiltroAprendizSinTodo() {
		return gradosMasonFiltroAprendizSinTodo;
	}

	public void setGradosMasonFiltroAprendizSinTodo(List<Tipo> gradosMasonFiltroAprendizSinTodo) {
		this.gradosMasonFiltroAprendizSinTodo = gradosMasonFiltroAprendizSinTodo;
	}

	public List<Tipo> getGradosMasonFiltroCompaneroSinTodo() {
		return gradosMasonFiltroCompaneroSinTodo;
	}

	public void setGradosMasonFiltroCompaneroSinTodo(List<Tipo> gradosMasonFiltroCompaneroSinTodo) {
		this.gradosMasonFiltroCompaneroSinTodo = gradosMasonFiltroCompaneroSinTodo;
	}

	public List<Tipo> getGradosMasonFiltroMaestroSinTodo() {
		return gradosMasonFiltroMaestroSinTodo;
	}

	public void setGradosMasonFiltroMaestroSinTodo(List<Tipo> gradosMasonFiltroMaestroSinTodo) {
		this.gradosMasonFiltroMaestroSinTodo = gradosMasonFiltroMaestroSinTodo;
	}

	public List<Tipo> getMeses() {
		return meses;
	}

	public void setMeses(List<Tipo> meses) {
		this.meses = meses;
	}

	public Tipo getMesEnero() {
		return mesEnero;
	}

	public void setMesEnero(Tipo mesEnero) {
		this.mesEnero = mesEnero;
	}

	public List<Tipo> getTiposMovimientosHH() {
		return tiposMovimientosHH;
	}

	public void setTiposMovimientosHH(List<Tipo> tiposMovimientosHH) {
		this.tiposMovimientosHH = tiposMovimientosHH;
	}

	public Tipo getTipoMovHHIngreso() {
		return tipoMovHHIngreso;
	}

	public void setTipoMovHHIngreso(Tipo tipoMovHHIngreso) {
		this.tipoMovHHIngreso = tipoMovHHIngreso;
	}

	public Tipo getTipoMovHHEgreso() {
		return tipoMovHHEgreso;
	}

	public void setTipoMovHHEgreso(Tipo tipoMovHHEgreso) {
		this.tipoMovHHEgreso = tipoMovHHEgreso;
	}

	public Tipo getTipoMovHHPagoCap() {
		return tipoMovHHPagoCap;
	}

	public void setTipoMovHHPagoCap(Tipo tipoMovHHPagoCap) {
		this.tipoMovHHPagoCap = tipoMovHHPagoCap;
	}

	public List<Tipo> getTiposEstadosH() {
		return tiposEstadosH;
	}

	public void setTiposEstadosH(List<Tipo> tiposEstadosH) {
		this.tiposEstadosH = tiposEstadosH;
	}

}
