package org.scoutsfev.cudu.storage;

import com.google.common.base.Strings;
import org.jooq.DSLContext;
import org.scoutsfev.cudu.db.tables.records.AsociadoRecord;
import org.scoutsfev.cudu.domain.AmbitoEdicion;
import org.scoutsfev.cudu.domain.Restricciones;
import org.scoutsfev.cudu.domain.commands.EditarPermisosUsuario;
import org.scoutsfev.cudu.domain.dto.UsuarioPermisosDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.val;
import static org.scoutsfev.cudu.db.tables.Asociado.ASOCIADO;

@Repository
public class UsuarioStorageImpl implements UsuarioStorage {

    private final DSLContext context;

    @Autowired
    public UsuarioStorageImpl(DSLContext context) {
        this.context = context;
    }

    @Override
    public List<UsuarioPermisosDto> obtenerUsuariosDeUnGrupo(String grupoId) {
        List<AsociadoRecord> asociados = context
                .select(ASOCIADO.ID, ASOCIADO.NOMBRE, ASOCIADO.APELLIDOS, ASOCIADO.EMAIL,
                        ASOCIADO.AMBITO_EDICION, ASOCIADO.NO_PUEDE_EDITAR_DATOS_DEL_GRUPO,
                        ASOCIADO.NO_PUEDE_EDITAR_OTRAS_RAMAS, ASOCIADO.SOLO_LECTURA,
                        ASOCIADO.FECHA_USUARIO_VISTO, ASOCIADO.CALIDAD_PASSWORD)
                .from(ASOCIADO)
                .where(ASOCIADO.USUARIO_ACTIVO.eq(val(true)))
                .and(ASOCIADO.GRUPO_ID.eq(grupoId))
                .orderBy(ASOCIADO.ID)
                .fetchInto(AsociadoRecord.class);

        return asociados.stream().map(a -> {
            AmbitoEdicion ambito = AmbitoEdicion.parse(a.getAmbitoEdicion().charAt(0));
            Restricciones restricciones = new Restricciones();
            restricciones.setNoPuedeEditarDatosDelGrupo(a.getNoPuedeEditarDatosDelGrupo());
            restricciones.setNoPuedeEditarOtrasRamas(a.getNoPuedeEditarOtrasRamas());
            restricciones.setSoloLectura(a.getSoloLectura());
            LocalDateTime fechaUsuarioVisto = a.getFechaUsuarioVisto() != null ? a.getFechaUsuarioVisto().toLocalDateTime() : null;
            String nombreCompleto = a.getNombre();
            if (!Strings.isNullOrEmpty(a.getApellidos()))
                nombreCompleto = nombreCompleto + ' ' + a.getApellidos();
            return new UsuarioPermisosDto(a.getId(), nombreCompleto, a.getEmail(), a.getCalidadPassword(), ambito, restricciones, fechaUsuarioVisto);
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
}