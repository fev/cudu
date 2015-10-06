package org.scoutsfev.cudu.storage;

import org.scoutsfev.cudu.domain.Rama;
import org.scoutsfev.cudu.domain.TipoAsociado;

import java.util.List;

public interface AsociadoRepositoryCustom {
    void cambiarRama(List<Integer> asociados, Rama rama, String grupoId);
    void cambiarTipo(List<Integer> asociados, TipoAsociado tipo, String grupoId);
    void desactivar(List<Integer> asociados, String grupoId);
}
