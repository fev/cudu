package org.scoutsfev.cudu.domain.validadores;

public class ImpresionTabla {

    private Integer[] identificadores;
    private String[] columnas;
    private String titulo;

    public ImpresionTabla() {
    }

    public ImpresionTabla(Integer[] identificadores, String[] columnas, String titulo) {
        this.identificadores = identificadores;
        this.columnas = columnas;
        this.titulo=titulo;
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

    public String getTitulo(){
      return this.titulo;
    }

    public void setTitulo(String titulo){
      this.titulo=titulo;
    }
}
