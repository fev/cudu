package org.scoutsfev.cudu.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.scoutsfev.cudu.domain.Grupo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class GrupoServiceImpl 
	extends StorageServiceImpl<Grupo>
	implements GrupoService {

	@PersistenceContext
	protected EntityManager entityManager;
	
	@Autowired
	protected AuditoriaService auditoria;

	public Grupo find(String id) {
		return entityManager.find(Grupo.class, id);
	}
	
	public Grupo findByUser(String username) {
		Query query = entityManager.createQuery("SELECT u.grupo FROM Usuario u WHERE u.username = :username");
		query.setParameter("username", username);
		Grupo grupo = (Grupo) query.getSingleResult();
		return grupo;
	}

	@Transactional
	public Grupo merge(Grupo g) {
		// Grupo merged = this.entityManager.merge(g);
		// this.entityManager.flush();
		// g.setId(merged.getId());
		// return this.entityManager.merge(g);

		Grupo grupo = this.entityManager.merge(g);
		auditoria.registrar(AuditoriaService.Operacion.Almacenar, AuditoriaService.Entidad.Grupo, g.getId());
		return grupo;
	}
}
