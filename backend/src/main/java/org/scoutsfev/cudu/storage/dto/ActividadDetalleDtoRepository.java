package org.scoutsfev.cudu.storage.dto;

import org.scoutsfev.cudu.domain.dto.ActividadDetalleDto;
import org.scoutsfev.cudu.domain.dto.ActividadDetalleDtoPk;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ActividadDetalleDtoRepository extends Repository<ActividadDetalleDto, ActividadDetalleDtoPk> {

    List<ActividadDetalleDto> findByActividadId(Integer actividadId);
    ActividadDetalleDto findOne(ActividadDetalleDtoPk pk);
}
