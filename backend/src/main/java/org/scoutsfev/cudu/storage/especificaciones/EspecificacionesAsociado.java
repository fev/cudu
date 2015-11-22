package org.scoutsfev.cudu.storage.especificaciones;

import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.Asociado_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;

public class EspecificacionesAsociado {

    public static Specification<Asociado> PorGrupoSegunRama(int asociadoId, String grupoId, boolean colonia, boolean manada, boolean exploradores, boolean expedicion, boolean ruta) {
        return (root, query, cb) -> {
            ArrayList<Predicate> predicados = new ArrayList<>();
            if (colonia) predicados.add(cb.equal(root.get(Asociado_.ramaColonia), true));
            if (manada) predicados.add(cb.equal(root.get(Asociado_.ramaManada), true));
            if (exploradores) predicados.add(cb.equal(root.get(Asociado_.ramaExploradores), true));
            if (expedicion) predicados.add(cb.equal(root.get(Asociado_.ramaExpedicion), true));
            if (ruta) predicados.add(cb.equal(root.get(Asociado_.ramaRuta), true));

            // where grupo_id = ? and (manada = true or ruta = true)
            if (predicados.size() >= 1) {
                Predicate selectorGrupo = cb.equal(root.get(Asociado_.grupoId), grupoId);
                Predicate[] ramas = new Predicate[predicados.size()];
                return cb.and(selectorGrupo, cb.or(predicados.toArray(ramas)));
            }

            // No debería darse salvo que hayamos cometido algún error en el backend, pero si el
            // usuario tiene restricciones de rama pero no pertenece a ninguna, únicamente podemos
            // mostrarle sus datos. where a.id = ?
            return cb.equal(root.get(Asociado_.id), asociadoId);
        };
    }
}
