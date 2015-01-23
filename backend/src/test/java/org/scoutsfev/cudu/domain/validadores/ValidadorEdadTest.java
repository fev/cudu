package org.scoutsfev.cudu.domain.validadores;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ValidadorEdadTest {

    private ValidadorEdad validador;
    private LocalDate fecha30;

    @Before
    public void setUp() throws Exception {
        LocalDate fechaActual = LocalDate.of(2000, 1, 1);
        fecha30 = fechaActual.minus(30, ChronoUnit.YEARS);
        validador = new ValidadorEdadTesteable(fechaActual);
    }

    @Test
    public void pasa_cuando_la_fecha_es_mayor_que_el_minimo() throws Exception {
        validador.initialize(edad(20, Integer.MAX_VALUE));
        assertTrue(validador.isValid(fecha30, null));
    }

    @Test
    public void pasa_cuando_la_fecha_es_igual_que_el_minimo() throws Exception {
        validador.initialize(edad(30, Integer.MAX_VALUE));
        assertTrue(validador.isValid(fecha30, null));
    }

    @Test
    public void no_pasa_cuando_la_fecha_es_menor_que_el_minimo() throws Exception {
        validador.initialize(edad(40, Integer.MAX_VALUE));
        assertFalse(validador.isValid(fecha30, null));
    }

    @Test
    public void pasa_cuando_la_fecha_es_menor_que_el_maximo() throws Exception {
        validador.initialize(edad(0, 40));
        assertTrue(validador.isValid(fecha30, null));
    }

    @Test
    public void pasa_cuando_la_fecha_es_igual_que_el_maximo() throws Exception {
        validador.initialize(edad(0, 30));
        assertTrue(validador.isValid(fecha30, null));
    }

    @Test
    public void no_pasa_cuando_la_fecha_es_mayor_que_el_maximo() throws Exception {
        validador.initialize(edad(0, 20));
        assertFalse(validador.isValid(fecha30, null));
    }

    private Edad edad(int min, int max) {
        Edad edad = mock(Edad.class);
        when(edad.min()).thenReturn(min);
        when(edad.max()).thenReturn(max);
        return edad;
    }

    class ValidadorEdadTesteable extends ValidadorEdad {

        private final LocalDate fechaActual;

        public ValidadorEdadTesteable(LocalDate fechaActual) {
            this.fechaActual = fechaActual;
        }

        @Override
        protected LocalDate obtenerFechaActual() {
            return fechaActual;
        }
    }
}