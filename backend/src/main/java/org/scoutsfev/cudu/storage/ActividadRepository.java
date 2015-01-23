package org.scoutsfev.cudu.storage;

import org.scoutsfev.cudu.domain.Actividad;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ActividadRepository extends CrudRepository<Actividad, Integer> {

    public List<Actividad> findByGrupoIdAndFechaBajaIsNull(@Param("id") String id);

    @Modifying
    @Transactional
    @Query(value = "insert into asistente_actividad (actividad_id, asociado_id, estado) \n" +
           "select :actividad_id, id, 'I' from asociado \n" +
           "where id = :asociado_id \n" +
           "and id not in (select asociado_id from asistente_actividad where actividad_id = :actividad_id)", nativeQuery = true)
    public int añadirAsistente(@Param("actividad_id") int actividadId, @Param("asociado_id") int asociadoId);


    @Modifying
    @Transactional
    @Query(value = "insert into asistente_actividad (actividad_id, asociado_id, estado) \n" +
            "select distinct :actividad_id , p.id, 'I' from asociado p, actividad a \n" +
            "where a.id = :actividad_id and a.grupo_id = p.grupo_id \n" +
            "  and p.activo = true and p.tipo in ('J', 'K') \n" +
            "  and p.rama_colonia = :colonia and p.rama_manada = :manada and p.rama_exploradores = :exploradores \n" +
            "  and p.rama_expedicion = :expedicion and p.rama_ruta = :ruta \n" +
            "  and p.id not in (select asociado_id from asistente_actividad where actividad_id = :actividad_id)", nativeQuery = true)
    public int añadirRamaCompleta(@Param("actividad_id") int actividadId, @Param("colonia") boolean colonia, @Param("manada") boolean manada,
                                  @Param("exploradores") boolean exploradores, @Param("expedicion") boolean expedicion, @Param("ruta") boolean ruta);

}
