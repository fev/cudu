package org.scoutsfev.cudu.services;

import org.scoutsfev.cudu.dao.GrupoDAO;
import org.scoutsfev.cudu.domain.Grupo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class GrupoServiceImpl implements GrupoService {
	
	@Autowired
	private GrupoDAO grupoDAO;
	
	@Autowired
	protected AuditoriaService auditoria;

	public Grupo find(String id) {
		return grupoDAO.find(id);
	}
	
	public Grupo findByUser(String username) {
		return grupoDAO.findByUser(username);
	}

	@Transactional
	public Grupo merge(Grupo g) {
		Grupo grupo = grupoDAO.merge(g);

		auditoria.registrar(AuditoriaService.Operacion.Almacenar, AuditoriaService.Entidad.Grupo, g.getId());
		return grupo;
	}
}
