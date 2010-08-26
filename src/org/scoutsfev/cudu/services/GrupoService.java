package org.scoutsfev.cudu.services;

import org.scoutsfev.cudu.domain.Grupo;

public interface GrupoService
	extends StorageService<Grupo> {

	public Grupo find(String id);
	public Grupo findByUser(String username);
	public Grupo merge(Grupo g);
}
