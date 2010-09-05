package org.scoutsfev.cudu.services;

import javax.persistence.Query;

import org.scoutsfev.cudu.domain.Grupo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class GrupoServiceImpl 
	extends StorageServiceImpl<Grupo>
	implements GrupoService {

	public Grupo find(String id) {
		return this.entityManager.find(Grupo.class, id);
	}
	
	public Grupo findByUser(String username) {
		Query query = this.entityManager.createQuery("SELECT u.grupo FROM Usuario u WHERE u.username = :username");
		query.setParameter("username", username);
		Grupo grupo = (Grupo) query.getSingleResult();
		return grupo;
	}

	@Override
	@Transactional
	public Grupo merge(Grupo g) {
		Grupo grupo = this.entityManager.merge(g);
		auditoria.registrar(AuditoriaService.Operacion.Almacenar, AuditoriaService.Entidad.Grupo, g.getId());
		return grupo;
	}
}
