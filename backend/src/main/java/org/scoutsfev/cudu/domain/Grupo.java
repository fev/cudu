package org.scoutsfev.cudu.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.hibernate.validator.constraints.Email;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Grupo {

    @Id
    @Column(length = 3)
    @Size(min = 2, max = 3)
    private String id;

    @OneToMany(mappedBy = "grupo")
    private List<Usuario> usuarios;

    @NotNull
    @Column(insertable = false, updatable = false)
    private Asociacion asociacion;

    @NotNull
    @Size(min = 3, max = 50)
    private String nombre;

    @NotNull
    @Size(min=3, max=300)
    private String direccion;

    @NotNull
    @Min(1)
    private Integer codigoPostal;

    @NotNull
    @Size(min = 3, max = 100)
    private String municipio;

    @Column(columnDefinition = "date NULL", nullable = true)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate aniversario;

    @NotNull
    @Size(min = 3, max = 15)
    private String telefono1;

    @Size(max = 15)
    private String telefono2;

    @Email
    @NotNull
    @Size(min = 6, max = 100)
    private String email;

    @Size(max = 300)
    private String web;

    @Size(max = 100)
    private String entidadPatrocinadora;

    protected Grupo() { }

    public Grupo(String id, Asociacion asociacion, String nombre, String direccion,
                 Integer codigoPostal, String municipio, String telefono1, String email) {
        this.telefono1 = telefono1;
        this.municipio = municipio;
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;
        this.nombre = nombre;
        this.id = id;
        this.email = email;
        this.asociacion = asociacion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public LocalDate getAniversario() {
        return aniversario;
    }

    public void setAniversario(LocalDate aniversario) {
        this.aniversario = aniversario;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public Asociacion getAsociacion() {
        return asociacion;
    }

    public void setAsociacion(Asociacion asociacion) {
        this.asociacion = asociacion;
    }

    public String getEntidadPatrocinadora() {
        return entidadPatrocinadora;
    }

    public void setEntidadpatrocinadora(String entidadPatrocinadora) {
        this.entidadPatrocinadora = entidadPatrocinadora;
    }
}

