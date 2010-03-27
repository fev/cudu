package org.scoutsfev.cudu.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Formula;

@Entity
@SecondaryTable(name = "asociado_rama", 
		pkJoinColumns = @PrimaryKeyJoinColumn(name = "idAsociado", referencedColumnName = "id"))
public class Asociado implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull
	@Size(min = 3, max = 30)
	private String nombre;
	
	@NotNull
	@Size(min = 3, max = 50)
	private String primerapellido;
	
	@Size(max = 50)
	private String segundoapellido;
	
	@Formula("nombre || ' ' || primerapellido || coalesce(' ' || segundoapellido, '')")
	private String nombreCompleto;
	
	@Temporal(TemporalType.DATE)
	@NotNull
	@Past
	private Date fechanacimiento;

	@NotNull
	@Size(min = 3, max = 100)
	private String calle;
	
	@NotNull
	@Size(min = 1, max = 3)
	private String numero;

	@Size(max = 3)
	private String escalera;

	@Size(max = 3)
	private String puerta;
	
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

	// @Pattern num(-/\)letra, numLetra
	@Column(nullable = true)
	@Size(max=9)
	private String dni;
	
	// @Pattern
	@Size(max = 100)
	private String email;

	@Size(max = 12)
	private String seguridadsocial;

	@NotNull
	// @Pattern(regexp = "[MF]")
	private char sexo;
	
	@Size(max = 15)
	private String telefonocasa;
	
	@Size(max = 15)
	private String telefonomovil;

	@Column(insertable = false, updatable = false)
	private Timestamp fechaalta;

	private Timestamp fechabaja;

	private String idGrupo;
	private char tipo;

	private Boolean tieneseguroprivado;
	private Boolean tienetutorlegal;

	@Column(name = "madre_nombre")
	@Size(max = 250)
	private String madreNombre;

	@Column(name = "madre_telefono")
	@Size(max = 15)
	private String madreTelefono;

	@Column(name = "madre_email")
	@Size(max = 100)
	//@Pattern(regexp = "")
	private String madreEmail;


	@Column(name = "padre_nombre")
	@Size(max = 250)
	private String padreNombre;

	@Column(name = "padre_telefono")
	@Size(max = 15)
	private String padreTelefono;

	@Column(name = "padre_email")
	@Size(max = 100)
	//@Pattern(regexp = "")
	private String padreEmail;

	@Column(table = "asociado_rama", name = "rama")
	private char[] unidades;

	public char[] getUnidades() {
		return this.unidades;
	}

	@Column(name = "jpa_version")
    @Version
    private int version;
	
	@OneToMany(mappedBy = "asociado", fetch = FetchType.EAGER)
	private Set<AsociadoRama> ramas;

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

	public String getIdGrupo() {
		return this.idGrupo;
	}

	public void setIdGrupo(String idGrupo) {
		this.idGrupo = idGrupo;
	}

	public Integer getIdMunicipio() {
		return this.idMunicipio;
	}

	public void setIdMunicipio(Integer idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public Integer getIdProvincia() {
		return this.idProvincia;
	}

	public void setIdProvincia(Integer idProvincia) {
		this.idProvincia = idProvincia;
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

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
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

	public char getSexo() {
		return this.sexo;
	}

	public void setSexo(char sexo) {
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

	public void setVersion(int version) {
		this.version = version;
	}

	public int getVersion() {
		return version;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}
}