package com.bascula.leerPeso;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

public class BasculaPesoVM {

	DecimalFormat nf = (DecimalFormat)NumberFormat.getNumberInstance(Locale.GERMAN);
	DateFormat df = new SimpleDateFormat("hh:mm:ss");
	
	@Init(superclass = true)
	public void initBasculaPesoVM(){
		BasculaPeso.init();
	}
	
	public String getBasculaPesoLeido() {
		return   nf.format(BasculaPeso.getPeso());
	}

	public String getBasculaMensaje(){
		return BasculaPeso.getMsg();
	}

	public String getBasculaActualizado(){
		return df.format(BasculaPeso.getActualizado());
	}
	
	@Command
	public void onTimer() {
		BindUtils.postNotifyChange(null, null, this, "basculaPesoLeido");
		BindUtils.postNotifyChange(null, null, this, "basculaActualizado");
		BindUtils.postNotifyChange(null, null, this, "basculaMensaje");
	}

}
