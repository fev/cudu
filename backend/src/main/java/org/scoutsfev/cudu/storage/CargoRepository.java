package org.scoutsfev.cudu.storage;

import org.scoutsfev.cudu.domain.AmbitoCargo;
import org.scoutsfev.cudu.domain.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface CargoRepository extends JpaRepository<Cargo, Integer> {

    List<Cargo> findByAmbitoIn(Collection<AmbitoCargo> ambitos);
}
