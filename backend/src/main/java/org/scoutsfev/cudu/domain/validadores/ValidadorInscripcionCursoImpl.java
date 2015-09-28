package org.scoutsfev.cudu.domain.validadores;

import org.scoutsfev.cudu.domain.Curso;
import org.scoutsfev.cudu.web.validacion.Error;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ValidadorInscripcionCursoImpl implements ValidadorInscripcionCurso {

    @Override
    public List<Error> validar(Curso curso, LocalDateTime fechaActual) {
        ArrayList<Error> errores = new ArrayList<>();
        if (curso == null)
            errores.add(new Error("curso", "El curso no puede ser nulo."));

        if (fechaActual == null)
            errores.add(new Error("fechaActual", "El curso no puede ser nulo."));

        if (errores.size() != 0)
            return errores;

        assert curso != null;
        if (curso.getFechaInicioInscripcion() != null && curso.getFechaFinInscripcion().toLocalDateTime().isBefore(fechaActual))
            errores.add(new Error("fechaFinInscripcion", "El plazo de inscripción está cerrado."));

        if (curso.getFechaInicioInscripcion() != null && fechaActual.isBefore(curso.getFechaInicioInscripcion().toLocalDateTime()))
            errores.add(new Error("fechaInicioInscripcion", "El plazo de inscripción todavía no está abierto."));

        return errores;
    }
}
