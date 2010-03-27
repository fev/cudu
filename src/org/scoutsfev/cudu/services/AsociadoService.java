package org.scoutsfev.cudu.services;

import java.util.Collection;

import org.scoutsfev.cudu.domain.Asociado;

public interface AsociadoService 
	extends StorageService<Asociado> {

	public Collection<Asociado> findWhere(String idGrupo, String columnas,
			String campoOrden, String sentidoOrden, int inicio,
			int resultadosPorPágina);
}
