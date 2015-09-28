package org.scoutsfev.cudu.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.scoutsfev.cudu.domain.dto.MiembroCursoDto;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @NotNull
    @Size(min = 3, max = 100)
    @Column(name = "titulo", nullable = false, length = 128)
    protected String titulo;

    @NotNull
    @Column(name = "fecha_inicio_inscripcion", nullable = false)
    private Timestamp fechaInicioInscripcion;

    @NotNull
    @Column(name = "fecha_fin_inscripcion", nullable = false)
    private Timestamp fechaFinInscripcion;

    @Column(name = "fecha_nacimiento_minima", columnDefinition = "date NULL", nullable = true)
    private Date fechaNacimientoMinima;

    @NotNull
    @Min(1)
    @Column(name = "plazas", nullable = false, precision = 32)
    private int plazas;

    // Las columnas "inscritos" y "disponibles" se rellenan únicamente mediante
    // coonsultas que vienen de jOOQ hasta que quitemos completamente JPA.
    @Transient
    @JsonProperty
    @Column(name = "inscritos")
    private int inscritos;

    @Transient
    @JsonProperty
    @Column(name = "disponibles")
    private int disponibles;

    @Transient
    @JsonProperty
    @Column(name = "usuario_inscrito")
    private boolean usuarioInscrito;

    @Transient
    @JsonProperty
    @Column(name = "usuario_lista_espera")
    private boolean usuarioListaEspera;

    @Size(max = 255)
    @Column(name = "descripcion_fechas", nullable = false)
    private String descripcionFechas;

    @Size(max = 255)
    @Column(name = "descripcion_lugar", nullable = false)
    private String descripcionLugar;

    @NotNull
    @Column(name = "visible", nullable = false)
    private boolean visible = false;

    @Column(name = "coordinador_id", precision = 32, nullable = true)
    private Integer coordinadorId;

    @Transient
    @JsonProperty
    private List<MiembroCursoDto> formadores = new ArrayList<>();

    @Transient
    @JsonProperty
    private List<MiembroCursoDto> participantes = new ArrayList<>();

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

    @JsonSerialize(using = LocalDateSerializer.class)
    public LocalDate getFechaNacimientoMinima() {
        return fechaNacimientoMinima.toLocalDate();
    }

    @JsonDeserialize(using = LocalDateDeserializer.class)
    public void setFechaNacimientoMinima(LocalDate fechaNacimientoMinima) {
        this.fechaNacimientoMinima = Date.valueOf(fechaNacimientoMinima);
    }

    public int getPlazas() {
        return plazas;
    }

    public void setPlazas(int plazas) {
        this.plazas = plazas;
    }

    public int getInscritos() {
        return inscritos;
    }

    public void setInscritos(int inscritos) {
        this.inscritos = inscritos;
    }

    public int getDisponibles() {
        return disponibles;
    }

    public void setDisponibles(int disponibles) {
        this.disponibles = disponibles;
    }

    public boolean isUsuarioInscrito() {
        return usuarioInscrito;
    }

    public void setUsuarioInscrito(boolean usuarioInscrito) {
        this.usuarioInscrito = usuarioInscrito;
    }

    public boolean isUsuarioListaEspera() {
        return usuarioListaEspera;
    }

    public void setUsuarioListaEspera(boolean usuarioListaEspera) {
        this.usuarioListaEspera = usuarioListaEspera;
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

    public Integer getCoordinadorId() {
        return coordinadorId;
    }

    public void setCoordinadorId(Integer coordinadorId) {
        this.coordinadorId = coordinadorId;
    }

    public List<MiembroCursoDto> getFormadores() {
        return formadores;
    }

    public void setFormadores(List<MiembroCursoDto> formadores) {
        this.formadores = formadores;
    }

    public List<MiembroCursoDto> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<MiembroCursoDto> participantes) {
        this.participantes = participantes;
    }
}
