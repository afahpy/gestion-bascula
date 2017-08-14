package com.logia.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.coreweb.domain.Domain;
import com.coreweb.domain.Tipo;
import com.coreweb.domain.Usuario;

public class Mason extends Domain {

	private String nombre = "";
	private Tipo grado;// Aprendiz, companhero, maestro
	private Usuario usuarioSistema;
	private Tipo cargo;
	private Set<CapitacionHH> capitacionesHH = new HashSet<CapitacionHH>();

	private Tipo estado; // activo, cotizante, suspendido
	private String nombres = "";
	private String apellidos = "";
	private Date fechaNacimiento;
	private String lugarNacimiento = "";
	private String nacionalidad = "";
	private String estadoCivil = "";
	private String ci = "";
	private String profesion = "";
	private String especializacion = "";
	private String direccionParticular = "";
	private String telefonoParticular = "";
	private String celular = "";
	private String email = "";
	private String lugarDeTrabajo = "";
	private String cargoQueOcupa = "";
	private String direccionLaboral = "";
	private String telefonoLaboral = "";
	private String emailLaboral = "";
	private String tipoDeSangre = "";
	private String conyugeNombreApellido = "";
	private Date conyugeFechaNacimiento;
	private String conyugeNacionalidad = "";
	private String conyugeProfesion = "";
	private String conyugeActividadLaboral = "";
	private String conyugeCargoQueOcupa = "";
	private String conyugeTelefonolaboral = "";
	private String conyugeTipoDeSangre = "";
	private String datosDeHijos = "";
	private Date fechaIniciacionRegularizacion;
	private Date fechaAumentoSalario;
	private Date fechaExaltacion;
	private Date fechaPlacetYQuite;
	private Date fechaFechaSuspension;
	private Date fechaExpulsion;

	public Tipo getGrado() {
		return grado;
	}

