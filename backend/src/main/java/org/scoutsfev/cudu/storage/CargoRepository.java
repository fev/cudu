package org.scoutsfev.cudu.storage;

import org.scoutsfev.cudu.domain.AmbitoCargo;
import org.scoutsfev.cudu.domain.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

public interface CargoRepository extends JpaRepository<Cargo, Integer> {

    List<Cargo> findByAmbitoIn(Collection<AmbitoCargo> ambitos);

    @Modifying
    @Transactional
    @Query(value = "insert into cargo_asociado (cargo_id, asociado_id) values (:cargoId, :asociadoId);", nativeQuery = true)
    void asignar(@Param("cargoId") int cargoId, @Param("asociadoId") int asociadoId);

    @Modifying
    @Transactional
    @Query(value = "delete from cargo_asociado where cargo_id = :cargoId and asociado_id = :asociadoId", nativeQuery = true)
    void desasignar(@Param("cargoId") int cargoId, @Param("asociadoId") int asociadoId);

    @Modifying
    @Transactional
    @Query(value = "delete from cargo_asociado where cargo_id = :cargoId and asociado_id in (select id from asociado where grupo_id = :grupoId)", nativeQuery = true)
    void desasignarCargoUnicoEnGrupo(@Param("cargoId") int cargoId, @Param("grupoId") String grupoId);
}
