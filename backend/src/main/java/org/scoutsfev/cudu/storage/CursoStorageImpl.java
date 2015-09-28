package org.scoutsfev.cudu.storage;

import org.jooq.*;
import org.scoutsfev.cudu.domain.CacheKeys;
import org.scoutsfev.cudu.domain.Curso;
import org.scoutsfev.cudu.db.tables.records.DtoEstadoInscripcionRecord;
import org.scoutsfev.cudu.domain.EstadoInscripcionEnCurso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    @Override
    public List<Curso> listado(Pageable pageable, Optional<Boolean> visibles, int usuarioId) {
        /*
         * select c.*,
         *  coalesce(ct.inscritos, 0) inscritos,
         *  greatest(0, coalesce((c.plazas - ct.inscritos), c.plazas)) as disponibles,
         *  case when ai.curso_id is not null then true else false end usuario_inscrito,
         *  case when u.orden > plazas then true else false end usuario_lista_espera
         * from curso c
         * left join (select c.curso_id, count(c.asociado_id) inscritos from curso_participante c group by c.curso_id) ct on ct.curso_id = c.id
         * left join (select *, row_number() over (partition by curso_id order by secuencia_inscripcion) as orden from curso_participante) u
         *   on u.curso_id = c.id and u.asociado_id = ?
         * where c.visible = ?
         * order by c.id desc
         * limit ? offset ?;
         */
        SelectHavingStep<Record2<Integer, Integer>> numeroParticipantes = context
                .select(CURSO_PARTICIPANTE.CURSO_ID, count().as("inscritos"))
                .from(CURSO_PARTICIPANTE)
                .groupBy(CURSO_PARTICIPANTE.CURSO_ID);

        SelectJoinStep<Record> usuarioActualInscrito = context
                .select(CURSO_PARTICIPANTE.fields())
                .select(rowNumber().over().partitionBy(CURSO_PARTICIPANTE.CURSO_ID).orderBy(CURSO_PARTICIPANTE.SECUENCIA_INSCRIPCION).as("orden"))
                .from(CURSO_PARTICIPANTE);

        Condition clausulaVisibles = val(true).equal(true);
        if (visibles.isPresent())
            clausulaVisibles = clausulaVisibles.and(CURSO.VISIBLE.eq(val(visibles.get())));

        final Field<Integer> INSCRITOS = numeroParticipantes.field("inscritos", Integer.class);
        final SelectForUpdateStep<Record> query = context
                .select(CURSO.fields())
                .select(coalesce(INSCRITOS, val(0)).as("inscritos"))
                .select(greatest(0, coalesce(CURSO.PLAZAS.minus(INSCRITOS), CURSO.PLAZAS)).as("disponibles"))
                .select(choose().when(usuarioActualInscrito.field(0).isNotNull(), true).otherwise(false).as("usuario_inscrito"))
                .select(choose().when(usuarioActualInscrito.field("orden", Integer.class).gt(CURSO.PLAZAS), true).otherwise(false).as("usuario_lista_espera"))
                .from(CURSO)
                .leftOuterJoin(numeroParticipantes)
                    .on(numeroParticipantes.field(CURSO_PARTICIPANTE.CURSO_ID.getName(), Integer.class).eq(CURSO.ID))
                .leftOuterJoin(usuarioActualInscrito)
                    .on(usuarioActualInscrito.field(CURSO_PARTICIPANTE.CURSO_ID.getName(), Integer.class).eq(CURSO.ID)
                            .and(usuarioActualInscrito.field(CURSO_PARTICIPANTE.ASOCIADO_ID.getName(), Integer.class).eq(usuarioId)))
                .where(clausulaVisibles)
                .orderBy(CURSO.ID.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getPageNumber());

        return query.fetchInto(Curso.class);
    }

    @Override
    @Cacheable(value = CacheKeys.NumeroDeCursosDisponibles, key = "#root.methodName")
    public int numeroDeCursosDisponibles(Optional<Boolean> visibles) {
        Condition clausulaVisibles = val(true).equal(true);
        if (visibles.isPresent())
            clausulaVisibles = clausulaVisibles.and(CURSO.VISIBLE.eq(val(visibles.get())));
        return context.select(count()).from(CURSO).where(clausulaVisibles).fetchOne(0, Integer.class);
    }

    @Override
    public EstadoInscripcionEnCurso estadoDeInscripcion(int cursoId, int asociadoId) {
        Result<DtoEstadoInscripcionRecord> inscripciones = context
                .selectFrom(DTO_ESTADO_INSCRIPCION)
                .where(DTO_ESTADO_INSCRIPCION.CURSO_ID.eq(val(cursoId)))
                .fetch();

        if (inscripciones.isEmpty()) {
            int plazas = obtenerPlazas(cursoId);
            return new EstadoInscripcionEnCurso(cursoId, plazas, 0, plazas, false);
        }

        for (DtoEstadoInscripcionRecord inscripcion : inscripciones) {
            if (inscripcion.getAsociadoId() == asociadoId) {
                boolean listaDeEspera = (inscripcion.getOrden() > inscripcion.getPlazas());
                return new EstadoInscripcionEnCurso(cursoId, inscripcion.getPlazas(), inscripcion.getInscritos(), inscripcion.getDisponibles(), listaDeEspera);
            }
        }

        // Llegados a este punto existen inscripciones pero el usuario no está inscrito
        int plazas = obtenerPlazas(cursoId);
        int inscritos = context.fetchCount(CURSO_PARTICIPANTE, CURSO_PARTICIPANTE.CURSO_ID.eq(val(cursoId)));
        return new EstadoInscripcionEnCurso(cursoId, plazas, inscritos, Math.max(plazas - inscritos, 0), false);
    }

    private int obtenerPlazas(int cursoId) {
        return context.select(CURSO.PLAZAS).from(CURSO).where(CURSO.ID.eq(val(cursoId))).fetchOne(CURSO.PLAZAS);
    }
}
