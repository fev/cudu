package org.scoutsfev.cudu.storage;

import org.scoutsfev.cudu.domain.Asociado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface AsociadoRepository extends PagingAndSortingRepository<Asociado, Integer> {

    public Page<Asociado> findByGrupoId(@Param("id") String id, Pageable pageable);

    @Modifying
    @Transactional
    @Query("update Asociado a set a.activo = :activo where a.id = :idAsociado")
    int activar(@Param("idAsociado") int idAsociado, @Param("activo") boolean activo);

    @Query("select a.grupoId from Asociado a where a.id = :idAsociado")
    String obtenerCodigoDeGrupoDelAsociado(@Param("idAsociado") Integer idAsociado);
}