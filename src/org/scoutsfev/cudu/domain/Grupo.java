package org.scoutsfev.cudu.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Grupo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private Date aniversario;

	private Integer asociacion;

	private String calle;

	private Integer codigopostal;

	private String entidadpatrocinadora;

	private String escalera;

	private Integer idmunicipio;

	private Integer idprovincia;

	private String mail;

	private Integer movil;

	private String nombre;

	private Integer numero;

	private Integer puerta;

	private Integer telefono;

	private String web;

    public Grupo() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getAniversario() {
		return this.aniversario;
	}

	public void setAniversario(Date aniversario) {
		this.aniversario = aniversario;
	}

	public Integer getAsociacion() {
		return this.asociacion;
	}

	public void setAsociacion(Integer asociacion) {
		this.asociacion = asociacion;
	}

	public String getCalle() {
		return this.calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public Integer getCodigoPostal() {
		return this.codigopostal;
	}

	public void setCodigoPostal(Integer codigopostal) {
		this.codigopostal = codigopostal;
	}

	public String getEntidadPatrocinadora() {
		return this.entidadpatrocinadora;
	}

	public void setEntidadPatrocinadora(String entidadPatrocinadora) {
		this.entidadpatrocinadora = entidadPatrocinadora;
	}

	public String getEscalera() {
		return this.escalera;
	}

	public void setEscalera(String escalera) {
		this.escalera = escalera;
	}

	public Integer getIdMunicipio() {
		return this.idmunicipio;
	}

	public void setIdMunicipio(Integer idmunicipio) {
		this.idmunicipio = idmunicipio;
	}

	public Integer getIdProvincia() {
		return this.idprovincia;
	}

	public void setIdProvincia(Integer idprovincia) {
		this.idprovincia = idprovincia;
	}

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Integer getMovil() {
		return this.movil;
	}

	public void setMovil(Integer movil) {
		this.movil = movil;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getNumero() {
		return this.numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Integer getPuerta() {
		return this.puerta;
	}

	public void setPuerta(Integer puerta) {
		this.puerta = puerta;
	}

	public Integer getTelefono() {
		return this.telefono;
	}

	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
	}

	public String getWeb() {
		return this.web;
	}

	public void setWeb(String web) {
		this.web = web;
	}
}

