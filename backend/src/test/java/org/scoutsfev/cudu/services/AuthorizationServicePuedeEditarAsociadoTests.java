package org.scoutsfev.cudu.services;

import org.junit.Test;
import org.scoutsfev.cudu.domain.Restricciones;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

public class AuthorizationServicePuedeEditarAsociadoTests extends AbstractAuthorizationServiceAsociadoTests {

    public AuthorizationServicePuedeEditarAsociadoTests() {
        super(true);
    }

    @Test
    public void no_permite_editar_si_el_usuario_esta_marcado_como_solo_lectura() throws Exception {
        prepararCasoMismoGrupoSinRestricciones();
        Restricciones restricciones = new Restricciones();
        restricciones.setSoloLectura(true);
        when(usuario.getRestricciones()).thenReturn(restricciones);
        assertFalse(service.comprobarAccesoAsociado(asociado, usuario, accesoParaEdicion));
    }

    @Test
    public void no_permite_editar_si_el_usuario_esta_marcado_como_solo_lectura_aun_siendo_tecnico_federativo() throws Exception {
        Restricciones restricciones = prepararCasoTecnicoFederativo();
        restricciones.setSoloLectura(true);
        assertFalse(service.comprobarAccesoAsociado(asociado, usuario, accesoParaEdicion));
    }

}
