package org.scoutsfev.cudu.storage;

import org.scoutsfev.cudu.domain.Asociacion;
import org.scoutsfev.cudu.domain.SparseTable;
import org.scoutsfev.cudu.domain.TipoAsociado;
import org.scoutsfev.cudu.domain.AsociadoParaAutorizar;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AsociadoStorage {
    SparseTable listado(Asociacion asociacion, String grupoId, TipoAsociado tipo, List<String> ramas, Boolean inactivo, String sexo, String nombreApellido, String orden, Boolean ordenAsc, Boolean certificadoDelitosSexuales, Pageable pageable);
    int contador(Asociacion asociacion, String grupoId, TipoAsociado tipo, List<String> ramas, Boolean inactivo, String sexo, String nombreApellido, Boolean certificadoDelitosSexuales);
    AsociadoParaAutorizar obtenerAsociadoParaEvaluarAutorizacion(Integer asociadoId);
}
