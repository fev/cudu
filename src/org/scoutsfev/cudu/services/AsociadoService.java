package org.scoutsfev.cudu.services;

import java.util.Collection;

import org.scoutsfev.cudu.domain.Asociado;

public interface AsociadoService {

	public Collection<Asociado> findWhere(String idGrupo, String columnas,
			String campoOrden, String sentidoOrden, int inicio,
			int resultadosPorPagina, String tipos, String ramas, boolean eliminados);
	
	public long count();
	public long count(String idGrupo, String tipos, String ramas, boolean eliminados);
	
	public Asociado merge(Asociado entity);
	public Asociado find(Integer id);
	public boolean delete(int id);
}
