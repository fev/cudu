package org.scoutsfev.cudu.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.hibernate.annotations.Immutable;
import org.scoutsfev.cudu.domain.TipoAsociado;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "dto_miembros_curso")
@Immutable
public class MiembroCursoDto implements Serializable {

    @Id
    private int cursoId;

    @Id
    private int id;

    // TODO Crear enum en algún momento
    @Id // [F]ormador o [P]articipante
    protected String tipoMiembro;

    protected Integer secuenciaInscripcion;
    protected TipoAsociado tipo;
    private String nombreCompleto;
    private String nombreGrupo;
    private String telefono;
    private String email;

    @Column(columnDefinition = "date NOT NULL")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fechaNacimiento;

    public int getCursoId() {
        return cursoId;
    }

    public void setCursoId(int cursoId) {
        this.cursoId = cursoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipoMiembro() {
        return tipoMiembro;
    }

    public void setTipoMiembro(String tipoMiembro) {
        this.tipoMiembro = tipoMiembro;
    }

    public Integer getSecuenciaInscripcion() {
        return secuenciaInscripcion;
    }

    public void setSecuenciaInscripcion(Integer secuenciaInscripcion) {
        this.secuenciaInscripcion = secuenciaInscripcion;
    }

    public TipoAsociado getTipo() {
        return tipo;
    }

    public void setTipo(TipoAsociado tipo) {
        this.tipo = tipo;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}
