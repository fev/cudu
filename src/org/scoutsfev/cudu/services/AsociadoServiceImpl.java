package org.scoutsfev.cudu.services;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.scoutsfev.cudu.domain.Asociado;

public class AsociadoServiceImpl 
	extends StorageServiceImpl<Asociado> 
	implements AsociadoService {

	@SuppressWarnings("unchecked")
	public Collection<Asociado> findWhere(String idGrupo, String columnas,
			String campoOrden, String sentidoOrden, int inicio,
			int resultadosPorPágina, String tipos, String ramas) {
		
		// Filtrado por tipo de asociado (joven, kraal, comite)
		String filtroTipos = "";
		if (!StringUtils.isBlank(tipos)) {
			// dirtySuperHack!
			filtroTipos += "AND tipo IN ('" + tipos.replace(",", "','") + "') ";
		}
		
		// Filtrado por rama
		String filtroRamas = "";
		if (!StringUtils.isBlank(ramas)) {
			ArrayList<String> arr = new ArrayList<String>();
			for (char rama : ramas.toCharArray()) {
				if (rama == 'C') { arr.add("rama_colonia = true"); continue; }
				if (rama == 'M') { arr.add("rama_manada = true"); continue; }
				if (rama == 'E') { arr.add("rama_exploradores = true"); continue; }
				if (rama == 'P') { arr.add("rama_pioneros = true"); continue; }
				if (rama == 'T') { arr.add("rama_rutas = true"); continue; }
			}
			if (arr.size() > 0)
				filtroRamas += "AND (" + StringUtils.join(arr.toArray(), " OR ") + ")"; 
		}

		return this.entityManager
			.createQuery("SELECT " + columnas + " FROM Asociado WHERE idGrupo = :idGrupo " 
					+ filtroTipos +	filtroRamas
					+ " ORDER BY " + campoOrden + " " + sentidoOrden)
			.setParameter("idGrupo", idGrupo)
			.setFirstResult(inicio)
			.setMaxResults(resultadosPorPágina)
			.getResultList();
	}

	public long count() {
		// TODO Enlazar con findWhere, se necesita para el DataTable de YUI
		// TODO Substituir por tabla donde se guarde el recuento
		// para acelerar el COUNT de PostgreSQL.
		return (Long) this.entityManager
			.createQuery("SELECT COUNT(a) FROM Asociado a")
			.getSingleResult();
	}
	
	public long count(String idGrupo) {
		return (Long) this.entityManager
			.createQuery("SELECT COUNT(a) FROM Asociado a WHERE idGrupo = :idGrupo")
			.setParameter("idGrupo", idGrupo)
			.getSingleResult();
	}
}
