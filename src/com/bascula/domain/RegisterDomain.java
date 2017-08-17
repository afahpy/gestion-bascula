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
	 ************************************************************/

	public static void main(String[] args) {
		try {

			RegisterDomain rr = RegisterDomain.getInstance();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
