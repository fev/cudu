package org.scoutsfev.cudu.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

@Entity
public class Grupo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	@NotNull
	@Size(min = 3, max = 50)
	private String nombre;

	@NotNull
	@Size(min=3, max=300)
	private String calle;
	
	@NotNull
	@Size(min = 1, max = 3)
	private String numero;

	@Size(max = 3)
	private String puerta;

	@Size(max = 3)
	private String escalera;
	
	@NotNull
	@Min(1)
	private Integer codigopostal;
	
	private Integer idprovincia;
	
	private Integer idmunicipio;

	@Past
	private Date aniversario;
	
	@NotNull
	@Size(min = 3, max = 15)
	private String telefono1;

	@Size(max = 15)
	private String telefono2;
	
	@NotNull
	@Size(min = 6, max = 100)
	private String mail;
	
	@Size(max = 300)
	private String web;
	
	private Integer asociacion;
	
	@Size(max = 100)
	private String entidadpatrocinadora;
	
	// HACK para permitir mensajes del estado de la edición, ver
	// controlador de grupos. Se eliminará cuando puedan editarse
	// múltiples grupos, en lugar de usar la BBDD.
	public static enum UiStates { Init, Error, Saved }
	
	@Transient
	private UiStates uiState = UiStates.Init;
	
	public void setUiState(UiStates uiState) {
		this.uiState = uiState;
	}

	public boolean isUiStatedSaved() {
		return uiState == UiStates.Saved;
	}
	// END HACK

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

	public String getTelefono2() {
		return this.telefono2;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getPuerta() {
		return this.puerta;
	}

	public void setPuerta(String puerta) {
		this.puerta = puerta;
	}

	public String getTelefono1() {
		return this.telefono1;
	}

	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}

	public String getWeb() {
		return this.web;
	}

	public void setWeb(String web) {
		this.web = web;
	}
}

