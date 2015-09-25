package org.scoutsfev.cudu.storage;

import org.scoutsfev.cudu.domain.Curso;
import org.scoutsfev.cudu.domain.EstadoInscripcionEnCurso;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CursoStorage {
    List<Curso> listado(Pageable pageable, Optional<Boolean> visibles);
    int numeroDeCursosDisponibles(Optional<Boolean> visibles);
    EstadoInscripcionEnCurso estadoDeInscripcion(int cursoId, int asociadoId);
}
