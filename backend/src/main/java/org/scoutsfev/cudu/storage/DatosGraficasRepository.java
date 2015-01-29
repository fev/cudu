package org.scoutsfev.cudu.storage;

import org.scoutsfev.cudu.domain.CacheKeys;
import org.scoutsfev.cudu.domain.dto.DatosPorGrupoRamaDto;
import org.scoutsfev.cudu.domain.dto.DatosPorGrupoTipoDto;
import org.scoutsfev.cudu.domain.dto.DatosPorPeriodoTipoDto;
import org.scoutsfev.cudu.domain.dto.DatosPorRamaTipoDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DatosGraficasRepository extends Repository<DatosPorPeriodoTipoDto, Integer> {

    @Cacheable(value = CacheKeys.DatosGraficasGlobales, key = "#root.methodName")
    @Query("select d from DatosPorPeriodoTipoDto d")
    List<DatosPorPeriodoTipoDto> findByDatosPorPeriodoTipoDto();

    @Cacheable(value = CacheKeys.DatosGraficasGlobales, key = "#root.methodName")
    @Query("select d from DatosPorRamaTipoDto d")
    List<DatosPorRamaTipoDto> findByDatosPorRamaTipoDto();

    @Cacheable(value = CacheKeys.DatosGraficasPorGrupoRama, key = "#a0")
    @Query("select d from DatosPorGrupoRamaDto d where d.id = :grupoId")
    DatosPorGrupoRamaDto findByDatosPorGrupoRamaDto(@Param("grupoId") String grupoId);

    @Cacheable(value = CacheKeys.DatosGraficasPorGrupoTipo, key = "#a0")
    @Query("select d from DatosPorGrupoTipoDto d where d.id = :grupoId")
    DatosPorGrupoTipoDto findByDatosPorGrupoTipoDto(@Param("grupoId") String grupoId);
}
