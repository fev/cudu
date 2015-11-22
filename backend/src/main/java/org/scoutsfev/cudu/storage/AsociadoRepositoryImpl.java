package org.scoutsfev.cudu.storage;

import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.Asociado_;
import org.scoutsfev.cudu.domain.Rama;
import org.scoutsfev.cudu.domain.TipoAsociado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.function.Consumer;

public class AsociadoRepositoryImpl implements AsociadoRepositoryCustom {

    private final EntityManager entityManager;

    @Autowired
    public AsociadoRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    @Modifying
    public void cambiarRama(List<Integer> asociados, Rama rama, String grupoId) {
        actualizarAsociados(asociados, grupoId, criteriaUpdate ->
            criteriaUpdate
                .set(Asociado_.ramaColonia, rama.isColonia())
                .set(Asociado_.ramaManada, rama.isManada())
                .set(Asociado_.ramaExploradores, rama.isExploradores())
                .set(Asociado_.ramaExpedicion, rama.isExpedicion())
                .set(Asociado_.ramaRuta, rama.isRuta()));
    }

    @Override
    @Transactional
    @Modifying
    public void cambiarTipo(List<Integer> asociados, TipoAsociado tipo, String grupoId) {
        // TODO Al cambiar el tipo a J, eliminar posibles múltiples ramas
        actualizarAsociados(asociados, grupoId, u -> u.set(Asociado_.tipo, tipo));
    }

    @Override
    @Transactional
    @Modifying
    public void desactivar(List<Integer> asociados, String grupoId) {
        actualizarAsociados(asociados, grupoId, u -> u.set(Asociado_.activo, false));
    }

    private void actualizarAsociados(List<Integer> asociados, String grupoId, Consumer<CriteriaUpdate<Asociado>> actualizacionColumnas) {
        // TODO Reemplazar código del método con jOOQ, cambiar tambien la generación
        // de metadata de JPA en el build, no se utiliza en ningún sitio mas.
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Asociado> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Asociado.class);

        Root<Asociado> root = criteriaUpdate.from(Asociado.class);

        actualizacionColumnas.accept(criteriaUpdate);

        Predicate asociadosImplicados = root.get(Asociado_.id).in(asociados);
        Predicate grupoDeLosAsociados = criteriaBuilder.equal(root.get(Asociado_.grupoId), grupoId);
        criteriaUpdate.where(criteriaBuilder.and(asociadosImplicados, grupoDeLosAsociados));

        Query query = entityManager.createQuery(criteriaUpdate);
        query.executeUpdate();
    }
}
