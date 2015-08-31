package org.scoutsfev.cudu.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "dto_miembros_escuela")
@Immutable
public class  MiembroEscuelaDto {

    @Id
    private int id;
    private int cargoId;
    private String nombreCompleto;
    private String nombreGrupo;
    private String etiqueta;
    private int cargoId;
    private boolean mesaPedagogica;
    private String telefono;
    private String email;

    protected MiembroEscuelaDto() { }

    public MiembroEscuelaDto(int id, String nombreCompleto, String nombreGrupo, String etiqueta, boolean mesaPedagogica, String telefono, String email, int cargoId) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.nombreGrupo = nombreGrupo;
        this.etiqueta = etiqueta;
        this.mesaPedagogica = mesaPedagogica;
        this.telefono = telefono;
        this.email = email;
        this.cargoId = cargoId;
    }

    @Column(columnDefinition = "date NOT NULL")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fechaNacimiento;

    public int getId() {
        return id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public int getCargoId() {
        return cargoId;
    }

    public void setCargoId(int cargoId) {
        this.cargoId = cargoId;
    }

    public boolean isMesaPedagogica() {
        return mesaPedagogica;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public int getCargoId() { return this.cargoId; }
}
