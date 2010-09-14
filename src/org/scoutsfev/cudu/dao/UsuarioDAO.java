package org.scoutsfev.cudu.dao;

import org.scoutsfev.cudu.domain.Usuario;

public interface UsuarioDAO  extends GenericDAOInterface<Usuario, String> {

	Usuario loadWithAuthorities(String username);

}
