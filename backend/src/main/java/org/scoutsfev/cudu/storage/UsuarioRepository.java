package org.scoutsfev.cudu.storage;

import org.scoutsfev.cudu.domain.Asociacion;
import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.Restricciones;
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
    @Query("update Usuario a set a.password = :password, a.usuarioActivo = true where a.id = :idAsociado")
    public void activar(@Param("idAsociado") int idAsociado, @Param("password") String password);

    @Modifying
    @Transactional
    @Query("update Usuario a set a.password = null, a.usuarioActivo = false where a.id = :idAsociado")
    public void desactivar(@Param("idAsociado") int idAsociado);

    @Modifying
    @Transactional
    @Query("update Usuario u set " +
           "u.restricciones.noPuedeEditarDatosDelGrupo = :noPuedeEditarDatosDelGrupo, " +
           "u.restricciones.noPuedeEditarOtrasRamas = :noPuedeEditarOtrasRamas, " +
           "u.restricciones.soloLectura = :soloLectura, " +
           "u.restricciones.restriccionAsociacion = :restriccionAsociacion " +
           "where u.id = :idAsociado")
    public void establecerRestricciones(@Param("idAsociado") int idAsociado,
        @Param("noPuedeEditarDatosDelGrupo") boolean noPuedeEditarDatosDelGrupo,
        @Param("noPuedeEditarOtrasRamas") boolean noPuedeEditarOtrasRamas,
        @Param("soloLectura") boolean soloLectura,
        @Param("restriccionAsociacion") Asociacion restriccionAsociacion);

    @Modifying
    @Transactional
    @Query("update Usuario a set a.lenguaje = :lenguaje where a.id = :idAsociado")
    public void cambiarIdioma(@Param("idAsociado") int idAsociado, @Param("lenguaje") String lenguaje);
}
