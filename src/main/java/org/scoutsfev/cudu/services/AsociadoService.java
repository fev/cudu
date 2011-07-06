package org.scoutsfev.cudu.services;

import java.util.Collection;
import java.util.List;

import org.scoutsfev.cudu.domain.Asociado;

public interface AsociadoService 
	extends StorageService<Asociado> {

	public Collection<Asociado> findWhere(String idGrupo, String columnas,
			String campoOrden, String sentidoOrden, int inicio,
			int resultadosPorPágina, String tipos, String ramas, boolean eliminados, int asociacion);
	
//	public long count();
	public long count(String idGrupo, String tipos, String ramas, boolean eliminados, int asociacion);
	
        @Override
	public Asociado merge(Asociado entity);
	public Asociado find(String id);
	public boolean delete(int id);
        public Integer getIdAsociado(String usuario);
        public Asociado findemail(String email);
        public List getRecorridoAsociado(int id);
}
