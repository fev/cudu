package org.scoutsfev.cudu.services;

import java.util.Collection;
import org.scoutsfev.cudu.domain.Grupo;

public interface GrupoService
	extends StorageService<Grupo> {

	public Grupo find(String id);
	public Grupo findByUser(String username);
        public long count();
	public Grupo merge(Grupo g);
        public Collection<Grupo> findWhere(String columnas,
			String campoOrden, String sentidoOrden, int inicio,
			int resultadosPorPágina, boolean eliminados, int asociacion);
        
        public Collection<Grupo> findWhere(String columnas,
			String campoOrden, String sentidoOrden, int inicio,
			int resultadosPorPágina, boolean eliminados, String[] asociaciones);
        
}
