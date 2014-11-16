package org.scoutsfev.cudu.storage;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scoutsfev.cudu.Application;
import org.scoutsfev.cudu.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class AsociadoRepositoryIntegrationTests {

    @Autowired
    private AsociadoRepository asociadoRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    private Grupo grupo;

    @Before
    public void setUp() throws Exception {
        grupo = grupoRepository.save(generarGrupo(Optional.of("AK")));
    }

    @Test
    public void puede_guardar_asociados_usando_el_constructor_por_defecto() throws Exception {
        Asociado nuevo = generarAsociado("Mike", "Wazowski");
        Asociado guardado = asociadoRepository.save(nuevo);
        assertThat(guardado.getId(), is(greaterThan(0)));
    }

    @Test
    public void al_actualizar_un_asociado_se_preserva_el_identificador() throws Exception {
        Asociado asociado = asociadoRepository.save(generarAsociado("Mike", "Wazowski"));
        int idOriginal = asociado.getId();
        asociado.setNombre("John");
        Asociado actualizado = asociadoRepository.save(asociado);
        assertThat(actualizado.getId(), is(equalTo(idOriginal)));
    }

    @Test
    public void el_identificador_de_asociado_es_incremental() throws Exception {
        Asociado a1 = asociadoRepository.save(generarAsociado("Mike", "Wazowski"));
        Asociado a2 = asociadoRepository.save(generarAsociado("Jack", "Sparrow"));
        assertThat(a2.getId(), is(greaterThan(a1.getId())));
    }

    @Test
    public void una_vez_guardado_un_usuario_esta_activado() throws Exception {
        Asociado guardardo = asociadoRepository.save(generarAsociado("Mike", "Wazowski"));
        Asociado asociado = asociadoRepository.findOne(guardardo.getId());
        assertTrue(asociado.isActivo());
    }

    @Test
    public void es_posible_activar_un_usuario() throws Exception {
        Asociado original = generarAsociado("Mike", "Wazowski");
        original.setActivo(false);
        Asociado guardado = asociadoRepository.save(original);
        assertFalse(guardado.isActivo());

        int filas = asociadoRepository.activar(guardado.getId(), true);
        assertThat(filas, is(equalTo(1)));

        Asociado activado = asociadoRepository.findOne(original.getId());
        assertTrue(activado.isActivo());
    }

    @Test
    public void es_posible_desactivar_un_usuario() throws Exception {
        Asociado original = generarAsociado("Mike", "Wazowski");
        original.setActivo(true);
        Asociado guardado = asociadoRepository.save(original);
        int filas = asociadoRepository.activar(guardado.getId(), false);
        assertThat(filas, is(equalTo(1)));

        Asociado desactivado = asociadoRepository.findOne(original.getId());
        assertFalse(desactivado.isActivo());
    }

    @Test
    public void es_posible_obtener_el_codigo_del_grupo_del_asociado_sin_mezclar_datos() throws Exception {
        Asociado a1 = asociadoRepository.save(generarAsociado("Lightning", "Mcqueen"));

        Grupo otroGrupo = grupoRepository.save(generarGrupo(Optional.of(grupo.getId() + "2")));
        Asociado a2 = generarAsociado("Lightning", "Mcqueen");
        a2.setGrupo(otroGrupo);
        Asociado a2s = asociadoRepository.save(a2);

        assertThat(a1.getId(), is(not(equalTo(a2s.getId()))));

        assertEquals(grupo.getId(), asociadoRepository.obtenerCodigoDeGrupoDelAsociado(a1.getId()));
        assertEquals(grupo.getId() + "2", asociadoRepository.obtenerCodigoDeGrupoDelAsociado(a2.getId()));
    }

    @Test
    public void test_si_el_asociado_no_existe_al_obtener_el_codigo_del_grupo_devuelve_null() throws Exception {
        assertNull(asociadoRepository.obtenerCodigoDeGrupoDelAsociado(-1));
    }

    @Test
    public void si_el_asociado_no_tiene_grupo_al_obtener_el_codigo_del_grupo_devuelve_null() throws Exception {
        Asociado nuevo = generarAsociado("John", "Doe");
        nuevo.setGrupo(null);
        Asociado guardado = asociadoRepository.save(nuevo);
        assertNull(asociadoRepository.obtenerCodigoDeGrupoDelAsociado(guardado.getId()));
    }

    @Test
    public void cuando_un_asociado_esta_en_varias_ramas_se_respeta_el_mapping() throws Exception {
        Supplier<HashSet<Rama>> generarRamas = () -> Sets.newHashSet(Rama.Lobatos, Rama.Pioneros);
        Asociado nuevo = generarAsociado("Jack", "Sparrow");
        nuevo.setRamas(generarRamas.get());
        Asociado guardado = asociadoRepository.save(nuevo);

        Asociado asociado = asociadoRepository.findOne(guardado.getId());
        assertThat(Sets.difference(asociado.getRamas(), generarRamas.get()), is(empty()));
    }

    private Grupo generarGrupo(Optional<String> idGrupo) {
        return new Grupo(idGrupo.orElse("AK"), Asociacion.MEV, "Nombre",
                "Calle", 46015, "Valencia", "Valencia", "963400000", "email@example.com");
    }

    private Asociado generarAsociado(String nombre, String apellidos) {
        return generarAsociado(nombre, apellidos, Optional.<Grupo>empty());
    }

    private Asociado generarAsociado(String nombre, String apellidos, Optional<Grupo> grupo) {
        return new Asociado(grupo.orElse(this.grupo), TipoAsociado.Joven, Sets.newHashSet(Arrays.asList(Rama.Pioneros)),
                nombre, apellidos, new Date(190), "Calle", 46015, "Valencia", Sexo.Masculino);
    }
}
