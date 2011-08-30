/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scoutsfev.cudu.services;

import java.util.Calendar;

import java.util.List;
import javax.persistence.EntityManager;
import org.scoutsfev.cudu.domain.MisCursos;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author gaxp
 */
public class MiCursoServiceImpl extends StorageServiceImpl<MisCursos> implements MiCursoService {


    @Override
    public List<MisCursos> getMisCursosRealizados(int idAsociado) {
        Calendar dat = Calendar.getInstance();
        dat.set(Calendar.YEAR, dat.get(Calendar.YEAR)-1);
        dat.set(Calendar.MONTH, 7);
        dat.set(Calendar.DAY_OF_MONTH, 1);
        
        
        EntityManager em = this.entityManager; 
        String consulta = "select object(mc) from MisCursos mc where idAsociado=:idAsociado "
                + " AND ronda<:anyoActual)";
                System.out.println(consulta);
        Query q = em.createQuery(consulta);
        q.setParameter("idAsociado", idAsociado);
        
        q.setParameter("anyoActual", dat.getTime(),TemporalType.DATE);
        ///select * from miscursos where (to_char(ronda, 'yyyy'))=(to_char(now(), 'yyyy'))
        //select * from miscursos where (to_char(ronda, 'yyyy'))<(to_char(now(), 'yyyy'))
        return q.getResultList();
    }

    @Override
    public List<MisCursos> getMisCursosActuales(int idAsociado) {
        
        Calendar dat = Calendar.getInstance();
        dat.set(Calendar.YEAR, dat.get(Calendar.YEAR)-1);
        dat.set(Calendar.MONTH, 7);
        dat.set(Calendar.DAY_OF_MONTH, 1);
        
        EntityManager em = this.entityManager; 
        String consulta = "select object(mc) from MisCursos mc where idAsociado=:idAsociado "
                + " AND ronda >=:anyoActual) ";
        System.out.println(consulta);
        Query q = em.createQuery(consulta);
        q.setParameter("idAsociado", idAsociado);
        q.setParameter("anyoActual", dat.getTime(),TemporalType.DATE);
        return q.getResultList();
    }
    
    @Override
    public List<MisCursos> getMiCursoAJActual(int idAsociado) {
        
        Calendar dat = Calendar.getInstance();
        dat.set(Calendar.YEAR, dat.get(Calendar.YEAR)-1);
        dat.set(Calendar.MONTH, 7);
        dat.set(Calendar.DAY_OF_MONTH, 1);
        
        EntityManager em = this.entityManager; 
        String consulta = "select object(mc) from MisCursos mc, Curso c "
                + " where idAsociado=:idAsociado and  mc.misCursosPK.idCurso = c.id and c.acronimo='AJ' "
                + " AND ronda >=:anyoActual) ";
        System.out.println(consulta);
        Query q = em.createQuery(consulta);
        q.setParameter("idAsociado", idAsociado);
        q.setParameter("anyoActual", dat.getTime(),TemporalType.DATE);
        return q.getResultList();
    }
    
}
