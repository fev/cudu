package org.scoutsfev.cudu.domain.validadores;

public class ImpresionTabla {

    private Integer[] identificadores;
    private String[] columnas;

    public ImpresionTabla() {
    }

    public ImpresionTabla(Integer[] identificadores, String[] columnas) {
        this.identificadores = identificadores;
        this.columnas = columnas;
    }

    public Integer[] getIdentificadores() {
        return this.identificadores;
    }

    public void setIdentificadores(Integer[] identificadores) {
        this.identificadores = identificadores;
    }

    public String[] getColumnas() {
        return this.columnas;
    }

    public void setColumnas(String[] columnas) {
        this.columnas = columnas;
    }
}
