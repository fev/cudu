package org.scoutsfev.cudu.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.scoutsfev.cudu.domain.dto.ActividadDetalleDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "grupo_id")
    private String grupoId;

    @NotNull
    @Size(max = 255)
    private String nombre;

    @NotNull
    @Column(columnDefinition = "date NOT NULL")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fechaInicio;

    @Column(columnDefinition = "date NULL")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fechaFin;

    @Size(max = 100)
    private String lugar;

    @Size(max = 20)
    private String precio;

    @Size(max = 130)
    private String responsable;

    @Size(max = 512)
    private String notas;

    @Embedded
    private Rama rama = new Rama();

    @JsonIgnore
    private Timestamp fechaBaja;

    @NotNull
    @Size(max = 130)
    protected String creadaPor;

    @Transient
    @JsonProperty
    private List<ActividadDetalleDto> detalle;

    protected Actividad() { }

    public Actividad(String grupoId, String nombre, LocalDate fechaInicio, String creadaPor) {
        this.grupoId = grupoId;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.creadaPor = creadaPor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(String grupoId) {
        this.grupoId = grupoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Rama getRama() {
        return rama;
    }

    public void setRama(Rama rama) {
        this.rama = rama;
    }

    public Timestamp getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Timestamp fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getCreadaPor() {
        return creadaPor;
    }

    public void setCreadaPor(String creadaPor) {
        this.creadaPor = creadaPor;
    }

    public List<ActividadDetalleDto> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<ActividadDetalleDto> detalle) {
        this.detalle = detalle;
    }
}
