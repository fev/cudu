package org.scoutsfev.cudu.storage;

import com.google.common.base.Strings;
import org.jooq.DSLContext;
import org.jooq.SelectField;
import org.scoutsfev.cudu.db.tables.records.AsociadoRecord;
import org.scoutsfev.cudu.domain.AmbitoEdicion;
import org.scoutsfev.cudu.domain.Asociacion;
import org.scoutsfev.cudu.domain.Restricciones;
import org.scoutsfev.cudu.domain.TipoAsociado;
import org.scoutsfev.cudu.domain.commands.EditarPermisosUsuario;
import org.scoutsfev.cudu.domain.dto.UsuarioPermisosDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.val;
import static org.scoutsfev.cudu.db.tables.Asociado.ASOCIADO;
import static org.scoutsfev.cudu.db.tables.Grupo.GRUPO;

@Repository
public class UsuarioStorageImpl implements UsuarioStorage {

    private final DSLContext context;

    private static final Collection<? extends SelectField<?>> camposListadoDePermisos = Arrays.asList(
            ASOCIADO.ID, ASOCIADO.GRUPO_ID, ASOCIADO.NOMBRE, ASOCIADO.APELLIDOS, ASOCIADO.EMAIL,
            ASOCIADO.TIPO, ASOCIADO.AMBITO_EDICION, ASOCIADO.NO_PUEDE_EDITAR_DATOS_DEL_GRUPO,
            ASOCIADO.NO_PUEDE_EDITAR_OTRAS_RAMAS, ASOCIADO.SOLO_LECTURA,
            ASOCIADO.FECHA_USUARIO_VISTO, ASOCIADO.CALIDAD_PASSWORD);

    @Autowired
    public UsuarioStorageImpl(DSLContext context) {
        this.context = context;
    }

    @Override
    public List<UsuarioPermisosDto> obtenerUsuariosDeUnGrupo(String grupoId) {
        List<AsociadoRecord> asociados = context
                .select(camposListadoDePermisos)
                .from(ASOCIADO)
                .where(ASOCIADO.USUARIO_ACTIVO.eq(val(true)))
                .and(ASOCIADO.GRUPO_ID.eq(grupoId))
                .orderBy(ASOCIADO.ID)
                .fetchInto(AsociadoRecord.class);
        return mapearListaAsociadosConPojo(asociados);
    }

    @Override
    public List<UsuarioPermisosDto> obtenerUsuariosDeUnaAsociacion(Asociacion asociacion) {
        List<AsociadoRecord> asociados = context
                .select(camposListadoDePermisos)
                .from(ASOCIADO)
                .innerJoin(GRUPO).on(GRUPO.ID.equal(ASOCIADO.GRUPO_ID))
                .where(ASOCIADO.USUARIO_ACTIVO.eq(val(true)))
                .and(GRUPO.ASOCIACION.equal(asociacion.getId()))
                .orderBy(ASOCIADO.GRUPO_ID, ASOCIADO.ID)
                .fetchInto(AsociadoRecord.class);
        return mapearListaAsociadosConPojo(asociados);
    }

    @Override
    public List<UsuarioPermisosDto> obtenerUsuarios() {
        List<AsociadoRecord> asociados = context
                .select(camposListadoDePermisos)
                .from(ASOCIADO)
                .where(ASOCIADO.USUARIO_ACTIVO.eq(val(true)))
                .orderBy(ASOCIADO.GRUPO_ID, ASOCIADO.ID)
                .fetchInto(AsociadoRecord.class);
        return mapearListaAsociadosConPojo(asociados);
    }

    private List<UsuarioPermisosDto> mapearListaAsociadosConPojo(List<AsociadoRecord> asociados) {
        return asociados.stream().map(a -> {
            AmbitoEdicion ambito = AmbitoEdicion.parse(a.getAmbitoEdicion().charAt(0));
            TipoAsociado tipo = TipoAsociado.parse(a.getTipo().charAt(0));
            Restricciones restricciones = new Restricciones();
            restricciones.setNoPuedeEditarDatosDelGrupo(a.getNoPuedeEditarDatosDelGrupo());
            restricciones.setNoPuedeEditarOtrasRamas(a.getNoPuedeEditarOtrasRamas());
            restricciones.setSoloLectura(a.getSoloLectura());
            LocalDateTime fechaUsuarioVisto = a.getFechaUsuarioVisto() != null ? a.getFechaUsuarioVisto().toLocalDateTime() : null;
            String nombreCompleto = a.getNombre();
            if (!Strings.isNullOrEmpty(a.getApellidos()))
                nombreCompleto = nombreCompleto + ' ' + a.getApellidos();
            return new UsuarioPermisosDto(a.getId(), nombreCompleto, a.getEmail(), a.getCalidadPassword(), tipo, ambito, restricciones, fechaUsuarioVisto, a.getGrupoId());
        }).collect(Collectors.toList());
    }

    @Override
    public void establecerPermisos(EditarPermisosUsuario command, AmbitoEdicion ambitoEdicion) {
        String ambito = Character.toString(ambitoEdicion.getAmbito());
        context.update(ASOCIADO)
            .set(ASOCIADO.AMBITO_EDICION, ambito)
            .set(ASOCIADO.NO_PUEDE_EDITAR_DATOS_DEL_GRUPO, command.isNoPuedeEditarDatosDelGrupo())
            .set(ASOCIADO.NO_PUEDE_EDITAR_OTRAS_RAMAS, command.isNoPuedeEditarOtrasRamas())
            .set(ASOCIADO.SOLO_LECTURA, command.isSoloLectura())
            .where(ASOCIADO.ID.eq(command.getUsuarioId()))
            .execute();
    }

    @Override
    public void cambiarEmail(Integer asociadoId, String email) {
        context.update(ASOCIADO).set(ASOCIADO.EMAIL, email).where(ASOCIADO.ID.eq(asociadoId)).execute();
    }
}