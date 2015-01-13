package org.scoutsfev.cudu.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@MappedSuperclass
public abstract class AsociadoAbstracto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @NotNull
    @Size(min = 3, max = 30)
    protected String nombre;

    @NotNull
    @Size(min = 3, max = 100)
    protected String apellidos;

    @Email
    @Size(max = 100)
    @Column(unique = true)
    protected String email;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "grupo_id", referencedColumnName = "id")
    @JsonIgnore
    protected Grupo grupo;

    @NotNull
    protected TipoAsociado tipo;

    @NotNull
    protected AmbitoEdicion ambitoEdicion;

    @NotNull
    @Column(nullable = false)
    protected boolean usuarioActivo = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public TipoAsociado getTipo() {
        return tipo;
    }

    public void setTipo(TipoAsociado tipo) {
        this.tipo = tipo;
    }

    public AmbitoEdicion getAmbitoEdicion() {
        return ambitoEdicion;
    }

    public void setAmbitoEdicion(AmbitoEdicion ambitoEdicion) {
        this.ambitoEdicion = ambitoEdicion;
    }

    public boolean isUsuarioActivo() {
        return usuarioActivo;
    }

    public void setUsuarioActivo(boolean usuarioActivo) {
        this.usuarioActivo = usuarioActivo;
    }
}
