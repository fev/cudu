package org.scoutsfev.cudu.storage;

import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.dto.AsociadoTypeaheadDto;
import org.scoutsfev.cudu.domain.dto.FormadorDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface AsociadoRepository extends PagingAndSortingRepository<Asociado, Integer>, AsociadoRepositoryCustom {

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

    @Query("SELECT new org.scoutsfev.cudu.domain.dto.AsociadoTypeaheadDto(a.id, a.grupoId, a.activo, a.nombre, a.apellidos, a.email, a.telefonoMovil, a.telefonoCasa) FROM Asociado a " +
            "WHERE (lower(a.nombre) LIKE :texto% OR lower(a.apellidos) LIKE :texto%) AND a.fechaNacimiento <= (SELECT c.fechaNacimientoMinima FROM Curso c WHERE c.id = :cursoId) AND a.activo = true")
    Page<AsociadoTypeaheadDto> participanteTypeAhead(@Param("texto") String texto, @Param("cursoId") int cursoId, Pageable pageable);

    @Query(value = "SELECT new org.scoutsfev.cudu.domain.dto.FormadorDto(F.id, F.nombreCompleto, F.telefono) FROM MiembroEscuelaDto F WHERE lower(F.nombreCompleto) LIKE :texto% AND F.cargoId = 34")
    Page<FormadorDto> formadorTypeAhead(@Param("texto") String texto, Pageable pageable);

    @Query("SELECT new org.scoutsfev.cudu.domain.dto.AsociadoTypeaheadDto(a.id, a.grupoId, a.activo, a.nombre, a.apellidos, COALESCE(a.email, a.emailContacto), a.telefonoMovil, a.telefonoCasa) FROM Asociado a " +
            "WHERE a.grupoId = :grupoId AND (lower(a.nombre) LIKE :texto% OR lower(a.apellidos) LIKE :texto%) AND a.activo = true AND a.usuarioActivo = false AND (EDAD(a.fechaNacimiento) >= 18) AND (a.tipo = 'K' OR a.tipo = 'C')")
    Page<AsociadoTypeaheadDto> usuariosTypeahead(@Param("grupoId") String grupoId, @Param("texto") String texto, Pageable pageable);

    /**
     * Comprueba si el email ya existe para algún otro usuario que no sea el especificado.
     * @param asociadoIdActual Id del usuario actual.
     * @param nuevoEmail Email a comprobar.
     * @return Verdadero o falso, en función de que ya exista.
     */
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN 'true' ELSE 'false' END FROM Asociado u WHERE u.id <> :id AND u.email = :email")
    boolean existeOtroUsuarioConEseEmail(@Param("id") int asociadoIdActual, @Param("email") String nuevoEmail);
}