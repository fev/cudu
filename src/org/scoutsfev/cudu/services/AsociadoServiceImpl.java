package org.scoutsfev.cudu.services;

import java.util.Collection;

import org.scoutsfev.cudu.domain.Asociado;

public class AsociadoServiceImpl 
	extends StorageServiceImpl<Asociado> 
	implements AsociadoService {

	@SuppressWarnings("unchecked")
	public Collection<Asociado> findWhere(String idGrupo, String columnas,
			String campoOrden, String sentidoOrden, int inicio,
			int resultadosPorPágina) {

		return this.entityManager
			.createQuery("SELECT " + columnas + " FROM Asociado WHERE idGrupo = :idGrupo ORDER BY " + campoOrden + " " + sentidoOrden)
			.setParameter("idGrupo", idGrupo)
			.setFirstResult(inicio)
			.setMaxResults(resultadosPorPágina)
			.getResultList();
	}

	public long count() {
		// TODO Substituir por tabla donde se guarde el recuento
		// para acelerar el COUNT de PostgreSQL.
		return (Long) this.entityManager
			.createQuery("SELECT COUNT(a) FROM Asociado a")
			.getSingleResult();
	}
	
	public long count(String idGrupo) {
		return (Long) this.entityManager
			.createQuery("SELECT COUNT(a) FROM Asociado a WHERE idGrupo = :idGrupo")
			.setParameter("idGrupo", idGrupo)
			.getSingleResult();
	}
}
