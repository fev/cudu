package org.scoutsfev.cudu.domain.validadores;

import org.scoutsfev.cudu.domain.Curso;
import org.scoutsfev.cudu.web.validacion.Error;

import java.time.LocalDateTime;
import java.util.List;

public interface ValidadorInscripcionCurso {
    List<Error> validar(Curso curso, LocalDateTime localDateTime);
}
