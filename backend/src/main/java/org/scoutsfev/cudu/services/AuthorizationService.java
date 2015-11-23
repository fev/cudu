package org.scoutsfev.cudu.services;

import org.scoutsfev.cudu.domain.*;
import org.scoutsfev.cudu.domain.AsociadoParaAutorizar;
import org.scoutsfev.cudu.storage.AsociadoStorage;
import org.scoutsfev.cudu.storage.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("auth")
public class AuthorizationService {

    private final GrupoRepository grupoRepository;
    private final AsociadoStorage asociadoStorage;

    @Autowired
    public AuthorizationService(AsociadoStorage asociadoStorage, GrupoRepository grupoRepository) {
        this.asociadoStorage = asociadoStorage;
        this.grupoRepository = grupoRepository;
    }

    @SuppressWarnings("unused")
    public boolean puedeVerAsociado(Integer idAsociado, Usuario usuario) {
        if (idAsociado == null || usuario == null)
            return false;
        AsociadoParaAutorizar asociado = asociadoStorage.obtenerAsociadoParaEvaluarAutorizacion(idAsociado);
        return comprobarAccesoAsociado(asociado, usuario, false);
    }

    public boolean puedeEditarAsociado(Integer idAsociado, Usuario usuario) {
        if (idAsociado == null || usuario == null)
            return false;
        AsociadoParaAutorizar asociado = asociadoStorage.obtenerAsociadoParaEvaluarAutorizacion(idAsociado);
        return comprobarAccesoAsociado(asociado, usuario, true);
    }

    public boolean puedeEditarAsociado(Asociado asociado, Usuario usuario) {
        if (asociado == null || usuario == null)
            return false;
        AsociadoParaAutorizar asociadoParaAutorizar = new AsociadoParaAutorizar(asociado.getId(), asociado.getGrupoId(), null,
                asociado.isRamaColonia(), asociado.isRamaManada(), asociado.isRamaExploradores(), asociado.isRamaExpedicion(), asociado.isRamaRuta());
        return comprobarAccesoAsociado(asociadoParaAutorizar, usuario, true);
    }

    public boolean comprobarAccesoAsociado(AsociadoParaAutorizar asociado, Usuario usuario, boolean accesoParaEdicion) {
        if (asociado == null || usuario == null || usuario.getRestricciones() == null || !usuario.isUsuarioActivo())
            return false;

        if (usuario.getId().equals(asociado.getId()))
            return true;

        if (accesoParaEdicion && usuario.getRestricciones().isSoloLectura())
            return false;

        if (esTecnico(usuario)) {
            //noinspection SimplifiableIfStatement
            if (usuario.getAmbitoEdicion() == AmbitoEdicion.Federacion)
                return true;
            return usuario.getAmbitoEdicion() == AmbitoEdicion.Asociacion
                && usuario.getRestricciones() != null && asociado.getAsociacionId() != null && usuario.getRestricciones().getRestriccionAsociacion() != null
                && asociado.getAsociacionId().equals(usuario.getRestricciones().getRestriccionAsociacion().getId());
        }

        if (usuario.getAmbitoEdicion() != AmbitoEdicion.Grupo)
            return false;

        // Si no tiene restricciones de rama, comprobar que es del mismo grupo únicamente
        boolean mismoGrupo = asociado.getGrupoId() != null && asociado.getGrupoId().equals(usuario.getGrupo().getId());
        if (usuario.getRestricciones() == null || !usuario.getRestricciones().isNoPuedeEditarOtrasRamas())
            return mismoGrupo;

        // En caso contrario, comprobar que todas las ramas del usuario coinciden con las del asociado
        boolean mismasRamas =
                (usuario.isRamaColonia() && asociado.isRamaColonia())
             || (usuario.isRamaManada() && asociado.isRamaManada())
             || (usuario.isRamaExploradores() && asociado.isRamaExploradores())
             || (usuario.isRamaExpedicion() && asociado.isRamaExpedicion())
             || (usuario.isRamaRuta() && asociado.isRamaRuta());

        return mismoGrupo && mismasRamas;
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

    @SuppressWarnings("SimplifiableIfStatement")
    public boolean comprobarAccesoGrupo(Grupo grupo, Usuario usuario, boolean accesoParaEdicion) {
        if (usuario == null || grupo == null || usuario.getRestricciones() == null)
            return false;

        if (esTecnico(usuario)) {
            return usuario.getAmbitoEdicion() == AmbitoEdicion.Federacion
                || usuario.getAmbitoEdicion() == AmbitoEdicion.Asociacion && grupo.getAsociacion().equals(usuario.getRestricciones().getRestriccionAsociacion());
        }

        if (usuario.getGrupo() == null || !usuario.getGrupo().getId().equals(grupo.getId()))
            return false;

        if (usuario.getAmbitoEdicion() == null || usuario.getAmbitoEdicion() != AmbitoEdicion.Grupo)
            return false;

        return !(accesoParaEdicion && !usuario.getRestricciones().isNoPuedeEditarDatosDelGrupo());
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
