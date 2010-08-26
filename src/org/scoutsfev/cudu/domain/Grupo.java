package org.scoutsfev.cudu.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;
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

	private Integer idMunicipio;
	
	@NotNull
	@Size(min = 3, max = 100)
	private String municipio;
	
	private Integer idProvincia;
	
	@NotNull
	@Size(min = 3, max = 100)
	private String provincia;
	
	@Past
	private Date aniversario;

	@NotNull
	@Size(min = 3, max = 15)
	private String telefono1;
	
	@Size(max = 15)
	private String telefono2;
	
	@NotNull
	@Size(min = 6, max = 100)
	private String email;
	
	@Size(max = 300)
	private String web;
	
	private Integer asociacion;
	
	@Size(max = 100)
	private String entidadpatrocinadora;
	
	@Column(name = "jpa_version")
    @Version
    private int version;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getPuerta() {
		return puerta;
	}

	public void setPuerta(String puerta) {
		this.puerta = puerta;
	}

	public String getEscalera() {
		return escalera;
	}

	public void setEscalera(String escalera) {
		this.escalera = escalera;
	}

	public Integer getCodigopostal() {
		return codigopostal;
	}

	public void setCodigopostal(Integer codigopostal) {
		this.codigopostal = codigopostal;
	}

	public Integer getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdmunicipio(Integer idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public Integer getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(Integer idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public Date getAniversario() {
		return aniversario;
	}

	public void setAniversario(Date aniversario) {
		this.aniversario = aniversario;
	}

	public String getTelefono1() {
		return telefono1;
	}

	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}

	public String getTelefono2() {
		return telefono2;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public Integer getAsociacion() {
		return asociacion;
	}

	public void setAsociacion(Integer asociacion) {
		this.asociacion = asociacion;
	}

	public String getEntidadpatrocinadora() {
		return entidadpatrocinadora;
	}

	public void setEntidadpatrocinadora(String entidadpatrocinadora) {
		this.entidadpatrocinadora = entidadpatrocinadora;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getVersion() {
		return version;
	}
}

