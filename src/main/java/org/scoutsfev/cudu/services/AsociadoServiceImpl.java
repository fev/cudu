package org.scoutsfev.cudu.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.scoutsfev.cudu.domain.Asociado;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
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
        
        private String componerFiltroCargo(String cargos) {
		String filtroCargos = "";
		if (!StringUtils.isBlank(cargos)) {
			ArrayList<String> arr = new ArrayList<String>();
			for (char cargo : cargos.toCharArray()) {
				if (cargo == 'P') { arr.add("cargo_presidencia = true"); continue; }
				if (cargo == 'S') { arr.add("cargo_secretaria = true"); continue; }
				if (cargo == 'T') { arr.add("cargo_tesoreria = true"); continue; }
				if (cargo == 'V') { arr.add("cargo_vocal = true"); continue; }
				if (cargo == 'O') { arr.add("cargo_otro = true"); continue; }
                                if (cargo == 'N') { arr.add("cargo_consiliario= true"); continue; }
                                if (cargo == 'C') { arr.add("cargo_cocina= true"); continue; }
                                if (cargo == 'I') { arr.add("cargo_intendencia= true"); continue; }
			}
			if (arr.size() > 0)
				filtroCargos = " AND (" + StringUtils.join(arr.toArray(), " OR ") + ")"; 
		}
		return filtroCargos;
	}

	@SuppressWarnings("unchecked")
	public Collection<Asociado> findWhere(String idGrupo, String columnas,
			String campoOrden, String sentidoOrden, int inicio,
			int resultadosPorPágina, String tipos, String ramas, boolean eliminados, int asociacion) {
		
		// Filtrado por tipo de 
                //storage.updateGrupoGroup(ids.split(","), idGrupoasociado (joven, kraal, comite)
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
        public Integer getIdAsociado(String usu)
        {
            Object o = null;
            try{
                 o = (Integer)this.entityManager
                        .createQuery("SELECT id from Asociado a where usuario = :usu")
                        .setParameter("usu", usu)
                        .getSingleResult();
            }
            catch (javax.persistence.NoResultException no)
            {
                o = null;
            }
            finally{
            
                return (Integer)o;
            }
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
        
        @Override
	@Transactional
	public boolean deleteGroup(String [] ids) {
            String whereRestriccion="id = " + ids[0];
            for(int i = 1; i < ids.length; i++)
            {
                whereRestriccion=whereRestriccion+" OR id = " +ids[i];
            }
            
		int n = this.entityManager
			.createQuery("UPDATE Asociado SET fechaBaja = :fechaBaja WHERE " +whereRestriccion)
			.setParameter("fechaBaja", new Date())
			.executeUpdate();
		
                for(int i = 1; i < ids.length; i++)
                {
                    auditoria.registrar(AuditoriaService.Operacion.Descartar, AuditoriaService.Entidad.Asociado, ids[i] + ":" + n);
                }
		return (n == 1);
	}
        
        @Override
	@Transactional
        public boolean updateGrupoGroup(String[] ids,String idGrupo)
        {
            return updateFieldInGroup("idgrupo",idGrupo,ids);
        }
        
        @Override
	@Transactional
        public boolean updateFieldInGroup(String column,String value,String [] ids)
        {
            String whereRestriccion="id = " + ids[0];
            for(int i = 1; i < ids.length; i++)
            {
                whereRestriccion=whereRestriccion+" OR id = " +ids[i];
            }
            
		int n = this.entityManager
			.createQuery("UPDATE Asociado SET " + column +" = '" + value + 
                        "' WHERE " +whereRestriccion)
			.executeUpdate();
		
                for(int i = 1; i < ids.length; i++)
                {
                    auditoria.registrar(AuditoriaService.Operacion.Modificar, AuditoriaService.Entidad.Asociado, ids[i] + ":" + n);
                }
		return (n == 1);
        }
        
        
        @Override
        public Asociado findemail(String email)
        {
            Asociado asociado = (Asociado)this.entityManager
                    .createQuery("SELECT a from Asociado a where email= :email")
                    .setParameter("usu", email)
                    .getSingleResult();
            
            
            return asociado;
        }
        
        @Override
        public List getRecorridoAsociado(int id)
        {
            
            String select = "select ha.id, "
                    + "      ha.tipo, "
                    + "      ha.ramas, "
                    + "      g.nombre, "
                    + "      max(to_char(ha.fecha, 'dd/mm/yyyy')) "+
                    " from historico_asociados ha, grupo g "
                    + " where g.id = ha.idgrupo and ha.id = :id "
                    + "group by  ha.id,ha.tipo,ha.ramas,g.nombre";
            
            List  recorrido= this.entityManager.createNativeQuery(select).setParameter("id", id).getResultList();            
            return recorrido;            
        }

    @Override
    public void deleteFromDB(int idAsociado) {
        int n = this.entityManager
			.createQuery("DELETE FROM Asociado WHERE id = :id")
			.setParameter("id", idAsociado)
			.executeUpdate();
		
		auditoria.registrar(AuditoriaService.Operacion.Eliminar, AuditoriaService.Entidad.Asociado, Integer.toString(idAsociado) + ":" + n);
	}

}
