package org.scoutsfev.cudu.storage;

import org.scoutsfev.cudu.domain.dto.UsuarioPermisosDto;

import java.util.List;

public interface UsuarioStorage {
    List<UsuarioPermisosDto> obtenerUsuariosDeUnGrupo(String grupoId);
}
