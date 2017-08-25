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

	public List<MovimientoEntradaSalida> getMovimientos(String filTipoMovimiento, String filLugarOrigen,
			String filLugarDestino, String filRemito, String filRemision, String filChapa, String filChapaCarreta,
			String filChofer, String filTransportadora, String filDespacho, String filDespachante,
			Date filtroFechaLlegadaDesde, Date filtroFechaLlegadaHasta, Date filtroFechaSalidaDesde,
			Date filtroFechaSalidaHasta) throws Exception {

		List<Object> listaParametros = new ArrayList<Object>();
		String query = "SELECT m FROM MovimientoEntradaSalida m ";
		String where = " WHERE 1=1 ";

		if (filTipoMovimiento != null && filTipoMovimiento.trim().length() > 0) {
			where += " AND lower(m.tipoMovimiento.descripcion) like '%" + filTipoMovimiento.toLowerCase() + "%' ";
		}

		if (filLugarOrigen != null && filLugarOrigen.trim().length() > 0) {
			where += " AND lower(m.origenLugar.strCampo1) like '%" + filLugarOrigen.toLowerCase() + "%' ";
		}

		if (filLugarDestino != null && filLugarDestino.trim().length() > 0) {
			where += " AND lower(m.destinoLugar.strCampo1) like '%" + filLugarDestino.toLowerCase() + "%' ";
		}

		if (filRemito != null && filRemito.trim().length() > 0) {
			where += " AND lower(m.remito) like '%" + filRemito.toLowerCase() + "%' ";
		}

		if (filRemision != null && filRemision.trim().length() > 0) {
			where += " AND lower(m.remision) like '%" + filRemision.toLowerCase() + "%' ";
		}

		if (filChapa != null && filChapa.trim().length() > 0) {
			where += " AND lower(m.chapa.strCampo1) like '%" + filChapa.toLowerCase() + "%' ";
		}

		if (filChapaCarreta != null && filChapaCarreta.trim().length() > 0) {
			where += " AND lower(m.chapaCarreta.strCampo1) like '%" + filChapaCarreta.toLowerCase() + "%' ";
		}

		if (filChofer != null && filChofer.trim().length() > 0) {
			where += " AND lower(m.chofer.strCampo1) like '%" + filChofer.toLowerCase() + "%' ";
		}

		if (filTransportadora != null && filTransportadora.trim().length() > 0) {
			where += " AND lower(m.transportadora.strCampo1) like '%" + filTransportadora.toLowerCase() + "%' ";
		}

		if (filDespacho != null && filDespacho.trim().length() > 0) {
			where += " AND lower(m.despacho) like '%" + filDespacho.toLowerCase() + "%' ";
		}

		if (filDespachante != null && filDespachante.trim().length() > 0) {
			where += " AND lower(m.despachante.strCampo1) like '%" + filDespachante.toLowerCase() + "%' ";
		}

		/*
		 * Ocurrencia del caso 2 explicado mas arriba Para devolver todos los
		 * movimientos entre el inicio de las operaciones hasta la "fechaHasta".
		 */
		if (filtroFechaLlegadaDesde == null && filtroFechaLlegadaHasta != null) {
			where += " AND m.fechaLlegada  <= ? ";
			listaParametros.add(filtroFechaLlegadaHasta);
		}

		/*
		 * Ocurrencia del caso 3 explicado mas arriba Para devolver todos los
		 * movimientos entre la "fechaDesde" hasta los movimientos mas
		 * recientes.
		 */
		if (filtroFechaLlegadaDesde != null && filtroFechaLlegadaHasta == null) {
			where += " AND m.fechaLlegada  >= ? ";
			listaParametros.add(filtroFechaLlegadaDesde);
		}

		/*
		 * Caso 4: Para devolver todos los movimientos entre "fechaDesde" y
		 * "fechaHasta"
		 */
		if ((filtroFechaLlegadaDesde != null && filtroFechaLlegadaHasta != null)
				&& (filtroFechaLlegadaDesde.compareTo(filtroFechaLlegadaHasta) <= 0)) {
			where += " and m.fechaLlegada between ? and ? ";
			listaParametros.add(filtroFechaLlegadaDesde);
			listaParametros.add(filtroFechaLlegadaHasta);
		}

		/*
		 * Ocurrencia del caso 2 explicado mas arriba Para devolver todos los
		 * movimientos entre el inicio de las operaciones hasta la "fechaHasta".
		 */
		if (filtroFechaSalidaDesde == null && filtroFechaSalidaHasta != null) {
			where += " AND m.fechaSalida  <= ? ";
			listaParametros.add(filtroFechaSalidaHasta);
		}

		/*
		 * Ocurrencia del caso 3 explicado mas arriba Para devolver todos los
		 * movimientos entre la "fechaDesde" hasta los movimientos mas
		 * recientes.
		 */
		if (filtroFechaSalidaDesde != null && filtroFechaSalidaHasta == null) {
			where += " and m.fechaSalida  >= ? ";
			listaParametros.add(filtroFechaSalidaDesde);
		}

		/*
		 * Caso 4: Para devolver todos los movimientos entre "fechaDesde" y
		 * "fechaHasta"
		 */
		if ((filtroFechaSalidaDesde != null && filtroFechaSalidaHasta != null)
				&& (filtroFechaSalidaDesde.compareTo(filtroFechaSalidaHasta) <= 0)) {
			where += " and m.fechaSalida between ? and ? ";
			listaParametros.add(filtroFechaSalidaDesde);
			listaParametros.add(filtroFechaSalidaHasta);
		}

		query += where;
		Object[] parametros = new Object[listaParametros.size()];
		for (int i = 0; i < listaParametros.size(); i++) {
			parametros[i] = listaParametros.get(i);
		}

		List<MovimientoEntradaSalida> movimientos = new ArrayList<MovimientoEntradaSalida>();
		movimientos = this.hql(query, parametros);
		return movimientos;
	}

	public List<MyObject> getObjetosByTipoObjeto(IiD tipoObjeto) throws Exception {
		String query = "SELECT o FROM MyObject o where o.tipoObjeto.id = " + tipoObjeto.getId()
				+ " order by strCampo1 asc ";
		List<MyObject> objetos = new ArrayList<MyObject>();
		objetos = this.hql(query);
		return objetos;
	}

	public List<MyObject> getListMyObjects(Tipo tipoObjeto) throws Exception {
		return this.getObjetosByTipoObjeto(tipoObjeto);
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

	public List<MyObject> getProductos() throws Exception {
		Tipo tipoObjeto = this.getTipoPorSigla(Configuracion.SIGLA_TIPO_OBJETO_PRODUCTO);
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
