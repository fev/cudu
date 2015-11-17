package org.scoutsfev.cudu.domain;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class RestriccionesTests {

    @Test
    public void por_defecto_no_existe_ninguna_restriccion() throws Exception {
        Restricciones restricciones = new Restricciones();
        assertFalse(restricciones.isNoPuedeEditarDatosDelGrupo());
        assertFalse(restricciones.isNoPuedeEditarOtrasRamas());
        assertFalse(restricciones.isSoloLectura());
        assertNull(restricciones.getRestriccionAsociacion());
    }

    @Test
    public void si_no_existe_ninguna_restriccion_tieneAlgunaRestriccion_devuelve_false() throws Exception {
        Restricciones restricciones = new Restricciones();
        assertFalse(restricciones.tieneAlgunaRestriccion());
    }

    @Test
    public void si_tiene_restriccion_sobre_los_datos_del_grupo_tieneAlgunaRestriccion_devuelve_true() throws Exception {
        Restricciones restricciones = new Restricciones();
        restricciones.setNoPuedeEditarDatosDelGrupo(true);
        assertTrue(restricciones.tieneAlgunaRestriccion());
    }

    @Test
    public void si_tiene_restriccion_sobre_la_rama_tieneAlgunaRestriccion_devuelve_true() throws Exception {
        Restricciones restricciones = new Restricciones();
        restricciones.setNoPuedeEditarOtrasRamas(true);
        assertTrue(restricciones.tieneAlgunaRestriccion());
    }

    @Test
    public void si_tiene_restriccion_de_solo_lectura_tieneAlgunaRestriccion_devuelve_true() throws Exception {
        Restricciones restricciones = new Restricciones();
        restricciones.setSoloLectura(true);
        assertTrue(restricciones.tieneAlgunaRestriccion());
    }

    @Test
    public void si_tiene_restriccion_de_asociacion_tieneAlgunaRestriccion_devuelve_true() throws Exception {
        Restricciones restricciones = new Restricciones();
        restricciones.setRestriccionAsociacion(Asociacion.MEV);
        assertTrue(restricciones.tieneAlgunaRestriccion());
    }
}
