package org.scoutsfev.cudu.storage;

import org.scoutsfev.cudu.domain.Asociacion;
import org.scoutsfev.cudu.domain.Grupo;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GrupoRepository extends PagingAndSortingRepository<Grupo, String> {

    public List<Grupo> findByAsociacionOrderByNombre(@Param("asociacion") Asociacion asociacion);
    public List<Grupo> findAllByOrderByNombreAsc();

}
