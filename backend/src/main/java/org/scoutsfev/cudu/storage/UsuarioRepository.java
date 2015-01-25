package org.scoutsfev.cudu.storage;

import org.scoutsfev.cudu.domain.Asociacion;
import org.scoutsfev.cudu.domain.Usuario;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Optional;

public interface UsuarioRepository extends Repository<Usuario, String> {

    public Usuario findByEmail(String email);

    @Modifying
    @Transactional
    @Query("update Usuario u set u.password = :password, u.usuarioActivo = true, u.requiereCaptcha = false where u.id = :idAsociado")
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
    @Query("update Usuario u set u.lenguaje = :lenguaje where u.id = :idAsociado")
    public void cambiarIdioma(@Param("idAsociado") int idAsociado, @Param("lenguaje") String lenguaje);

    @Modifying
    @Transactional
    @Query("update Usuario u set u.requiereCaptcha = :positivo where u.email = :email")
    public void marcarCaptcha(@Param("email") String email, @Param("positivo") boolean positivo);

    @Modifying
    @Transactional
    @Query("update Usuario u set u.requiereCaptcha = false, u.fechaUsuarioVisto = :fechaEntrada where u.email = :email")
    public void marcarEntrada(@Param("email") String email, @Param("fechaEntrada") Timestamp fechaEntrada);

    @Query("select u.requiereCaptcha from Usuario u where u.email = :email")
    public Optional<Boolean> requiereCaptcha(@Param("email") String email);
}
