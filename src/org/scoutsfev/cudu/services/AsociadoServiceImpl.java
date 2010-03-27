package org.scoutsfev.cudu.services;

import java.util.Collection;

import javax.persistence.Query;

import org.scoutsfev.cudu.domain.Asociado;
import org.springframework.transaction.annotation.Transactional;

public class AsociadoServiceImpl 
	extends StorageServiceImpl<Asociado> 
	implements AsociadoService {

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public Collection<Asociado> findWhere(String idGrupo, String columnas,
			String campoOrden, String sentidoOrden, int inicio,
			int resultadosPorPágina) {

		Query query = this.entityManager
			.createQuery("SELECT " + columnas + " FROM Asociado WHERE idGrupo = :idGrupo ORDER BY " + campoOrden + " " + sentidoOrden)
			.setParameter("idGrupo", idGrupo)
			.setFirstResult(inicio)
			.setMaxResults(resultadosPorPágina);

		return query.getResultList();
	}
}
