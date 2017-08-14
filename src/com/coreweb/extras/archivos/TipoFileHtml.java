package com.coreweb.extras.archivos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Comparator;

import javax.activation.MimetypesFileTypeMap;

import com.coreweb.util.Misc;

public class TipoFileHtml implements Comparator{
	
	static double UNIDAD_TAMANIO = 1024.0 * 1024.0;
	static String UNIDAD_SIGLA = " mg";
	
	
	Misc m = new Misc();
	static String IMG_DIRECTORIO = "glyphicon glyphicon-folder-open";
	static String IMG_ARCHIVO = "glyphicon glyphicon-file";
	
	String image = "";
	
	File f;
	
	public TipoFileHtml(File f){
		this.f = f;
	}
	
	public String getNombre(){
		String out = this.f.getName();
		return out;
	}
	
	public boolean esDirectorio(){
		return this.f.isDirectory();
	}
	
	public String getImage(){
		String out = IMG_ARCHIVO;
		if (this.f.isDirectory() == true){
			out = IMG_DIRECTORIO;
		}
		return out;
	}
	
	public FileInputStream getInputStream() throws FileNotFoundException{
		return new FileInputStream(this.f);
	}
	
	
	public String getMimetypesFile(){
		return new MimetypesFileTypeMap().getContentType(this.f);
	}
	
	public String getTamanio(){
//		String out = m.formatoNumero(Math.round(Math.ceil(this.f.length()/1024.0)))+" mg";
		
		String out = "-";
		double tamanio = this.f.length()/UNIDAD_TAMANIO;
		if (tamanio > 0.009){
			out = ""+m.formatoNumero((this.f.length()/UNIDAD_TAMANIO))+UNIDAD_SIGLA+"";
		}
		return out;
	}
	
	public File getFile(){
		return this.f;
	}

	@Override
	public int compare(Object o1, Object o2) {

		String ff1 = "02";
		String nn1 = ((File) o1).getName();

		if (((File) o1).isDirectory()){
			ff1 = "01";
		}

		String ff2 = "02";
		String nn2 = ((File) o2).getName();

		if (((File) o2).isDirectory()){
			ff2 = "01";
		}
		
		return (ff1+nn1).compareTo(ff2+nn2);
	}
	
}
