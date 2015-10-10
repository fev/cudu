package org.scoutsfev.cudu.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.scoutsfev.cudu.domain.AmbitoEdicion;
import org.scoutsfev.cudu.domain.Restricciones;

import java.time.LocalDateTime;

public class UsuarioPermisosDto {

    public final int id;
    public final String nombreCompleto;
    public final String email;
    public final int calidadPassword;
    public final AmbitoEdicion ambitoEdicion;
    public final Restricciones restricciones;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public final LocalDateTime ultimoUso;

    public UsuarioPermisosDto(int id, String nombreCompleto, String email, int calidadPassword, AmbitoEdicion ambitoEdicion, Restricciones restricciones, LocalDateTime ultimoUso) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.calidadPassword = calidadPassword;
        this.ambitoEdicion = ambitoEdicion;
        this.restricciones = restricciones;
        this.ultimoUso = ultimoUso;
    }
}
