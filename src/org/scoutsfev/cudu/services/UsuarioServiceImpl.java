package org.scoutsfev.cudu.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.scoutsfev.cudu.domain.Usuario;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioServiceImpl implements UsuarioService {

	@PersistenceContext
	protected EntityManager entityManager;
	
	public Usuario find(String username) {
		return entityManager.find(Usuario.class, username);
	}
}
