package org.scoutsfev.cudu.domain.commands;

import org.scoutsfev.cudu.domain.Restricciones;

import javax.validation.constraints.NotNull;

// Gracias JPA por no permitir immutabilidad... sigh!
// @JsonConstruct debería bastar para futuras implementaciones
public class EditarPermisosUsuario extends Restricciones {

    // Enviar a null desde javascript, usamos la URL
    private Integer usuarioId;

    @NotNull
    @SuppressWarnings("FieldCanBeLocal")
    private boolean ambitoPersonal = false;

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public boolean isAmbitoPersonal() {
        return ambitoPersonal;
    }
}
