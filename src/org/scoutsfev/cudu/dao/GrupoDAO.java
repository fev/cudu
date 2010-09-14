package org.scoutsfev.cudu.dao;

import org.scoutsfev.cudu.domain.Grupo;

public interface GrupoDAO  extends GenericDAOInterface<Grupo, String> {
	public Grupo findByUser(String username);
}
