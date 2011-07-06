package org.scoutsfev.cudu.services;

import java.util.List;
import org.scoutsfev.cudu.domain.Usuario;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioService {

	public Usuario find(String username);
        public UserDetails loadUserByUsername(String username);
        public Usuario obtenerUsuarioActual();

        public Usuario merge(Usuario usuario);
        
        public List<Usuario> obtenerUsuariosALosQuePuedeCambiarPermiso(String userName, String permiso);
}
