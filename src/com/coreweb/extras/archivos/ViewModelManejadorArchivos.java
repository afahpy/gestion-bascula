package com.coreweb.extras.archivos;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.input.ReaderInputStream;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zkmax.zul.Filedownload;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Include;
import org.zkoss.zul.Textbox;

import com.coreweb.Config;
import com.coreweb.util.Misc;

public class ViewModelManejadorArchivos {

	Misc m = new Misc();

	boolean modoAdm = false;
	boolean modoCrearDirectorio = false;
	boolean modoVerArchivo = false;
	String directorioNuevo = "";

	boolean estaEnRaiz = true;

	String direBase = "";
	String direCCWeb = "";

	File fileCC = null;

	String archivoCC = "-";
	
	boolean noHayArchivos = true;

	// la lista de archivos corrientes
	List<TipoFileHtml> listaArchivos = new ArrayList<>();

	public String getDirectorioNuevo() {
		return directorioNuevo;
	}

	public void setDirectorioNuevo(String directorioNuevo) {
		this.directorioNuevo = directorioNuevo;
	}

	public boolean isModoCrearDirectorio() {
		return modoCrearDirectorio;
	}

	public void setModoCrearDirectorio(boolean modoCrearDirectorio) {
		this.modoCrearDirectorio = modoCrearDirectorio;
	}

	/*
	 * @Init(superclass = true) public void initViewModelManejadorArchivos(
	 * 
	 * @ExecutionArgParam("direBase") String
	 * direBase, @ExecutionArgParam("modoAdm") boolean modoAdm) { this.direBase
	 * = direBase; this.fileCC = new File(this.direBase); this.modoAdm =
	 * modoAdm; this.actualizarArchivos(); }
	 */

	@Init(superclass = true)
	public void initViewModelManejadorArchivos() {
		String direBase = (String) Executions.getCurrent().getAttribute("direBase");
		boolean modoAdm = (boolean) Executions.getCurrent().getAttribute("modoAdm");
		this.direBase = direBase;
		this.fileCC = new File(this.direBase);
		this.modoAdm = modoAdm;
		this.actualizarArchivos();
	}

	@AfterCompose(superclass = true)
	public void afterComposeViewModelManejadorArchivos() {
	}

	@Command
	@NotifyChange("*")
	public void clickFile(@BindingParam("file") TipoFileHtml file) throws Exception {
		if (file.esDirectorio() == true) {
			this.fileCC = file.getFile();
			this.actualizarArchivos();
			// BindUtils.postNotifyChange(null, null, this, "*");

		} else {
			// es archivo
			// Filedownload.save(file.getInputStream(), file.getMimetypesFile(),
			// file.getNombre());
			/*
			 * System.out.println(
			 * "------------------------------this.getDireCCWeb(): " +
			 * this.getDireCCWeb()); System.out.println(
			 * "------------------------------file.getNombre(): " +
			 * file.getNombre()); System.out.println(
			 * "------------------------------real: " +
			 * Config.DIRECTORIO_BASE_REAL); System.out.println(
			 * "------------------------------web : " +
			 * Config.DIRECTORIO_BASE_WEB);
			 */

			this.archivoCC = this.getDireCCWeb() + "/" + file.getNombre();

			Component compTool = Path.getComponent("/idManejadorArchivos");
			Iframe iframe = (Iframe) compTool.getFellow("idIframeArchivo");

			AMedia media = new AMedia(file.getFile(), null, null);
			iframe.setContent(media);

			iframe.setWidth("100%");
			iframe.setStyle("border: none;overflow:hidden;");
			iframe.setScrolling("yes");
			// Here is the trick. The script sets the frame's height as it's
			// content's scrollHeight plus 8 maybe 10 pixels.
			iframe.setWidgetListener("onBind", "var f = document.getElementById('" + iframe.getUuid()
					+ "');f.height=f.contentWindow.document.body.scrollHeight+8;");

			this.cambiarModoverArchivos();

			/*
			 * System.out.println("------------------------------media : " +
			 * media.getName()); System.out.println(
			 * "------------------------------iframe.getSrc() : " +
			 * iframe.getSrc());
			 * 
			 * System.out.println(
			 * "Executions.getCurrent().encodeURL(file.getFile().getAbsolutePath()):"
			 * +
			 * Executions.getCurrent().encodeURL(file.getFile().getAbsolutePath(
			 * )));
			 * 
			 */
			// String fileReal = Config.DIRECTORIO_BASE_REAL+"/";

			/*
			 * this.archivoCC = this.getDireCCWeb()+ "/"+file.getNombre();
			 * 
			 * Component compTool = Path.getComponent("/idManejadorArchivos");
			 * Iframe iframe = (Iframe) compTool.getFellow("idIframeArchivo");
			 * AMedia media = new AMedia(file.getFile(), null, null);
			 * iframe.setContent(media); System.out.println(
			 * "------------------------------media.getName():"+media.getName())
			 * ;
			 */

		}

	}

	@Command
	public void eliminarFile(@BindingParam("file") TipoFileHtml file) {
		String msg = "Está seguro que desea eliminar el archivo/directorio\n" + file.getNombre();
		boolean siDel = this.m.mensajeSiNo(msg);
		if (siDel == true) {
			System.out.println(file.getNombre());
			this.deleteFolder(file.getFile());
			this.actualizarArchivos();
			BindUtils.postNotifyChange(null, null, this, "*");
		} else {
			System.out.println("----no");
		}

	}

