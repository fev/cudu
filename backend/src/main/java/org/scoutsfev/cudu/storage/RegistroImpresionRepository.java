package org.scoutsfev.cudu.storage;

import org.scoutsfev.cudu.RegistroImpresionId;
import org.scoutsfev.cudu.domain.RegistroImpresion;
import org.springframework.data.repository.CrudRepository;

public interface RegistroImpresionRepository extends CrudRepository<RegistroImpresion, RegistroImpresionId> {
}
