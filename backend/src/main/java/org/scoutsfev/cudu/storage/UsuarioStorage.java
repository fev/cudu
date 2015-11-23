package org.scoutsfev.cudu.storage;

import org.scoutsfev.cudu.domain.AmbitoEdicion;
import org.scoutsfev.cudu.domain.Asociacion;
import org.scoutsfev.cudu.domain.commands.EditarPermisosUsuario;
import org.scoutsfev.cudu.domain.dto.UsuarioPermisosDto;

import java.util.List;

public interface UsuarioStorage {
    List<UsuarioPermisosDto> obtenerUsuariosDeUnGrupo(String grupoId);
    List<UsuarioPermisosDto> obtenerUsuariosDeUnaAsociacion(Asociacion asociacion);
    List<UsuarioPermisosDto> obtenerUsuarios();

    void establecerPermisos(EditarPermisosUsuario command, AmbitoEdicion ambitoEdicion);
    void cambiarEmail(Integer asociadoId, String email);
}
