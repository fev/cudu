package org.scoutsfev.cudu.domain;

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "asociado")
public class Asociado extends AsociadoAbstracto {

    public static final String REGEX_NIF = "(\\d{8}|[KLMXYZ]\\d{7})-?\\w";

    @NotNull
    private TipoAsociado tipo;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean ramaCastores = false;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean ramaLobatos = false;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean ramaExploradores = false;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean ramaPioneros = false;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean ramaRuta = false;

    @Past
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    @NotNull
    @Size(min = 5, max = 200)
    @Column(name = "calle")
    private String direccion;

    @NotNull
    @Min(1)
    private Integer codigoPostal;

    @NotNull
    @Size(min = 3, max = 100)
    private String municipio;

    @Size(max=10, min=9)
    @Column(nullable = true)
    @Pattern(regexp = REGEX_NIF)
    private String dni;

    @Size(max = 12)
    private String seguridadSocial;

    @NotNull
    private Sexo sexo;

    @Size(max = 15)
    private String telefonoCasa;

    @Size(max = 15)
    private String telefonoMovil;

    @Column(insertable = false, updatable = false)
    private Timestamp fechaAlta;

    private Timestamp fechaBaja;

    @Column(insertable = false, updatable = false)
    private Timestamp fechaActualizacion;

    private Boolean tieneSeguroPrivado;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean tieneTutorLegal = false;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean padresDivorciados = false;

    @Column(name = "madre_nombre")
    @Size(max = 250)
    private String nombreMadre;

    @Column(name = "madre_telefono")
    @Size(max = 15)
    private String telefonoMadre;

    @Column(name = "madre_email")
    @Size(max = 100)
    @Email
    private String emailMadre;

    @Column(name = "padre_nombre")
    @Size(max = 250)
    private String nombrePadre;

    @Column(name = "padre_telefono")
    @Size(max = 15)
    private String telefonoPadre;

    @Column(name = "padre_email")
    @Size(max = 100)
    @Email
    private String emailPadre;

    @Lob
    @Column(name = "notas")
    private String notas;

    @NotNull
    @Column(nullable = false)
    private boolean activo = true;

    protected Asociado() { }

    public Asociado(Grupo grupo, TipoAsociado tipo, String nombre, String apellidos, Date fechaNacimiento,
                    String direccion, Integer codigoPostal, String municipio, Sexo sexo) {
        this.grupo = grupo;
        this.tipo = tipo;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;
        this.municipio = municipio;
        this.sexo = sexo;
        this.activo = true;
    }

    public TipoAsociado getTipo() {
        return tipo;
    }

    public void setTipo(TipoAsociado tipo) {
        this.tipo = tipo;
    }

    public boolean isRamaCastores() {
        return ramaCastores;
    }

    public void setRamaCastores(boolean ramaCastores) {
        this.ramaCastores = ramaCastores;
    }

    public boolean isRamaLobatos() {
        return ramaLobatos;
    }

    public void setRamaLobatos(boolean ramaLobatos) {
        this.ramaLobatos = ramaLobatos;
    }

    public boolean isRamaExploradores() {
        return ramaExploradores;
    }

    public void setRamaExploradores(boolean ramaExploradores) {
        this.ramaExploradores = ramaExploradores;
    }

    public boolean isRamaPioneros() {
        return ramaPioneros;
    }

    public void setRamaPioneros(boolean ramaPioneros) {
        this.ramaPioneros = ramaPioneros;
    }

    public boolean isRamaRuta() {
        return ramaRuta;
    }

    public void setRamaRuta(boolean ramaRuta) {
        this.ramaRuta = ramaRuta;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(Integer codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getSeguridadSocial() {
        return seguridadSocial;
    }

    public void setSeguridadSocial(String seguridadSocial) {
        this.seguridadSocial = seguridadSocial;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String getTelefonoCasa() {
        return telefonoCasa;
    }

    public void setTelefonoCasa(String telefonoCasa) {
        this.telefonoCasa = telefonoCasa;
    }

    public String getTelefonoMovil() {
        return telefonoMovil;
    }

    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public Timestamp getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Timestamp fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Timestamp getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Timestamp fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public Timestamp getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Timestamp fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Boolean getTieneSeguroPrivado() {
        return tieneSeguroPrivado;
    }

    public void setTieneSeguroPrivado(Boolean tieneSeguroPrivado) {
        this.tieneSeguroPrivado = tieneSeguroPrivado;
    }

    public Boolean getTieneTutorLegal() {
        return tieneTutorLegal;
    }

    public void setTieneTutorLegal(Boolean tieneTutorLegal) {
        this.tieneTutorLegal = tieneTutorLegal;
    }

    public Boolean getPadresDivorciados() {
        return padresDivorciados;
    }

    public void setPadresDivorciados(Boolean padresDivorciados) {
        this.padresDivorciados = padresDivorciados;
    }

    public String getNombreMadre() {
        return nombreMadre;
    }

    public void setNombreMadre(String nombreMadre) {
        this.nombreMadre = nombreMadre;
    }

    public String getTelefonoMadre() {
        return telefonoMadre;
    }

    public void setTelefonoMadre(String telefonoMadre) {
        this.telefonoMadre = telefonoMadre;
    }

    public String getEmailMadre() {
        return emailMadre;
    }

    public void setEmailMadre(String emailMadre) {
        this.emailMadre = emailMadre;
    }

    public String getNombrePadre() {
        return nombrePadre;
    }

    public void setNombrePadre(String nombrePadre) {
        this.nombrePadre = nombrePadre;
    }

    public String getTelefonoPadre() {
        return telefonoPadre;
    }

    public void setTelefonoPadre(String telefonoPadre) {
        this.telefonoPadre = telefonoPadre;
    }

    public String getEmailPadre() {
        return emailPadre;
    }

    public void setEmailPadre(String emailPadre) {
        this.emailPadre = emailPadre;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}