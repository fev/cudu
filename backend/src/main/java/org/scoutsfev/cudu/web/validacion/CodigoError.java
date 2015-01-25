package org.scoutsfev.cudu.web.validacion;

public enum CodigoError {
    AsociadoInactivo,
    DeshabilitarUsuarioActual,
    ActivacionDeUsuarioEnCurso;

    public ErrorUnico asError() {
        return new ErrorUnico(name());
    }
}