	public void setGrado(Tipo grado) {
		this.grado = grado;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Usuario getUsuarioSistema() {
		return usuarioSistema;
	}

	public void setUsuarioSistema(Usuario usuarioSistema) {
		this.usuarioSistema = usuarioSistema;
	}

	public Tipo getCargo() {
		return cargo;
	}

	public void setCargo(Tipo cargo) {
		this.cargo = cargo;
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}

	public Set<CapitacionHH> getCapitacionesHH() {
		return capitacionesHH;
	}

	public void setCapitacionesHH(Set<CapitacionHH> capitacionesHH) {
		this.capitacionesHH = capitacionesHH;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getLugarNacimiento() {
		return lugarNacimiento;
	}

	public void setLugarNacimiento(String lugarNacimiento) {
		this.lugarNacimiento = lugarNacimiento;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getCi() {
		return ci;
	}

	public void setCi(String ci) {
		this.ci = ci;
	}

	public String getProfesion() {
		return profesion;
	}

	public void setProfesion(String profesion) {
		this.profesion = profesion;
	}

	public String getEspecializacion() {
		return especializacion;
	}

	public void setEspecializacion(String especializacion) {
		this.especializacion = especializacion;
	}

	public String getTelefonoParticular() {
		return telefonoParticular;
	}

	public void setTelefonoParticular(String telefonoParticular) {
		this.telefonoParticular = telefonoParticular;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLugarDeTrabajo() {
		return lugarDeTrabajo;
	}

	public void setLugarDeTrabajo(String lugarDeTrabajo) {
		this.lugarDeTrabajo = lugarDeTrabajo;
	}

	public String getCargoQueOcupa() {
		return cargoQueOcupa;
	}

	public void setCargoQueOcupa(String cargoQueOcupa) {
		this.cargoQueOcupa = cargoQueOcupa;
	}

	public String getTelefonoLaboral() {
		return telefonoLaboral;
	}

	public void setTelefonoLaboral(String telefonoLaboral) {
		this.telefonoLaboral = telefonoLaboral;
	}

	public String getEmailLaboral() {
		return emailLaboral;
	}

	public void setEmailLaboral(String emailLaboral) {
		this.emailLaboral = emailLaboral;
	}

	public String getTipoDeSangre() {
		return tipoDeSangre;
	}

	public void setTipoDeSangre(String tipoDeSangre) {
		this.tipoDeSangre = tipoDeSangre;
	}

	public String getConyugeNombreApellido() {
		return conyugeNombreApellido;
	}

	public void setConyugeNombreApellido(String conyugeNombreApellido) {
		this.conyugeNombreApellido = conyugeNombreApellido;
	}

	public Date getConyugeFechaNacimiento() {
		return conyugeFechaNacimiento;
	}

	public void setConyugeFechaNacimiento(Date conyugeFechaNacimiento) {
		this.conyugeFechaNacimiento = conyugeFechaNacimiento;
	}

	public String getConyugeNacionalidad() {
		return conyugeNacionalidad;
	}

	public void setConyugeNacionalidad(String conyugeNacionalidad) {
		this.conyugeNacionalidad = conyugeNacionalidad;
	}

	public String getConyugeProfesion() {
		return conyugeProfesion;
	}

	public void setConyugeProfesion(String conyugeProfesion) {
		this.conyugeProfesion = conyugeProfesion;
	}

	public String getConyugeActividadLaboral() {
		return conyugeActividadLaboral;
	}

	public void setConyugeActividadLaboral(String conyugeActividadLaboral) {
		this.conyugeActividadLaboral = conyugeActividadLaboral;
	}

	public String getConyugeCargoQueOcupa() {
		return conyugeCargoQueOcupa;
	}

	public void setConyugeCargoQueOcupa(String conyugeCargoQueOcupa) {
		this.conyugeCargoQueOcupa = conyugeCargoQueOcupa;
	}

	public String getConyugeTelefonolaboral() {
		return conyugeTelefonolaboral;
	}

	public void setConyugeTelefonolaboral(String conyugeTelefonolaboral) {
		this.conyugeTelefonolaboral = conyugeTelefonolaboral;
	}

	public String getConyugeTipoDeSangre() {
		return conyugeTipoDeSangre;
	}

	public void setConyugeTipoDeSangre(String conyugeTipoDeSangre) {
		this.conyugeTipoDeSangre = conyugeTipoDeSangre;
	}

	public String getDatosDeHijos() {
		return datosDeHijos;
	}

	public void setDatosDeHijos(String datosDeHijos) {
		this.datosDeHijos = datosDeHijos;
	}

	public Date getFechaAumentoSalario() {
		return fechaAumentoSalario;
	}

	public void setFechaAumentoSalario(Date fechaAumentoSalario) {
		this.fechaAumentoSalario = fechaAumentoSalario;
	}

	public Date getFechaExaltacion() {
		return fechaExaltacion;
	}

	public void setFechaExaltacion(Date fechaExaltacion) {
		this.fechaExaltacion = fechaExaltacion;
	}

	public Date getFechaPlacetYQuite() {
		return fechaPlacetYQuite;
	}

	public void setFechaPlacetYQuite(Date fechaPlacetYQuite) {
		this.fechaPlacetYQuite = fechaPlacetYQuite;
	}

	public Date getFechaFechaSuspension() {
		return fechaFechaSuspension;
	}

	public void setFechaFechaSuspension(Date fechaFechaSuspension) {
		this.fechaFechaSuspension = fechaFechaSuspension;
	}

	public Date getFechaExpulsion() {
		return fechaExpulsion;
	}

	public void setFechaExpulsion(Date fechaExpulsion) {
		this.fechaExpulsion = fechaExpulsion;
	}

	public Tipo getEstado() {
		return estado;
	}

	public void setEstado(Tipo estado) {
		this.estado = estado;
	}

	public String getDireccionParticular() {
		return direccionParticular;
	}

	public void setDireccionParticular(String direccionParticular) {
		this.direccionParticular = direccionParticular;
	}

	public String getDireccionLaboral() {
		return direccionLaboral;
	}

	public void setDireccionLaboral(String direccionLaboral) {
		this.direccionLaboral = direccionLaboral;
	}

	public Date getFechaIniciacionRegularizacion() {
		return fechaIniciacionRegularizacion;
	}

	public void setFechaIniciacionRegularizacion(Date fechaIniciacionRegularizacion) {
		this.fechaIniciacionRegularizacion = fechaIniciacionRegularizacion;
	}

}
