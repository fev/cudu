package org.scoutsfev.cudu.domain.validadores;

import org.junit.Before;
import org.junit.Test;
import org.scoutsfev.cudu.domain.Curso;
import org.scoutsfev.cudu.web.validacion.Error;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ValidadorInscripcionEnCursoTests {

    private ValidadorInscripcionCurso validador;

    @Before
    public void setUp() throws Exception {
        this.validador = new ValidadorInscripcionCursoImpl();
    }

    @Test
    public void si_el_curso_es_nulo_devuelve_un_error() throws Exception {
        List<Error> errores = validador.validar(null, LocalDate.of(2013, 12, 25).atStartOfDay());
        assertNotNull(errores);
        assertThat(errores.size(), is(equalTo(1)));
        assertTrue("No considera cursos nulos", errores.stream().anyMatch(c -> c.getCampo().equals("curso")));
    }

    @Test
    public void si_la_fecha_actual_es_nula_devuelve_un_error() throws Exception {
        List<Error> errores = validador.validar(mock(Curso.class), null);
        assertNotNull(errores);
        assertThat(errores.size(), is(equalTo(1)));
        assertTrue("No considera fechas nulas", errores.stream().anyMatch(c -> c.getCampo().equals("fechaActual")));
    }

    @Test
    public void si_el_curso_es_nulo_y_la_fecha_actual_es_nula_devuelve_dos_errores() throws Exception {
        List<Error> errores = validador.validar(null, null);
        assertNotNull(errores);
        assertThat(errores.size(), is(equalTo(2)));
        assertTrue("No considera cursos nulos", errores.stream().anyMatch(c -> c.getCampo().equals("curso")));
        assertTrue("No considera fechas nulas", errores.stream().anyMatch(c -> c.getCampo().equals("fechaActual")));
    }

    @Test
    public void permite_inscribirse_si_la_fecha_de_cierre_de_inscripcion_es_posterior_a_la_actual() throws Exception {
        LocalDateTime fechaActual = LocalDate.of(2013, 12, 25).atStartOfDay();
        Curso curso = mock(Curso.class);
        when(curso.getFechaInicioInscripcion()).thenReturn(Timestamp.valueOf(fechaActual.minus(5l, ChronoUnit.DAYS)));
        when(curso.getFechaFinInscripcion()).thenReturn(Timestamp.valueOf(fechaActual.plus(1l, ChronoUnit.DAYS)));
        List<Error> errores = validador.validar(curso, fechaActual);
        assertNotNull(errores);
        assertThat(errores.size(), is(equalTo(0)));
    }

    @Test
    public void no_permite_inscribirse_si_la_fecha_de_cierre_de_inscripcion_es_anterior_a_la_actual() throws Exception {
        LocalDateTime fechaActual = LocalDate.of(2013, 12, 25).atStartOfDay();
        Curso curso = mock(Curso.class);
        when(curso.getFechaInicioInscripcion()).thenReturn(Timestamp.valueOf(fechaActual.minus(5l, ChronoUnit.DAYS)));
        when(curso.getFechaFinInscripcion()).thenReturn(Timestamp.valueOf(fechaActual.minus(1l, ChronoUnit.DAYS)));
        List<Error> errores = validador.validar(curso, fechaActual);
        assertNotNull(errores);
        assertThat(errores.size(), is(equalTo(1)));
        assertTrue("No valida bien el fin de inscripción", errores.stream().anyMatch(c -> c.getCampo().equals("fechaFinInscripcion")));
    }

    @Test
    public void permite_inscribirse_si_la_fecha_de_inicio_de_inscripcion_es_anterior_a_la_actual() throws Exception {
        LocalDateTime fechaActual = LocalDate.of(2013, 12, 25).atStartOfDay();
        Curso curso = mock(Curso.class);
        when(curso.getFechaInicioInscripcion()).thenReturn(Timestamp.valueOf(fechaActual.minus(5l, ChronoUnit.DAYS)));
        when(curso.getFechaFinInscripcion()).thenReturn(Timestamp.valueOf(fechaActual.plus(1l, ChronoUnit.DAYS)));
        List<Error> errores = validador.validar(curso, fechaActual);
        assertNotNull(errores);
        assertThat(errores.size(), is(equalTo(0)));
    }

    @Test
    public void no_permite_inscribirse_si_la_fecha_de_inicio_de_inscripcion_es_posterior_a_la_actual() throws Exception {
        LocalDateTime fechaActual = LocalDate.of(2013, 12, 25).atStartOfDay();
        Curso curso = mock(Curso.class);
        when(curso.getFechaInicioInscripcion()).thenReturn(Timestamp.valueOf(fechaActual.plus(1l, ChronoUnit.DAYS)));
        when(curso.getFechaFinInscripcion()).thenReturn(Timestamp.valueOf(fechaActual.plus(5l, ChronoUnit.DAYS)));
        List<Error> errores = validador.validar(curso, fechaActual);
        assertNotNull(errores);
        assertThat(errores.size(), is(equalTo(1)));
        assertTrue("No valida bien el inicio de inscripción", errores.stream().anyMatch(c -> c.getCampo().equals("fechaInicioInscripcion")));
    }

    @Test
    public void no_permite_inscribirse_si_tanto_la_fecha_de_inicio_como_la_de_fin_de_inscripcion_son_erroneas() throws Exception {
        LocalDateTime fechaActual = LocalDate.of(2013, 12, 25).atStartOfDay();
        Curso curso = mock(Curso.class);
        when(curso.getFechaInicioInscripcion()).thenReturn(Timestamp.valueOf(fechaActual.plus(1l, ChronoUnit.DAYS)));
        when(curso.getFechaFinInscripcion()).thenReturn(Timestamp.valueOf(fechaActual.minus(1l, ChronoUnit.DAYS)));
        List<Error> errores = validador.validar(curso, fechaActual);
        assertNotNull(errores);
        assertThat(errores.size(), is(equalTo(2)));
        assertTrue("No valida bien el inicio de inscripción", errores.stream().anyMatch(c -> c.getCampo().equals("fechaInicioInscripcion")));
        assertTrue("No valida bien el fin de inscripción", errores.stream().anyMatch(c -> c.getCampo().equals("fechaFinInscripcion")));
    }
}
