package org.scoutsfev.cudu.storage;

import org.scoutsfev.cudu.domain.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, String> {

}
