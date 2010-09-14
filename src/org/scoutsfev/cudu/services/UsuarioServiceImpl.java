package org.scoutsfev.cudu.services;

import org.scoutsfev.cudu.dao.UsuarioDAO;
import org.scoutsfev.cudu.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service("usuarioService")
@Transactional(rollbackFor={java.lang.Exception.class},isolation=Isolation.READ_COMMITTED)
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	public Usuario find(String username) {
		return usuarioDAO.loadWithAuthorities(username);
	}

	/**
	 * Obtiene un usuario de la base de datos. El método se implementa
	 * para poder obtener el usuario actual desde el contexto de seguridad,
	 * mediante SecurityContextHolder.getContext().getAuthentication().getPrincipal()
	 * 
	 * @param username Nombre (login) del usuario a obtener
	 * @return Objeto Usuario cargado desde BBDD.
	 * @see http://bit.ly/9AxtUh
	 */
	@Transactional
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {

		Usuario usuario = find(username);
		if (usuario == null)
			throw new UsernameNotFoundException("No se encontró el usuario con el nombre " + username);
		
		return usuario;
	}
	
	public Usuario obtenerUsuarioActual() {
		return (Usuario)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
