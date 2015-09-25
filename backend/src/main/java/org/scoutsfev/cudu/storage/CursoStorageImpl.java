package org.scoutsfev.cudu.storage;

import org.jooq.*;
import org.scoutsfev.cudu.domain.Curso;
import org.scoutsfev.cudu.db.tables.records.DtoEstadoInscripcionRecord;
import org.scoutsfev.cudu.domain.EstadoInscripcionEnCurso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.jooq.impl.DSL.*;
import static org.scoutsfev.cudu.db.tables.Curso.CURSO;
import static org.scoutsfev.cudu.db.tables.CursoParticipante.CURSO_PARTICIPANTE;
import static org.scoutsfev.cudu.db.tables.DtoEstadoInscripcion.DTO_ESTADO_INSCRIPCION;

@Service
public class CursoStorageImpl implements CursoStorage {

    private final DSLContext context;

    @Autowired
    public CursoStorageImpl(DSLContext context) {
        this.context = context;
    }

    public List<Curso> listado(Pageable pageable, Optional<Boolean> visibles) {
        /*
         * select c.*, coalesce(ct.inscritos, 0) inscritos
         * from curso c
         * left join (
         *  select c.curso_id, count(c.asociado_id) inscritos from curso_participante c group by c.curso_id) ct
         * on ct.curso_id = c.id;
         */
        SelectHavingStep<Record2<Integer, Integer>> numeroParticipantes = context
                .select(CURSO_PARTICIPANTE.CURSO_ID, count().as("inscritos"))
                .from(CURSO_PARTICIPANTE)
                .groupBy(CURSO_PARTICIPANTE.CURSO_ID);

        Condition clausulaVisibles = val(true).equal(true);
        if (visibles.isPresent())
            clausulaVisibles = clausulaVisibles.and(CURSO.VISIBLE.eq(val(visibles.get())));

        final SelectForUpdateStep<Record> query = context
                .select(CURSO.fields())
                .select(coalesce(numeroParticipantes.field("inscritos", Integer.class), val(0)).as("inscritos"))
                .from(CURSO)
                .leftOuterJoin(numeroParticipantes)
                    .on(numeroParticipantes.field(CURSO_PARTICIPANTE.CURSO_ID.getName(), Integer.class)
                            .eq(CURSO.ID))
                .where(clausulaVisibles)
                .orderBy(CURSO.ID.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getPageNumber());

        return query.fetchInto(Curso.class);
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
