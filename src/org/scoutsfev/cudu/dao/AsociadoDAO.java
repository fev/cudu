package org.scoutsfev.cudu.dao;

import java.util.Collection;

import org.scoutsfev.cudu.domain.Asociado;

public interface AsociadoDAO  extends GenericDAOInterface<Asociado, Integer> {
	
	public Collection<Asociado> findWhere(String idGrupo, String columnas,
			String campoOrden, String sentidoOrden, int inicio,
			int resultadosPorPagina, String tipos, String ramas);
	
	public long count(String idGrupo, String tipos, String ramas);
}
