package org.scoutsfev.cudu.web.validacion;

public enum CodigoError {
    AsociadoInactivo,
    HabilitarUsuarioActual,
    DeshabilitarUsuarioActual,
    ActivacionDeUsuarioEnCurso,
    YaExisteUsuarioConEseEmail;

    public ErrorUnico asError() {
        return new ErrorUnico(name());
    }
}
