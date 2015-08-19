package org.scoutsfev.cudu.services;

import org.scoutsfev.cudu.domain.*;
import org.scoutsfev.cudu.storage.AsociadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("auth")
public class AuthorizationService {

    private final AsociadoRepository asociadoRepository;

    @Autowired
    public AuthorizationService(AsociadoRepository asociadoRepository) {
        this.asociadoRepository = asociadoRepository;
    }

    public boolean puedeEditarAsociado(Integer idAsociado, Usuario usuario) {
        if (idAsociado == null || usuario == null || usuario.getGrupo() == null)
            return false;
        String idGrupo = asociadoRepository.obtenerCodigoDeGrupoDelAsociado(idAsociado);
        return idGrupo != null && idGrupo.equals(usuario.getGrupo().getId());
    }

    public boolean puedeEditarAsociado(Asociado asociado, Usuario usuario) {
        if (asociado == null || usuario == null || usuario.getGrupo() == null) {
            return false;
        }
        String idGrupo = asociado.getGrupoId();
        return idGrupo != null && idGrupo.equals(usuario.getGrupo().getId());
    }

    public boolean puedeEditarGrupo(Grupo grupo, Usuario usuario) {
        return grupo != null && usuario != null
            && usuario.getGrupo() != null && usuario.getGrupo().getId().equals(grupo.getId())
            && !usuario.getRestricciones().isNoPuedeEditarDatosDelGrupo();
    }

    public boolean puedeAccederLluerna(Usuario usuario) {
        return !(usuario == null || !usuario.isUsuarioActivo() || usuario.getAmbitoEdicion() == null || usuario.getTipo() == null || usuario.getTipo() != TipoAsociado.Tecnico)
            && ((usuario.getAmbitoEdicion() == AmbitoEdicion.Escuela) || (usuario.getAmbitoEdicion() == AmbitoEdicion.Federacion && usuario.getRestricciones() != null && usuario.getRestricciones().getRestriccionAsociacion() == null));

    }
}
