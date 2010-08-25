package org.scoutsfev.cudu.services;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.scoutsfev.cudu.domain.Asociado;
import org.springframework.beans.factory.annotation.Autowired;

public class AsociadoServiceImpl 
	extends StorageServiceImpl<Asociado> 
	implements AsociadoService {
	
	@Autowired
	protected AuditoriaService auditoria;
	
	private String componerFiltroTipo(String tipos) {
		String filtroTipos = "";
		if (!StringUtils.isBlank(tipos)) {
			// dirtySuperHack!
			filtroTipos = " AND tipo IN ('" + tipos.replace(",", "','") + "') ";
		}
		return filtroTipos;
	}

	private String componerFiltroRamas(String ramas) {
		String filtroRamas = "";
		if (!StringUtils.isBlank(ramas)) {
			ArrayList<String> arr = new ArrayList<String>();
			for (char rama : ramas.toCharArray()) {
				if (rama == 'C') { arr.add("rama_colonia = true"); continue; }
				if (rama == 'M') { arr.add("rama_manada = true"); continue; }
				if (rama == 'E') { arr.add("rama_exploradores = true"); continue; }
				if (rama == 'P') { arr.add("rama_pioneros = true"); continue; }
				if (rama == 'R') { arr.add("rama_rutas = true"); continue; }
			}
			if (arr.size() > 0)
				filtroRamas = " AND (" + StringUtils.join(arr.toArray(), " OR ") + ")"; 
		}
		return filtroRamas;
	}

	@SuppressWarnings("unchecked")
	public Collection<Asociado> findWhere(String idGrupo, String columnas,
			String campoOrden, String sentidoOrden, int inicio,
			int resultadosPorPágina, String tipos, String ramas) {
		
		// Filtrado por tipo de asociado (joven, kraal, comite)
		String filtroTipos = componerFiltroTipo(tipos);
		
		// Filtrado por rama
		String filtroRamas = componerFiltroRamas(ramas);
		
		// Filtro por grupo, implícito,
		// en usuarios de administración viene a null.
		String filtroGrupo = "1 = 1 ";
		if (idGrupo != null)
			filtroGrupo = "idGrupo = :idGrupo ";

		Query query = this.entityManager
			.createQuery("SELECT " + columnas + " FROM Asociado WHERE "
					+ filtroGrupo + filtroTipos +	filtroRamas
					+ " ORDER BY " + campoOrden + " " + sentidoOrden);
		
		if (idGrupo != null)
			query.setParameter("idGrupo", idGrupo);
		
		return query.setFirstResult(inicio).setMaxResults(resultadosPorPágina).getResultList();
	}

	public long count() {
		// TODO Substituir por tabla donde se guarde el recuento para acelerar el COUNT de PostgreSQL.
		return (Long) this.entityManager
			.createQuery("SELECT COUNT(a) FROM Asociado a")
			.getSingleResult();
	}
	
	public long count(String idGrupo, String tipos, String ramas) {
		if (idGrupo == null)
			return count();
		
		String filtroTipos = componerFiltroTipo(tipos);
		String filtroRamas = componerFiltroRamas(ramas);

		return (Long) this.entityManager
			.createQuery("SELECT COUNT(a) FROM Asociado a WHERE idGrupo = :idGrupo " + filtroRamas + filtroTipos)
			.setParameter("idGrupo", idGrupo)
			.getSingleResult();
	}
	
	@Override
	public Asociado merge(Asociado entity) {
		Asociado persistedEntity = super.merge(entity);
		auditoria.registrar(AuditoriaService.Operacion.Almacenar, AuditoriaService.Entidad.Asociado, persistedEntity.getId().toString());
		return persistedEntity;
	}
}
