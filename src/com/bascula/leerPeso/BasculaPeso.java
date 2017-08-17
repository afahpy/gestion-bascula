package com.bascula.leerPeso;

import java.util.Date;

public class BasculaPeso {

	
	static private Date actualizado = new Date();
	static private String msg = "kg";
	static private long PESO = 0;
	
	
	public static long getPeso(){
		return PESO;
	}
	
	public static void setPeso(long peso){
		actualizado = new Date();
		PESO = peso;
	}

	public static String getMsg() {
		return msg;
	}

	public static void setMsg(String msg) {
		BasculaPeso.msg = msg;
	}
	
	
	static MyThread mt = null;
	
	public static void init(){
//		if (mt  == null){
//			mt = new MyThread();
//			mt.start();
//		}
	}

	public static Date getActualizado() {
		return actualizado;
	}

	
	
}

class MyThread extends Thread{
	
	@Override
	public void run(){
		while (true){
			
//			System.out.println("            thread:"+this);
			BasculaPeso.setPeso(BasculaPeso.getPeso()+1);
			
			try {
				this.sleep(1000*3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
}
