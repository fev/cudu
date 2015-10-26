package org.scoutsfev.cudu.storage.dto;

import org.scoutsfev.cudu.domain.dto.MiembroEscuelaDto;
import org.springframework.data.repository.Repository;
import java.util.List;

public interface MiembroEscuelaDtoRepository extends Repository<MiembroEscuelaDto, Integer> {
    List<MiembroEscuelaDto> findAll();
}

