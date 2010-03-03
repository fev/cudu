package org.scoutsfev.cudu.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.scoutsfev.cudu.domain.Grupo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class GrupoServiceImpl implements GrupoService {

	@PersistenceContext
	protected EntityManager entityManager;

	public Grupo find(String id) {
		return entityManager.find(Grupo.class, id);
	}

	@Transactional
	public void merge(Grupo g) {
		// Grupo merged = this.entityManager.merge(g);
		// this.entityManager.flush();
		// g.setId(merged.getId());
		// return this.entityManager.merge(g);

		this.entityManager.merge(g);
	}
}
