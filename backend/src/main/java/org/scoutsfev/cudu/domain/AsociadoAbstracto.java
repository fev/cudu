package org.scoutsfev.cudu.domain;

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

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

    @NotNull
    protected TipoAsociado tipo;

    @NotNull
    protected AmbitoEdicion ambitoEdicion;

    @NotNull
    @Column(nullable = false)
    protected boolean activo = true;

    @NotNull
    @Column(nullable = false)
    protected boolean usuarioActivo = false;

    protected Timestamp fechaUsuarioCreado = null;
    protected Timestamp fechaUsuarioVisto = null;
    protected Integer usuarioCreadoPorId = null;

    @Column(length = 130)
    protected String usuarioCreadoPorNombre = null;

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

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean isUsuarioActivo() {
        return usuarioActivo;
    }

    public void setUsuarioActivo(boolean usuarioActivo) {
        this.usuarioActivo = usuarioActivo;
    }

    public Timestamp getFechaUsuarioCreado() {
        return fechaUsuarioCreado;
    }

    public void setFechaUsuarioCreado(Timestamp fechaUsuarioCreado) {
        this.fechaUsuarioCreado = fechaUsuarioCreado;
    }

    public Timestamp getFechaUsuarioVisto() {
        return fechaUsuarioVisto;
    }

    public void setFechaUsuarioVisto(Timestamp fechaUsuarioVisto) {
        this.fechaUsuarioVisto = fechaUsuarioVisto;
    }

    public String getUsuarioCreadoPorNombre() {
        return usuarioCreadoPorNombre;
    }

    public void setUsuarioCreadoPorNombre(String usuarioCreadoPorNombre) {
        this.usuarioCreadoPorNombre = usuarioCreadoPorNombre;
    }

    public Integer getUsuarioCreadoPorId() {
        return usuarioCreadoPorId;
    }

    public void setUsuarioCreadoPorId(Integer usuarioCreadoPorId) {
        this.usuarioCreadoPorId = usuarioCreadoPorId;
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
}
