package org.scoutsfev.cudu.domain;

public class EstadoInscripcionEnCurso {

    public final int cursoId;
    public final int plazas;
    public final int inscritos;
    public final boolean listaDeEspera;

    public EstadoInscripcionEnCurso(int cursoId, int plazas, int inscritos, boolean listaDeEspera) {
        this.cursoId = cursoId;
        this.plazas = plazas;
        this.inscritos = inscritos;
        this.listaDeEspera = listaDeEspera;
    }
}

