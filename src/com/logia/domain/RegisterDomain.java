package com.logia.domain;

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

import com.coreweb.domain.IiD;
import com.coreweb.domain.Register;
import com.coreweb.domain.Tipo;
import com.coreweb.domain.TipoTipo;
import com.coreweb.util.Misc;
import com.coreweb.util.MyArray;
import com.coreweb.util.MyPair;
import com.logia.Configuracion;

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
	 ************************************************************/

	/**
	 * Retorna el mason correspondiente al usuario
	 * 
	 * @return
	 * @throws Exception
	 */
	public Mason getMasonByUserLogin(String login) throws Exception {
		String query = "select m from Mason m  where m.usuarioSistema.login = '" + login + "'";

		List<Mason> list = this.hql(query);
		System.out.println("Cantidad de elementos " + list.size());
		if (list.size() == 1) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public List<Mason> getMasonesByName(String name) throws Exception {
		String query = "select m from Mason m where lower(m.nombre) like '%" + name.toLowerCase() + "%' order by m.nombre";

		List<Mason> list = this.hql(query);

		return list;

	}

	public List<Mason> getMasonesOrderByCargo() throws Exception {
		return getMasones(Configuracion.ORDEN_MAS_CARGO);
	}

	public List<Mason> getMasones() throws Exception {
		return getMasones(Configuracion.ORDEN_MAS_NOMBRE);

	}

	
	public List<Mason> getMasones(String order) throws Exception {
		String query = "select m from Mason m order by "+order;
		System.out.println("query: ["+query+"]");
		List<Mason> list = this.hql(query);
		return list;
	}
	
	
	public List<CapitacionGL> getCapitacionesGlByHermano(Mason mason, Tipo periodo) throws Exception{
		String query = "select c from CapitacionGL c where c.mason.id = " + mason.getId() + " and c.periodo.id = " + periodo.getId();

		List<CapitacionGL> list = this.hql(query);

		return list;
	}

	
	public List<Tenida> getTenidas(Tipo gradoMasonFiltro, Tipo periodo, MyPair gradoMason) throws Exception {
		String query = "select t from Tenida t ";
		String where = " where 1=1 ";
		String order = " order by t.fecha desc";

		if (gradoMasonFiltro.getSigla().compareTo(Configuracion.SIGLA_TIPO_TODO) == 0) {

			if (gradoMason.getSigla().compareTo(Configuracion.SIGLA_TIPO_GRADO_MASON_APRENDIZ) == 0) {

				where += " and t.grado.sigla like '" + Configuracion.SIGLA_TIPO_GRADO_MASON_APRENDIZ + "'";

			} else if (gradoMason.getSigla().compareTo(Configuracion.SIGLA_TIPO_GRADO_MASON_COMPANERO) == 0) {

				where += " and (t.grado.sigla like '" + Configuracion.SIGLA_TIPO_GRADO_MASON_APRENDIZ
						+ "' or t.grado.sigla like '" + Configuracion.SIGLA_TIPO_GRADO_MASON_COMPANERO + "')";

			} else if (gradoMason.getSigla().compareTo(Configuracion.SIGLA_TIPO_GRADO_MASON_MAESTRO) == 0) {

				where += " and (t.grado.sigla like '" + Configuracion.SIGLA_TIPO_GRADO_MASON_APRENDIZ
						+ "' or t.grado.sigla like '" + Configuracion.SIGLA_TIPO_GRADO_MASON_COMPANERO
						+ "' or t.grado.sigla like '" + Configuracion.SIGLA_TIPO_GRADO_MASON_MAESTRO + "')";

			}

		} else {
			where += " and t.grado.sigla like '" + gradoMasonFiltro.getSigla() + "'";
		}

		if (periodo.getSigla().compareTo(Configuracion.SIGLA_TIPO_TODO) != 0) {
			where += " and t.periodo.sigla like '" + periodo.getSigla() + "'";
		}

		query += where + order;

		List<Tenida> list = this.hql(query);

		return list;

	}

	public List<MovimientoHospitalario> getMovimientosHospitalario() throws Exception {
		String query = "select m from MovimientoHospitalario m ";
		String where = " where 1=1 ";
		String order = " order by m.fecha desc";

		query += where + order;

		List<MovimientoHospitalario> list = this.hql(query);

		return list;

	}

	public double getSumaSaldoMovimientosHospitalario() throws Exception {
		String query = "select sum(m.importe) from MovimientoHospitalario m ";
		Double suma = (Double) this.hqlToObject(query);

		if (suma == null) {
			suma = new Double(0);
		}

		return suma;

	}

	public List<MovimientoTesoreria> getMovimientosTesoreria() throws Exception {
		String query = "select m from MovimientoTesoreria m ";
		String where = " where 1=1 ";
		String order = " order by m.fecha desc";

		query += where + order;

		List<MovimientoTesoreria> list = this.hql(query);

		return list;

	}

	public double getSumaSaldoMovimientosTesoreria() throws Exception {
		String query = "select sum(m.importe) from MovimientoTesoreria m ";
		Double suma = (Double) this.hqlToObject(query);

		if (suma == null) {
			suma = new Double(0);
		}

		return suma;

	}

	public static void main(String[] args) {
		try {

			RegisterDomain rr = RegisterDomain.getInstance();
			Mason m = rr.getMasonByUserLogin("test");
			System.out.println("m.getUsuarioSistema() " + m.getUsuarioSistema());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
