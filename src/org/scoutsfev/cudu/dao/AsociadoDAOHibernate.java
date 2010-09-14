package org.scoutsfev.cudu.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.scoutsfev.cudu.domain.Asociado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("asociadoDAO")
public class AsociadoDAOHibernate extends GenericDAOHibernateObject<Asociado, Integer> implements AsociadoDAO {

	@Autowired
	public AsociadoDAOHibernate(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	protected Class<Asociado> getDomainClass() {
		return Asociado.class;
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

		Session session = template.getSessionFactory().openSession();
		Query query = session.createQuery("SELECT " + columnas + " FROM Asociado WHERE "
					+ filtroGrupo + filtroTipos +	filtroRamas
					+ " ORDER BY " + campoOrden + " " + sentidoOrden);
		
		if (idGrupo != null)
			query.setParameter("idGrupo", idGrupo);
		
		List<Asociado> lasociados = query.setFirstResult(inicio).setMaxResults(resultadosPorPágina).list();
		session.close();
		
		return lasociados;
	}
	
	public long count(String idGrupo, String tipos, String ramas) {
		if (idGrupo == null)
			return count();
		
		String filtroTipos = componerFiltroTipo(tipos);
		String filtroRamas = componerFiltroRamas(ramas);

		Session session = template.getSessionFactory().openSession();
		BigInteger l = (BigInteger) 
			session
			.createSQLQuery("SELECT COUNT(a) FROM Asociado a WHERE idGrupo = :idGrupo " + filtroRamas + filtroTipos)
			.setParameter("idGrupo", idGrupo).uniqueResult();
		
		session.close();
		return l.longValue();
	}
	
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
}
