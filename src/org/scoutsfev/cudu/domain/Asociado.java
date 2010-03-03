package org.scoutsfev.cudu.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Asociado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	private String calle;

	private Integer codigopostal;

	private String dni;

	private String email;

	private String escalera;

	private Timestamp fechaalta;

	private Timestamp fechabaja;

	@Temporal(TemporalType.DATE)
	private Date fechanacimiento;

	private String idgrupo;

	private Integer idmunicipio;

	private Integer idprovincia;

	@Column(name = "madre_nombre")
	private String madreNombre;

	@Column(name = "madre_telefono")
	private String madreTelefono;

	@Column(name = "madre_email")
	private String madreEmail;

	private String municipio;

	@NotNull
	@Size(min = 3, max = 30)
	private String nombre;

	@Column(name = "padre_nombre")
	private String padreNombre;

	@Column(name = "padre_telefono")
	private String padreTelefono;

	@Column(name = "padre_email")
	private String padreEmail;

	private String patio;

	@NotNull
	@Size(min = 3, max = 50)
	private String primerapellido;

	private String provincia;

	private String puerta;

	@NotNull
	@Size(min = 3, max = 50)
	private String segundoapellido;

	private String seguridadsocial;

	private String sexo;

	private String telefonocasa;

	private String telefonomovil;

	private Boolean tieneseguroprivado;

	private Boolean tienetutorlegal;

	private char tipo;

	@OneToMany(mappedBy = "asociado", fetch = FetchType.EAGER)
	private Set<AsociadoRama> ramas;

	public Asociado() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCalle() {
		return this.calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public Integer getCodigopostal() {
		return this.codigopostal;
	}

	public void setCodigopostal(Integer codigopostal) {
		this.codigopostal = codigopostal;
	}

	public String getDni() {
		return this.dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEscalera() {
		return this.escalera;
	}

	public void setEscalera(String escalera) {
		this.escalera = escalera;
	}

	public Timestamp getFechaalta() {
		return this.fechaalta;
	}

	public void setFechaalta(Timestamp fechaalta) {
		this.fechaalta = fechaalta;
	}

	public Timestamp getFechabaja() {
		return this.fechabaja;
	}

	public void setFechabaja(Timestamp fechabaja) {
		this.fechabaja = fechabaja;
	}

	public Date getFechanacimiento() {
		return this.fechanacimiento;
	}

	public void setFechanacimiento(Date fechanacimiento) {
		this.fechanacimiento = fechanacimiento;
	}

	public String getIdgrupo() {
		return this.idgrupo;
	}

	public void setIdgrupo(String idgrupo) {
		this.idgrupo = idgrupo;
	}

	public Integer getIdmunicipio() {
		return this.idmunicipio;
	}

	public void setIdmunicipio(Integer idmunicipio) {
		this.idmunicipio = idmunicipio;
	}

	public Integer getIdprovincia() {
		return this.idprovincia;
	}

	public void setIdprovincia(Integer idprovincia) {
		this.idprovincia = idprovincia;
	}

	public String getMadreNombre() {
		return this.madreNombre;
	}

	public void setMadreNombre(String madreNombre) {
		this.madreNombre = madreNombre;
	}

	public String getMadreTelefono() {
		return this.madreTelefono;
	}

	public void setMadreTelfono(String madreTelefono) {
		this.madreTelefono = madreTelefono;
	}

	public String getMadreEmail() {
		return this.madreEmail;
	}

	public void setMadreEmail(String madreEmail) {
		this.madreEmail = madreEmail;
	}

	public String getMunicipio() {
		return this.municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPadreNombre() {
		return this.padreNombre;
	}

	public void setPadreNombre(String padreNombre) {
		this.padreNombre = padreNombre;
	}

	public String getPadreTelefono() {
		return this.padreTelefono;
	}

	public void setPadreTelfono(String padreTelefono) {
		this.padreTelefono = padreTelefono;
	}

	public String getPadreEmail() {
		return this.padreEmail;
	}

	public void setPadreEmail(String padreEmail) {
		this.padreEmail = padreEmail;
	}

	public String getPatio() {
		return this.patio;
	}

	public void setPatio(String patio) {
		this.patio = patio;
	}

	public String getPrimerapellido() {
		return this.primerapellido;
	}

	public void setPrimerapellido(String primerapellido) {
		this.primerapellido = primerapellido;
	}

	public String getProvincia() {
		return this.provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getPuerta() {
		return this.puerta;
	}

	public void setPuerta(String puerta) {
		this.puerta = puerta;
	}

	public String getSegundoapellido() {
		return this.segundoapellido;
	}

	public void setSegundoapellido(String segundoapellido) {
		this.segundoapellido = segundoapellido;
	}

	public String getSeguridadsocial() {
		return this.seguridadsocial;
	}

	public void setSeguridadsocial(String seguridadsocial) {
		this.seguridadsocial = seguridadsocial;
	}

	public String getSexo() {
		return this.sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getTelefonocasa() {
		return this.telefonocasa;
	}

	public void setTelefonocasa(String telefonocasa) {
		this.telefonocasa = telefonocasa;
	}

	public String getTelefonomovil() {
		return this.telefonomovil;
	}

	public void setTelefonomovil(String telefonomovil) {
		this.telefonomovil = telefonomovil;
	}

	public Boolean getTieneseguroprivado() {
		return this.tieneseguroprivado;
	}

	public void setTieneseguroprivado(Boolean tieneseguroprivado) {
		this.tieneseguroprivado = tieneseguroprivado;
	}

	public Boolean getTienetutorlegal() {
		return this.tienetutorlegal;
	}

	public void setTienetutorlegal(Boolean tienetutorlegal) {
		this.tienetutorlegal = tienetutorlegal;
	}

	public char getTipo() {
		return this.tipo;
	}

	public void setTipo(char tipo) {
		this.tipo = tipo;
	}

	public Set<AsociadoRama> getRamas() {
		return this.ramas;
	}

	public void setRamas(Set<AsociadoRama> ramas) {
		this.ramas = ramas;
	}
}