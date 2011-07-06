package org.scoutsfev.cudu.services;

import java.util.Collection;
import javax.persistence.Query;

import org.scoutsfev.cudu.domain.Grupo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class GrupoServiceImpl 
	extends StorageServiceImpl<Grupo>
	implements GrupoService {

	public Grupo find(String id) {
		return this.entityManager.find(Grupo.class, id);
	}
	
	public Grupo findByUser(String username) {
		Query query = this.entityManager.createQuery("SELECT u.grupo FROM Usuario u WHERE u.username = :username");
		query.setParameter("username", username);
		Grupo grupo = (Grupo) query.getSingleResult();
		return grupo;
	}

	@Override
	@Transactional
	public Grupo merge(Grupo g) {
		Grupo grupo = this.entityManager.merge(g);
		auditoria.registrar(AuditoriaService.Operacion.Almacenar, AuditoriaService.Entidad.Grupo, g.getId());
		return grupo;
	}
        
        @Override
        @SuppressWarnings("unchecked")
	public Collection<Grupo> findWhere(String columnas,
            String campoOrden, String sentidoOrden, int inicio,
            int resultadosPorPágina, boolean eliminados, int asociacion) 
        {
	
            String select  = "SELECT " + columnas + " FROM Grupo g ";  
            
            if(asociacion>=0 && asociacion<3)
                select += "WHERE g.asociacion = :asociacion ";

            select +=  " ORDER BY " + campoOrden + " " + sentidoOrden;
	
            Query query = this.entityManager.createQuery(select);
		
            if ((asociacion >= 0) && (asociacion <= 2))
                    query.setParameter("asociacion", asociacion);
		
            return query.setFirstResult(inicio).setMaxResults(resultadosPorPágina).getResultList();
	}
        
         @Override
        @SuppressWarnings("unchecked")
	public Collection<Grupo> findWhere(String columnas,
            String campoOrden, String sentidoOrden, int inicio,
            int resultadosPorPágina, boolean eliminados, String[] asociaciones) 
        {
	
            String select  = "SELECT " + columnas + " FROM Grupo g ";  
            
            if(asociaciones.length>0){
                if(asociaciones[0]!=null&&!asociaciones[0].equals(""))
                select += "WHERE g.asociacion = "+ asociaciones[0]+" ";
                for(int i = 1 ; i < asociaciones.length;i++)
                {
                    select += "OR g.asociacion = "+ asociaciones[i]+" ";
                }
                    

            }
            select +=  " ORDER BY " + campoOrden + " " + sentidoOrden;
            System.out.println(select);
            Query query = this.entityManager.createQuery(select);		
            return query.setFirstResult(inicio).setMaxResults(resultadosPorPágina).getResultList();
	}
        
        @Override
        public long count() {
		return (Long) this.entityManager
			.createQuery("SELECT COUNT(a) FROM Grupo a ")
			.getSingleResult();
	}
}
