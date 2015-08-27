package org.scoutsfev.cudu.domain.dto;

import org.scoutsfev.cudu.domain.TipoMiembroEscuela;

public class MiembroEscuelaEdicionDto {

    protected TipoMiembroEscuela cargo;
    protected boolean mesaPedagogica;

    public MiembroEscuelaEdicionDto() { }

    public TipoMiembroEscuela getCargo() {
        return cargo;
    }

    public void setCargo(TipoMiembroEscuela cargo) {
        this.cargo = cargo;
    }

    public boolean isMesaPedagogica() {
        return mesaPedagogica;
    }

    public void setMesaPedagogica(boolean mesaPedagogica) {
        this.mesaPedagogica = mesaPedagogica;
    }
}