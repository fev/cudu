package org.scoutsfev.cudu.storage;

import org.scoutsfev.cudu.domain.Asociacion;
import org.scoutsfev.cudu.domain.SparseTable;
import org.scoutsfev.cudu.domain.TipoAsociado;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AsociadoStorage {
    SparseTable listado(Asociacion asociacion, String grupoId, TipoAsociado tipo, List<String> ramas, Boolean activo, Pageable pageable);
}
