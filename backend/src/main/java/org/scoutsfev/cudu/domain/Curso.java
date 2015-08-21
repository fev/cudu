package org.scoutsfev.cudu.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @NotNull
    @Size(min = 3, max = 100)
    private String titulo;

    @NotNull
    private Timestamp fechaInicioInscripcion;

    @NotNull
    private Timestamp fechaFinInscripcion;

    @Column(columnDefinition = "date NULL", nullable = true)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fechaNacimientoMinima;

    @NotNull
    @Min(1)
    private int plazas;

    @Size(max = 255)
    private String descripcionFechas;

    @Size(max = 255)
    private String descripcionLugar;

    @NotNull
    @Column(nullable = false)
    private boolean visible = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Timestamp getFechaInicioInscripcion() {
        return fechaInicioInscripcion;
    }

    public void setFechaInicioInscripcion(Timestamp fechaInicioInscripcion) {
        this.fechaInicioInscripcion = fechaInicioInscripcion;
    }

    public Timestamp getFechaFinInscripcion() {
        return fechaFinInscripcion;
    }

    public void setFechaFinInscripcion(Timestamp fechaFinInscripcion) {
        this.fechaFinInscripcion = fechaFinInscripcion;
    }

    public LocalDate getFechaNacimientoMinima() {
        return fechaNacimientoMinima;
    }

    public void setFechaNacimientoMinima(LocalDate fechaNacimientoMinima) {
        this.fechaNacimientoMinima = fechaNacimientoMinima;
    }

    public int getPlazas() {
        return plazas;
    }

    public void setPlazas(int plazas) {
        this.plazas = plazas;
    }

    public String getDescripcionFechas() {
        return descripcionFechas;
    }

    public void setDescripcionFechas(String descripcionFechas) {
        this.descripcionFechas = descripcionFechas;
    }

    public String getDescripcionLugar() {
        return descripcionLugar;
    }

    public void setDescripcionLugar(String descripcionLugar) {
        this.descripcionLugar = descripcionLugar;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
