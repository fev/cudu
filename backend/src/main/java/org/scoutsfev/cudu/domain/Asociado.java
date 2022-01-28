package org.scoutsfev.cudu.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.hibernate.validator.constraints.Email;
import org.scoutsfev.cudu.domain.dto.CargoAsociadoDto;
import org.scoutsfev.cudu.domain.validadores.Edad;
import org.scoutsfev.cudu.domain.validadores.ValidarDniNie;
import org.scoutsfev.cudu.domain.validadores.ValidarRama;
import org.scoutsfev.cudu.domain.validadores.ValidarTipo;
import org.scoutsfev.cudu.pdfbuilder.IPrintable;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "asociado")
@ValidarRama
@ValidarTipo
@ValidarDniNie
public class Asociado extends AsociadoAbstracto implements IPrintable {

    @Transient // Sólo para impresión en PDF
    private String rama;

    @Column(name = "grupo_id")
    private String grupoId;

    @OneToMany(mappedBy = "asociado", fetch = FetchType.LAZY)
    protected List<CargoAsociadoDto> cargos;

    @NotNull
    @Edad(max = 70)
    @Column(columnDefinition = "date NOT NULL")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fechaNacimiento;

    @Size(min = 5, max = 100)
    private String direccion;

    @Min(1)
    private Integer codigoPostal;

    @Size(min = 3, max = 100)
    private String municipio;

    @Size(max = 10)
    @Column(nullable = true)
    private String dni;

    @Size(max = 20)
    @Column(nullable = true)
    private String documentacionExtrangera;

    @Size(max = 12)
    private String seguridadSocial;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean certificadoDelitosSexuales = false;

    /*@Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean cursoCovid = false;*/

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean certificadoVoluntariado = false;
   
    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean cursoProteccionInfancia = false;

    private Genero genero;

    @Email
    @Size(max = 100)
    protected String emailContacto;

    @Size(max = 15)
    private String telefonoCasa;

    @Size(max = 15)
    private String telefonoMovil;

    @Column(insertable = false, updatable = false)
    private Timestamp fechaAlta;

    private Timestamp fechaBaja;

    @Column(insertable = false, updatable = false)
    private Timestamp fechaActualizacion;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean tieneSeguroPrivado = false;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean tieneTutorLegal = false;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean padresDivorciados = false;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean hermanosEnElGrupo = false;

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

    @Column(columnDefinition = "TEXT NULL", nullable = true)
    private String notas;

    @Size(max = 128)
    private String profesion;

    @Size(max = 128)
    private String estudios;

    protected Asociado() {
    }

    public Asociado(String grupoId, TipoAsociado tipo, AmbitoEdicion ambitoEdicion, String nombre, String apellidos, LocalDate fechaNacimiento) {
        this.grupoId = grupoId;
        this.tipo = tipo;
        this.ambitoEdicion = ambitoEdicion;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.activo = true;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(String grupoId) {
        this.grupoId = grupoId;
    }

    // Importante, no establecer setCargos, la colección mapea sobre un dto en vista
    public List<CargoAsociadoDto> getCargos() {
        return cargos;
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

    public String getDocumentacionExtrangera() {
        return documentacionExtrangera;
    }

    public void setDocumentacionExtrangera(String documentacionExtrangera) {
        this.documentacionExtrangera = documentacionExtrangera;
    }

    public String getSeguridadSocial() {
        return seguridadSocial;
    }

    public void setSeguridadSocial(String seguridadSocial) {
        this.seguridadSocial = seguridadSocial;
    }

    public Boolean getCertificadoDelitosSexuales() {
        return certificadoDelitosSexuales;
    }

    public void setCertificadoDelitosSexuales(Boolean certificadoDelitosSexuales) {
        this.certificadoDelitosSexuales = certificadoDelitosSexuales;
    }

     /*public Boolean getCursoCovid() {
        return cursoCovid;
    }

    public void setCursoCovid(Boolean cursoCovid) {
        this.cursoCovid = cursoCovid;
    }*/

    public Boolean getCertificadoVoluntariado() {
        return certificadoVoluntariado;
    }

    public void setCertificadoVoluntariado(Boolean certificadoVoluntariado) {
        this.certificadoVoluntariado = certificadoVoluntariado;
    }

    public Boolean getCursoProteccionInfancia() {
        return cursoProteccionInfancia;
    }

    public void setCursoProteccionInfancia(Boolean cursoProteccionInfancia) {
        this.cursoProteccionInfancia = cursoProteccionInfancia;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getEmailContacto() {
        return emailContacto;
    }

    public void setEmailContacto(String emailContacto) {
        this.emailContacto = emailContacto;
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

    public Boolean getHermanosEnElGrupo() {
        return hermanosEnElGrupo;
    }

    public void setHermanosEnElGrupo(Boolean hermanosEnElGrupo) {
        this.hermanosEnElGrupo = hermanosEnElGrupo;
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

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getEstudios() {
        return estudios;
    }

    public void setEstudios(String estudios) {
        this.estudios = estudios;
    }

    public String getRama() {
        return this.rama;
    }

    public void setRama(String rama) {
        this.rama = rama;
    }

     @Override
    public Map<String, String> ToPrintableRow() {
        Map<String, String> diccionario = new HashMap<String, String>();
        diccionario.put("nombre", String.format("%s %s", this.nombre, this.apellidos));

        StringBuilder direccionSb = new StringBuilder();
        direccionSb.append(this.direccion);
        if (this.codigoPostal != null)
            direccionSb.append(String.format(", %s", this.codigoPostal));
        if (this.municipio != null)
            direccionSb.append(String.format(", %s", this.municipio));
        diccionario.put("direccion", direccionSb.toString());

        diccionario.put("rama", this.rama);
        diccionario.put("email", this.emailContacto);
        diccionario.put("telefono", this.telefonoMovil);

        return diccionario;
    }
}
