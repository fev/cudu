package org.scoutsfev.cudu.domain.dto;

public class FormadorDto {

    private int id;
    private String nombreCompleto;
    private String telefono;
    private String email;
    private String grupo;

    public FormadorDto(int id, String nombreCompleto, String telefono, String email, String grupo) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.email = email;
        this.grupo = grupo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreCompleto() {
        return this.nombreCompleto;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setEmail(String email) { this.email = email; }

    public String getEmail() { return this.email; }

    public void setGrupo(String grupo) { this.grupo = grupo; }

    public String getGrupo() { return this.grupo; }
}
