package org.scoutsfev.cudu.storage;

import org.scoutsfev.cudu.domain.AmbitoEdicion;
import org.scoutsfev.cudu.domain.commands.EditarPermisosUsuario;
import org.scoutsfev.cudu.domain.dto.UsuarioPermisosDto;

import java.util.List;

public interface UsuarioStorage {
    List<UsuarioPermisosDto> obtenerUsuariosDeUnGrupo(String grupoId);
    void establecerPermisos(EditarPermisosUsuario command, AmbitoEdicion ambitoEdicion);
    void cambiarEmail(Integer asociadoId, String email);
}
