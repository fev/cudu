package org.scoutsfev.cudu.domain;

import com.google.common.collect.Sets;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;

public class AsociadoTests {

    private Grupo grupo;

    @Before
    public void setUp() throws Exception {
        grupo = new Grupo("TEST", Asociacion.MEV, "Placeholder", "ABCDEF", 46015, "Valencia", "Valencia", "963400000", "test@example.com");
    }

    @Test
    public void por_defecto_un_usuario_esta_activado() throws Exception {
        Asociado asociado = new Asociado(grupo, TipoAsociado.Joven,
                Sets.newHashSet(Arrays.asList(Rama.Castores)),
                "Mike", "Wazowski", new Date(190), "Calle", 46015, "Valencia", Sexo.Masculino);
        Assert.assertTrue(asociado.isActivo());
    }

    @Test
    public void usando_el_ctor_por_defecto_un_usuario_esta_activado() throws Exception {
        Asociado asociado = new Asociado();
        Assert.assertTrue(asociado.isActivo());
    }

    // TODO (servicio) Un joven no puede estar en más de una rama (Kraal y Comité si)
    // TODO (servicio) Un joven debe estar obligatóriamente en una rama (Kraal y Comité no)
    // TODO cuando_un_asociado_se_serializa_no_contiene_el_password()
    // TODO (servicio, web/integración) el servicio de asociados devuelve un asociado sin el password
}
