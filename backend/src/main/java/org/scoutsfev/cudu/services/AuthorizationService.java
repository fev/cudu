package org.scoutsfev.cudu.services;

import org.scoutsfev.cudu.domain.*;
import org.scoutsfev.cudu.storage.AsociadoRepository;
import org.scoutsfev.cudu.storage.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("auth")
public class AuthorizationService {

    private final AsociadoRepository asociadoRepository;
    private final GrupoRepository grupoRepository;

    @Autowired
    public AuthorizationService(AsociadoRepository asociadoRepository, GrupoRepository grupoRepository) {
        this.asociadoRepository = asociadoRepository;
        this.grupoRepository = grupoRepository;
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

    public boolean puedeEditarUsuariosDelGrupo(String grupoId, Usuario usuario) {
        if (usuario == null || grupoId == null || !usuario.isUsuarioActivo())
            return false;

        if (usuario.getTipo() == TipoAsociado.Tecnico)
        {
            Asociacion restriccionAsociacion = usuario.getRestricciones().getRestriccionAsociacion();
            if (restriccionAsociacion == null)
                return true;

            Grupo grupo = grupoRepository.findOne(grupoId);
            return grupo != null && grupo.getAsociacion() == restriccionAsociacion;
        }

        return (usuario.getTipo() == TipoAsociado.Comite || usuario.getTipo() == TipoAsociado.Kraal)
            && (usuario.getGrupo() != null)
            && !(usuario.getRestricciones() != null && usuario.getRestricciones().tieneAlgunaRestriccion())
            && (grupoId.equals(usuario.getGrupo().getId()));
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

    public boolean esTecnico(Usuario usuario) {
        return !(usuario == null || !usuario.isUsuarioActivo() || usuario.getTipo() == null || usuario.getAmbitoEdicion() == null || usuario.getTipo() != TipoAsociado.Tecnico)
             && (usuario.getAmbitoEdicion() == AmbitoEdicion.Asociacion || usuario.getAmbitoEdicion() == AmbitoEdicion.Federacion);
    }
}
