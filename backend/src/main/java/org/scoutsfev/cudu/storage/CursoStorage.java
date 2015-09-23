package org.scoutsfev.cudu.storage;

import org.scoutsfev.cudu.domain.EstadoInscripcionEnCurso;

public interface CursoStorage {
    EstadoInscripcionEnCurso estadoDeInscripcion(int cursoId, int asociadoId);
}
