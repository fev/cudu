package org.scoutsfev.cudu.domain;

public class EstadoInscripcionEnCurso {

    public final int cursoId;
    public final int plazas;
    public final int inscritos;
    public final int disponibles;
    public final boolean listaDeEspera;

    public EstadoInscripcionEnCurso(int cursoId, int plazas, int inscritos, int disponibles, boolean listaDeEspera) {
        this.cursoId = cursoId;
        this.plazas = plazas;
        this.inscritos = inscritos;
        this.disponibles = disponibles;
        this.listaDeEspera = listaDeEspera;
    }
}

