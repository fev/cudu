package org.scoutsfev.cudu.services;

import com.google.common.base.Strings;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.storage.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!Strings.isNullOrEmpty(username)) {
            Usuario usuario = usuarioRepository.findOne(username);
            if (usuario != null)
                return usuario;
        }
        throw new UsernameNotFoundException("El usuario especificado no existe.");
    }
}
