package org.scoutsfev.cudu.storage;

import org.jooq.DSLContext;
import org.jooq.Result;
import org.scoutsfev.cudu.db.tables.records.DtoEstadoInscripcionRecord;
import org.scoutsfev.cudu.domain.EstadoInscripcionEnCurso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.jooq.impl.DSL.val;
import static org.scoutsfev.cudu.db.tables.DtoEstadoInscripcion.DTO_ESTADO_INSCRIPCION;

@Service
public class CursoStorageImpl implements CursoStorage {

    private final DSLContext context;

    @Autowired
    public CursoStorageImpl(DSLContext context) {
        this.context = context;
    }

    @Override
    public EstadoInscripcionEnCurso estadoDeInscripcion(int cursoId, int asociadoId) {
        Result<DtoEstadoInscripcionRecord> inscripciones = context
                .selectFrom(DTO_ESTADO_INSCRIPCION)
                .where(DTO_ESTADO_INSCRIPCION.CURSO_ID.eq(val(cursoId)))
                .fetch();

        for (DtoEstadoInscripcionRecord inscripcion : inscripciones) {
            if (inscripcion.getAsociadoId() == asociadoId) {
                boolean listaDeEspera = (inscripcion.getOrden() > inscripcion.getPlazas());
                return new EstadoInscripcionEnCurso(cursoId, inscripcion.getPlazas(), inscripciones.size(), listaDeEspera);
            }
        }
        return null;
    }
}
