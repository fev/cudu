package org.scoutsfev.cudu.storage;

import com.google.common.base.Strings;
import org.jooq.*;
import org.scoutsfev.cudu.domain.Asociacion;
import org.scoutsfev.cudu.domain.SparseTable;
import org.scoutsfev.cudu.domain.TipoAsociado;
import org.scoutsfev.cudu.domain.AsociadoParaAutorizar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.coalesce;
import static org.jooq.impl.DSL.val;
import static org.jooq.impl.DSL.when;
import static org.scoutsfev.cudu.db.tables.Asociado.ASOCIADO;
import static org.scoutsfev.cudu.db.tables.Grupo.GRUPO;

@Repository
public class AsociadoStorageImpl implements AsociadoStorage {

    private final DSLContext context;

    private final static Field<String> RAMA =
            when(ASOCIADO.RAMA_COLONIA.isTrue(), "rama.colonia")
            .when(ASOCIADO.RAMA_MANADA.isTrue(), "rama.manada")
            .when(ASOCIADO.RAMA_EXPLORADORES.isTrue(), "rama.exploradores")
            .when(ASOCIADO.RAMA_EXPEDICION.isTrue(), "rama.expedicion")
            .when(ASOCIADO.RAMA_RUTA.isTrue(), "rama.ruta")
            .otherwise((String)null)
            .as("rama");

    private final static Field[] camposListado = {
        ASOCIADO.ID, ASOCIADO.GRUPO_ID.as("grupo_id"), GRUPO.NOMBRE.as("grupo_nombre"), ASOCIADO.NOMBRE, ASOCIADO.APELLIDOS, ASOCIADO.TIPO, RAMA,
        coalesce(ASOCIADO.EMAIL, ASOCIADO.EMAIL_CONTACTO).as("email"), coalesce(ASOCIADO.TELEFONO_MOVIL, ASOCIADO.TELEFONO_CASA).as("telefono"),
        ASOCIADO.ACTIVO, ASOCIADO.USUARIO_ACTIVO, ASOCIADO.FECHA_ALTA, ASOCIADO.FECHA_BAJA, ASOCIADO.FECHA_ACTUALIZACION, ASOCIADO.SEXO
    };

    private final static List<String> nombresCamposListado = Arrays.asList(camposListado)
            .stream().map(Field::getName).collect(Collectors.toList());

    @Autowired
    public AsociadoStorageImpl(DSLContext context) {
        this.context = context;
    }

    @Override
    public SparseTable listado(Asociacion asociacion, String grupoId, TipoAsociado tipo, List<String> ramas, Boolean activo, String sexo, Pageable pageable) {

        SelectConditionStep<Record> base = context
                .select(camposListado)
                .from(ASOCIADO)
                .innerJoin(GRUPO).on(ASOCIADO.GRUPO_ID.eq(GRUPO.ID))
                .where(val(1).eq(1));

        if (asociacion != null) base = base.and(GRUPO.ASOCIACION.eq(asociacion.getId()));
        if (!Strings.isNullOrEmpty(grupoId)) base = base.and(GRUPO.ID.equal(grupoId));
        if (tipo != null) base = base.and(ASOCIADO.TIPO.eq(String.valueOf(tipo.getTipo())));
        if (ramas != null) base = añadirCondicionesDeRama(base, ramas);
        if (activo != null) base = base.and(ASOCIADO.ACTIVO.eq(activo));
        if (!Strings.isNullOrEmpty(sexo)) base = base.and(ASOCIADO.SEXO.equal(sexo));

        Object[][] asociados = base
                .orderBy(ASOCIADO.ID)
                .limit(pageable.getPageSize())
                .offset(pageable.getPageNumber())
                .fetchArrays();

        return new SparseTable(nombresCamposListado, asociados);
    }

    private SelectConditionStep<Record> añadirCondicionesDeRama(SelectConditionStep<Record> query, List<String> ramas) {
        List<Condition> condicionesRama = new ArrayList<>();
        for (String rama : ramas) {
            if (rama.equalsIgnoreCase("colonia")) condicionesRama.add(ASOCIADO.RAMA_COLONIA.isTrue());
            if (rama.equalsIgnoreCase("manada")) condicionesRama.add(ASOCIADO.RAMA_MANADA.isTrue());
            if (rama.equalsIgnoreCase("exploradores")) condicionesRama.add(ASOCIADO.RAMA_EXPLORADORES.isTrue());
            if (rama.equalsIgnoreCase("expedicion")) condicionesRama.add(ASOCIADO.RAMA_EXPEDICION.isTrue());
            if (rama.equalsIgnoreCase("ruta")) condicionesRama.add(ASOCIADO.RAMA_RUTA.isTrue());
        }

        if (condicionesRama.size() == 0)
            return query;

        Condition rama = condicionesRama.stream().skip(1).reduce(condicionesRama.get(0), Condition::or);
        return query.and(rama);
    }

    public AsociadoParaAutorizar obtenerAsociadoParaEvaluarAutorizacion(Integer asociadoId) {
        return context
            .select(ASOCIADO.ID, ASOCIADO.GRUPO_ID, GRUPO.ASOCIACION,
                    ASOCIADO.RAMA_COLONIA, ASOCIADO.RAMA_MANADA, ASOCIADO.RAMA_EXPLORADORES, ASOCIADO.RAMA_EXPEDICION, ASOCIADO.RAMA_RUTA)
            .from(ASOCIADO)
            .innerJoin(GRUPO).on(ASOCIADO.GRUPO_ID.eq(GRUPO.ID))
            .where(ASOCIADO.ID.eq(asociadoId))
            .fetchAnyInto(AsociadoParaAutorizar.class);
    }
}
