package org.scoutsfev.cudu.web.validacion;

public class ErrorUnico {

    private String codigo;

    protected ErrorUnico() { }

    public ErrorUnico(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
