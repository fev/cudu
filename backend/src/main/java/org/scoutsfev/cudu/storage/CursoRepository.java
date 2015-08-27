package org.scoutsfev.cudu.storage;

import org.scoutsfev.cudu.domain.Curso;
import org.scoutsfev.cudu.domain.dto.MiembroCursoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CursoRepository extends PagingAndSortingRepository<Curso, Integer> {

    @Query("select m from MiembroCursoDto m where cursoId = :cursoId")
    List<MiembroCursoDto> obtenerMiembros(@Param("cursoId") int cursoId);
}
