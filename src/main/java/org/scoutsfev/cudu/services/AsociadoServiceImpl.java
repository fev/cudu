package org.scoutsfev.cudu.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.scoutsfev.cudu.domain.Asociado;
import org.springframework.transaction.annotation.Transactional;

public class AsociadoServiceImpl 
	extends StorageServiceImpl<Asociado> 
	implements AsociadoService {
	
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
			int resultadosPorPágina, String tipos, String ramas, boolean eliminados, int asociacion) {
		
		// Filtrado por tipo de asociado (joven, kraal, comite)
		String filtroTipos = componerFiltroTipo(tipos);
		
		// Filtrado por rama
		String filtroRamas = componerFiltroRamas(ramas);
		
		// Filtro por grupo, implícito,
		// en usuarios de administración viene a null.
		String filtroGrupo = "1 = 1 ";
		if (idGrupo != null)
			filtroGrupo = "idGrupo = :idGrupo ";
		else if ((asociacion >= 0) && (asociacion <= 3))
			filtroGrupo = "asociacion = :asociacion"; // Hack!
		
		Query query = this.entityManager
			.createQuery("SELECT " + columnas + " FROM Asociado WHERE "
					+ filtroGrupo + filtroTipos + filtroRamas
					+ (eliminados ? null : " AND fechaBaja IS NULL ")
					+ " ORDER BY " + campoOrden + " " + sentidoOrden);
		
		if (idGrupo != null)
			query.setParameter("idGrupo", idGrupo);
		else if ((asociacion >= 0) && (asociacion <= 2))
			query.setParameter("asociacion", asociacion);
		
		return query.setFirstResult(inicio).setMaxResults(resultadosPorPágina).getResultList();
	}

	protected long count(int asociacion) {
		// TODO Substituir por tabla donde se guarde el recuento para acelerar el COUNT de PostgreSQL.
		if (asociacion != -1)
			return (Long) this.entityManager
				.createQuery("SELECT COUNT(a) FROM Asociado a WHERE asociacion = :asociacion")
				.setParameter("asociacion", asociacion)
				.getSingleResult();
			
		return (Long) this.entityManager
			.createQuery("SELECT COUNT(a) FROM Asociado a")
			.getSingleResult();
	}
	
	public long count(String idGrupo, String tipos, String ramas, boolean eliminados, int asociacion) {
		if (idGrupo == null)
			return count(asociacion);
		
		String filtroTipos = componerFiltroTipo(tipos);
		String filtroRamas = componerFiltroRamas(ramas);

		return (Long) this.entityManager
			.createQuery("SELECT COUNT(a) FROM Asociado a WHERE idGrupo = :idGrupo "
					+ (eliminados ? null : " AND fechaBaja IS NULL ")
					+ filtroRamas + filtroTipos)
			.setParameter("idGrupo", idGrupo)
			.getSingleResult();
	}
	
	@Override
	@Transactional
	public Asociado merge(Asociado entity) {
		Asociado persistedEntity = super.merge(entity);
		auditoria.registrar(AuditoriaService.Operacion.Almacenar, AuditoriaService.Entidad.Asociado, persistedEntity.getId().toString());
		return persistedEntity;
	}

	
	@Override
	public Asociado find(String id) {
		Asociado asociado = super.find(id);
		auditoria.registrar(AuditoriaService.Operacion.Acceder, AuditoriaService.Entidad.Asociado, id);
		return asociado;
	}

	@Override
	@Transactional
	public boolean delete(int id) {
		int n = this.entityManager
			.createQuery("UPDATE Asociado SET fechaBaja = :fechaBaja WHERE id = :id")
			.setParameter("fechaBaja", new Date())
			.setParameter("id", id)
			.executeUpdate();
		
		auditoria.registrar(AuditoriaService.Operacion.Descartar, AuditoriaService.Entidad.Asociado, Integer.toString(id) + ":" + n);
		return (n == 1);
	}
}
