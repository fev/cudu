package org.scoutsfev.cudu.domain.dto;

public class AsociadoTypeaheadDto {

    private int id;
    private String grupoId;
    private boolean activo;
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;

    protected AsociadoTypeaheadDto() { }

    public AsociadoTypeaheadDto(int id, String grupoId, boolean activo, String nombre, String apellidos, String email, String telefonoMovil, String telefonoCasa) {
        this.id = id;
        this.grupoId = grupoId;
        this.activo = activo;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefonoMovil == null ? telefonoCasa : telefonoMovil ;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(String grupoId) {
        this.grupoId = grupoId;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
