package org.scoutsfev.cudu.storage;

import org.scoutsfev.cudu.domain.Usuario;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface UsuarioRepository extends Repository<Usuario, String> {
    public Usuario findByEmail(String email);

    @Modifying
    @Transactional
    @Query("update Usuario a set a.password = :password where a.id = :idAsociado")
    public void activar(@Param("idAsociado") int idAsociado, @Param("password") String password);

    @Modifying
    @Transactional
    @Query("update Usuario a set a.password = null where a.id = :idAsociado")
    public void desactivar(@Param("idAsociado") int idAsociado);
}
