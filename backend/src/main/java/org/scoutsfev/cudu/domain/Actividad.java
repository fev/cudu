package org.scoutsfev.cudu.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "grupo_id")
    private String grupoId;

    @NotNull
    @Size(max = 255)
    private String nombre;

    @Column(columnDefinition = "date NOT NULL")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fecha;

    @Size(max = 100)
    private String lugar;

    @Size(max = 20)
    private String precio;

    @Size(max = 512)
    private String notas;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean ramaCualquiera = true;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean ramaColonia = false;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean ramaManada = false;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean ramaExploradores = false;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean ramaExpedicion = false;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean ramaRuta = false;

    @JsonIgnore
    private Timestamp fechaBaja;

    @NotNull
    @Size(max = 130)
    protected String creadaPor;

    protected Actividad() { }

    public Actividad(String grupoId, String nombre, LocalDate fecha, String creadaPor) {
        this.grupoId = grupoId;
        this.nombre = nombre;
        this.fecha = fecha;
        this.creadaPor = creadaPor;
        this.ramaCualquiera = true;
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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
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

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public boolean isRamaCualquiera() {
        return ramaCualquiera;
    }

    public void setRamaCualquiera(boolean ramaCualquiera) {
        this.ramaCualquiera = ramaCualquiera;
    }

    public boolean isRamaColonia() {
        return ramaColonia;
    }

    public void setRamaColonia(boolean ramaColonia) {
        this.ramaColonia = ramaColonia;
    }

    public boolean isRamaManada() {
        return ramaManada;
    }

    public void setRamaManada(boolean ramaManada) {
        this.ramaManada = ramaManada;
    }

    public boolean isRamaExploradores() {
        return ramaExploradores;
    }

    public void setRamaExploradores(boolean ramaExploradores) {
        this.ramaExploradores = ramaExploradores;
    }

    public boolean isRamaExpedicion() {
        return ramaExpedicion;
    }

    public void setRamaExpedicion(boolean ramaExpedicion) {
        this.ramaExpedicion = ramaExpedicion;
    }

    public boolean isRamaRuta() {
        return ramaRuta;
    }

    public void setRamaRuta(boolean ramaRuta) {
        this.ramaRuta = ramaRuta;
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
}
