package org.scoutsfev.cudu.storage;

import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.dto.AsociadoTypeaheadDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface AsociadoRepository extends PagingAndSortingRepository<Asociado, Integer> {

    Page<Asociado> findByGrupoId(@Param("id") String id, Pageable pageable);

    @Modifying
    @Transactional
    @Query("update Asociado a set a.activo = :activo where a.id = :idAsociado")
    int activar(@Param("idAsociado") int idAsociado, @Param("activo") boolean activo);

    @Query("select a.grupoId from Asociado a where a.id = :idAsociado")
    String obtenerCodigoDeGrupoDelAsociado(@Param("idAsociado") Integer idAsociado);

    @Query("SELECT a FROM Asociado a LEFT JOIN FETCH a.cargos WHERE a.id = :id")
    Asociado findByIdAndFetchCargosEagerly(@Param("id") int id);

    // Requiere un índice extra en BBDD, para postgres (se incluye en scripts de migración):
    // CREATE INDEX CREATE INDEX typeahead_asociado_nombre_completo ON asociado (lower(nombre) varchar_pattern_ops, lower(apellidos) varchar_pattern_ops);
    // El texto debe estar en minúsculas para que pueda encontrar el índice correctamente.
    @Query("SELECT new org.scoutsfev.cudu.domain.dto.AsociadoTypeaheadDto(a.id, a.grupoId, a.activo, a.nombre, a.apellidos, a.email, a.telefonoMovil, a.telefonoCasa) FROM Asociado a " +
           "WHERE a.grupoId = :grupoId AND (lower(a.nombre) LIKE :texto% OR lower(a.apellidos) LIKE :texto%) AND a.activo = true")
    Page<AsociadoTypeaheadDto> typeahead(@Param("grupoId") String grupoId, @Param("texto") String texto, Pageable pageable);

    @Query("SELECT new org.scoutsfev.cudu.domain.dto.AsociadoTypeaheadDto(A.id, A.grupoId, A.activo, A.nombre, A.apellidos, A.email, A.telefonoMovil, A.telefonoCasa) FROM Asociado A " +
            "WHERE (lower(A.nombre) LIKE :texto% OR lower(A.apellidos) LIKE :texto%) AND (EDAD(A.fechaNacimiento) >= 18)")
    Page<AsociadoTypeaheadDto> typeahead(@Param("texto") String texto, Pageable pageable);
}