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
	public Collection<Asociado> findWhere(String idGrupo, String columnas) {
		Query query = this.entityManager.createQuery("SELECT " + columnas + " FROM Asociado WHERE idGrupo = :idGrupo");
		query.setParameter("idGrupo", idGrupo);
		return query.getResultList();
	}

}
