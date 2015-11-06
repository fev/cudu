package org.scoutsfev.cudu.storage;

import org.scoutsfev.cudu.domain.Curso;
import org.scoutsfev.cudu.domain.dto.MiembroCursoDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

public interface CursoRepository extends PagingAndSortingRepository<Curso, Integer> {

    @Query("select m from MiembroCursoDto m where cursoId = :cursoId")
    List<MiembroCursoDto> obtenerMiembros(@Param("cursoId") int cursoId);

    @Modifying
    @Transactional
    @Query(value = "insert into curso_formador (curso_id, asociado_id) values (:cursoId, :asociadoId);", nativeQuery = true)
    void añadirFormador(@Param("cursoId") int cursoId, @Param("asociadoId") int asociadoId);

    @Modifying
    @Transactional
    @Query(value = "delete from curso_formador where curso_id = :cursoId and asociado_id = :asociadoId", nativeQuery = true)
    void quitarFormador(@Param("cursoId") int cursoId, @Param("asociadoId") int asociadoId);

    @Modifying
    @Transactional
    @Query(value = "insert into curso_participante (curso_id, asociado_id, fecha_inscripcion) VALUES (:cursoId, :asociadoId, :fechaInscripcion)", nativeQuery = true)
    void añadirParticipante(@Param("cursoId") Integer cursoId, @Param("asociadoId") Integer asociadoId, @Param("fechaInscripcion") Timestamp timestamp);

    @Modifying
    @Transactional
    @Query(value = "delete from curso_participante where curso_id = :cursoId and asociado_id = :asociadoId", nativeQuery = true)
    void quitarParticipante(@Param("cursoId") int cursoId, @Param("asociadoId") int asociadoId);
}
