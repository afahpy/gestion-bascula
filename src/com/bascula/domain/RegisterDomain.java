package com.bascula.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.bascula.Configuracion;
import com.coreweb.domain.IiD;
import com.coreweb.domain.Register;
import com.coreweb.domain.Tipo;
import com.coreweb.domain.TipoTipo;
import com.coreweb.util.Misc;
import com.coreweb.util.MyArray;
import com.coreweb.util.MyPair;

import net.sourceforge.barbecue.Main;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class RegisterDomain extends Register {

	Misc misc = new Misc();

	// El register tiene que ser un sigleton
	private RegisterDomain() {
		super();
	}

	public synchronized static RegisterDomain getInstance() {
		return (RegisterDomain) Register.getInstanceCore(RegisterDomain.class.getName());
	}

	/************************************************************
	 * Metodos especificos de las clases del dominio
	 * 
	 * @throws Exception
	 ************************************************************/

	public List<MovimientoEntradaSalida> getMovimientos() throws Exception {
		String query = "SELECT m FROM MovimientoEntradaSalida m";
		List<MovimientoEntradaSalida> movimientos = new ArrayList<MovimientoEntradaSalida>();
		movimientos = this.hql(query);
		return movimientos;
	}

	public List<MyObject> getObjetosByTipoObjeto(IiD tipoObjeto) throws Exception {
		String query = "SELECT o FROM MyObject o where o.tipoObjeto.id = " + tipoObjeto.getId();
		List<MyObject> objetos = new ArrayList<MyObject>();
		objetos = this.hql(query);
		return objetos;
	}

	public List<MyObject> getOrigenLugares() throws Exception {
		Tipo tipoObjeto = this.getTipoPorSigla(Configuracion.SIGLA_TIPO_OBJETO_ORIGEN_LUGAR);
		return this.getObjetosByTipoObjeto(tipoObjeto);
	}

	public List<MyObject> getDestinoLugares() throws Exception {
		Tipo tipoObjeto = this.getTipoPorSigla(Configuracion.SIGLA_TIPO_OBJETO_DESTINO_LUGAR);
		return this.getObjetosByTipoObjeto(tipoObjeto);
	}

	public List<MyObject> getChapas() throws Exception {
		Tipo tipoObjeto = this.getTipoPorSigla(Configuracion.SIGLA_TIPO_OBJETO_CHAPA);
		return this.getObjetosByTipoObjeto(tipoObjeto);
	}

	public List<MyObject> getChapasCarretas() throws Exception {
		Tipo tipoObjeto = this.getTipoPorSigla(Configuracion.SIGLA_TIPO_OBJETO_CHAPA_CARRETA);
		return this.getObjetosByTipoObjeto(tipoObjeto);
	}

	public List<MyObject> getChoferes() throws Exception {
		Tipo tipoObjeto = this.getTipoPorSigla(Configuracion.SIGLA_TIPO_OBJETO_CHOFER);
		return this.getObjetosByTipoObjeto(tipoObjeto);
	}

	public List<MyObject> getTransportadoras() throws Exception {
		Tipo tipoObjeto = this.getTipoPorSigla(Configuracion.SIGLA_TIPO_OBJETO_TRANSPORTADORA);
		return this.getObjetosByTipoObjeto(tipoObjeto);
	}

	public List<MyObject> getDespachantes() throws Exception {
		Tipo tipoObjeto = this.getTipoPorSigla(Configuracion.SIGLA_TIPO_OBJETO_DESPACHANTE);
		return this.getObjetosByTipoObjeto(tipoObjeto);
	}

	public static void main(String[] args) {
		try {

			RegisterDomain rr = RegisterDomain.getInstance();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