	private void deleteFolder(File folder) {
		String nn = folder.getName();
		File[] files = folder.listFiles();
		if (files != null) { // some JVMs return null for empty dirs
			for (File f : files) {
				if (f.isDirectory()) {
					deleteFolder(f);
				} else {
					f.delete();
				}
			}
		}
		folder.delete();

	}

	@Command
	public void agregarFile(@BindingParam("evt") UploadEvent event) {
		String path = this.fileCC.getPath() + "/" + event.getMedia().getName();

		if (event.getMedia().isBinary() == true) {
			m.uploadFile(path, (InputStream) new ByteArrayInputStream(event.getMedia().getByteData()));
		} else {
			InputStream s = new BufferedInputStream(new ReaderInputStream(event.getMedia().getReaderData()));
			m.uploadFile(path, s);
		}
		this.actualizarArchivos();
		BindUtils.postNotifyChange(null, null, this, "*");
	}

	@Command
	public void volverNivel() {
		if (this.getDireCCWeb().compareTo("./") != 0) {
			this.fileCC = this.fileCC.getParentFile();
			this.actualizarArchivos();
			BindUtils.postNotifyChange(null, null, this, "*");
		} else {
			// no hace nada, está en el raiz
		}
	}

	@Command
	@NotifyChange("*")
	public void crearDirectorio() {
		this.directorioNuevo = "";
		// invierte el estado entre visible/invisible
		this.setModoCrearDirectorio(!this.isModoCrearDirectorio());
	}

	@Command
	@NotifyChange("*")
	public void grabarCrearDirectorio() {
		if (this.directorioNuevo.trim().length() > 0) {
			File f = new File(this.fileCC.getPath() + "/" + this.directorioNuevo);
			f.mkdir();
			this.actualizarArchivos();
			// ocultar el crear directorio
			this.setModoCrearDirectorio(false);
			BindUtils.postNotifyChange(null, null, this, "*");
		}
	}

	@Command
	@NotifyChange("*")
	public void cerrarModoverArchivos() throws FileNotFoundException {

		// borrar el contenido
		Component compTool = Path.getComponent("/idManejadorArchivos");
		Iframe iframe = (Iframe) compTool.getFellow("idIframeArchivo");

		File ff = new File(Config.DIRECTORIO_BASE_REAL + "" + Config.MANEJADOR_ARCHIVOS_DESCARGA);
		AMedia mediaAux = new AMedia(ff, null, null);
		iframe.setContent(mediaAux);

		this.cambiarModoverArchivos();
	}

	public void cambiarModoverArchivos() {
		this.modoVerArchivo = !this.modoVerArchivo;
	}

	public String getDireBase() {
		return direBase;
	}

	public void setDireBase(String direBase) {
		this.direBase = direBase;
	}

	private void actualizarArchivos() {

		this.listaArchivos = new ArrayList<>();

		File[] fList = this.fileCC.listFiles();
		if (fList != null) {
			Arrays.sort(fList, new TipoFileHtml(null));

			for (int i = 0; i < fList.length; i++) {
				File ff = fList[i];
				this.listaArchivos.add(new TipoFileHtml(ff));
			}
		}
	}

	public List<TipoFileHtml> getListaArchivos() {
		return listaArchivos;
	}

	public void setListaArchivos(List<TipoFileHtml> listaArchivos) {
		this.listaArchivos = listaArchivos;
	}

	public String getDireCCWeb() {
		this.direCCWeb = "./";
		try {
			String cc = this.fileCC.getPath();
			int pIni = this.direBase.length();
			this.direCCWeb = "./" + cc.substring(pIni);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return direCCWeb;
	}

	public void setDireCCWeb(String direCCWeb) {
		this.direCCWeb = direCCWeb;
	}

	public boolean isModoVerArchivo() {
		return modoVerArchivo;
	}

	public void setModoVerArchivo(boolean modoVerArchivo) {
		this.modoVerArchivo = modoVerArchivo;
	}

	public static void main(String[] args) throws Exception {
		String f1 = "/home/daniel/datos/varios/propuestas/scg33/proyecto-scg33/directorios/logia4/";

		File ff = new File(f1);

		File[] fa = ff.listFiles();
		for (int i = 0; i < fa.length; i++) {
			File file = fa[i];
			String out = "" + Math.round(Math.ceil(file.length() / 1024.0));
			System.out.println(file.getName() + "(" + out + ")");
		}
		System.out.println("==========================");
		Arrays.sort(fa, new TipoFileHtml(ff));
		for (int i = 0; i < fa.length; i++) {
			File file = fa[i];
			System.out.println(file.getName());
		}

	}

	public boolean isModoAdm() {
		return modoAdm;
	}

	public void setModoAdm(boolean modoAdm) {
		this.modoAdm = modoAdm;
	}

	public String getArchivoCC() {
		return archivoCC;
	}

	public void setArchivoCC(String archivoCC) {
		this.archivoCC = archivoCC;
	}

	public boolean isEstaEnRaiz() {
		return this.getDireCCWeb().compareTo("./") == 0;
		// return estaEnRaiz;
	}

	public boolean isNoHayArchivos() {
		return this.listaArchivos.size() == 0;
	}


	// public void setEstaEnRaiz(boolean estaEnRaiz) {
	// this.estaEnRaiz = estaEnRaiz;
	// }

}

// ===================================================
// ===================================================
// ===================================================
