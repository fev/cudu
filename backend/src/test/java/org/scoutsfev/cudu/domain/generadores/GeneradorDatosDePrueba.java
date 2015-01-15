package org.scoutsfev.cudu.domain.generadores;

import org.scoutsfev.cudu.domain.*;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class GeneradorDatosDePrueba {

    public static final String ID_GRUPO = "AK";

    private static AtomicInteger idAsociado = new AtomicInteger(0);

    public static Asociado generarAsociado(Grupo grupo) {
        int seqId = idAsociado.getAndIncrement();
        Asociado asociado = new Asociado(grupo, TipoAsociado.Joven, AmbitoEdicion.Grupo, "Nombre" + seqId, "Apellidos" + seqId, new Date(1418077278));
        asociado.setRamaManada(true);
        return asociado;
    }

    public static Asociado generarAsociado() {
        Grupo grupo = generarGrupo(Optional.empty());
        return generarAsociado(grupo);
    }

    public static Grupo generarGrupo(Optional<String> id) {
        return new Grupo(id.orElse(ID_GRUPO), Asociacion.MEV, "Nombre", "Calle", 46015, "Valencia", "963400000", "email@example.com");
    }
}
