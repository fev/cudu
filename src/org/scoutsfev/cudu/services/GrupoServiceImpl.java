package org.scoutsfev.cudu.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.scoutsfev.cudu.domain.Grupo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class GrupoServiceImpl implements GrupoService {

	@PersistenceContext
	protected EntityManager entityManager;
	
	@Autowired
	protected AuditoriaService auditoria;

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
		auditoria.registrar(AuditoriaService.Operacion.Almacenar, AuditoriaService.Entidad.Grupo, g.getId());
	}
}
