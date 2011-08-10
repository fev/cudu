/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scoutsfev.cudu.services;

import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;
import org.scoutsfev.cudu.domain.Curso;
import org.scoutsfev.cudu.domain.Monografico;
import javax.persistence.Query;
import org.scoutsfev.cudu.domain.MonograficosEnCursos;
import org.springframework.stereotype.Repository;
/**
 *
 * @author gaxp
 */
@Repository
public class CursoServiceImpl extends StorageServiceImpl<Curso> 
implements CursoService{

    @Override
    public List<Monografico> getMonograficosCurso(Curso curso) {
        EntityManager em = this.entityManager;

        Query q = em.createQuery("select object(mc.monografico) from MonograficosEnCursos  mc as m where mc.idcurso =:idcuso");
        q.setParameter("idcurso", curso.getId());

        return q.getResultList();        
    }

    @Override
    public List<String> getBloquesCurso(Curso curso) {
        EntityManager em = this.entityManager;
        
        Query q = em.createQuery("select distinct bloque from MonograficosEnCursos mc where mc.idcurso =:idcurso order by bloque");
        q.setParameter("idcurso", curso.getId());

        return q.getResultList();
    }

    @Override
    public List<Monografico> getMonograficosBloque(Curso curso, String bloque) {
        EntityManager em = this.entityManager;
        
        Query q = em.createQuery("select object(m.monografico) from MonograficosEnCursos  mc as m where mc.idcurso =:idcurso and mc.bloque =:bloque");
        q.setParameter("idcurso", curso.getId());
        q.setParameter("bloque", bloque);

        return q.getResultList();
    }

    @Override
    public List<Curso> getCursos() {
        EntityManager em = this.entityManager;
        
        Query q = em.createQuery("select object(c) from Curso as c");
        return q.getResultList();
        
    }
/*
    @Override
    public Curso getCursoActual(String acronimo) {
        EntityManager em = this.entityManager;
        Calendar c = Calendar.getInstance();
        int anyo = c.get(Calendar.YEAR);
        Query q = em.createQuery("select object(c) from Curso c where acronimo =:acronimo and anyo =:anyo")
                .setParameter("acronimo", acronimo)
                .setParameter("anyo", anyo);        

        Curso curso =(Curso)q.getSingleResult();
        return curso;
    }*/
    
    
    @Override
    public List<MonograficosEnCursos> getMonograficosEnCursoActual(String acronimo) {
        EntityManager em = this.entityManager;
        Calendar c = Calendar.getInstance();
        int anyo = c.get(Calendar.YEAR);
        Query q = em.createQuery("select object(c) from MonograficosEnCursos c where c.curso.acronimo =:acronimo and c.curso.anyo =:anyo order by c.bloque")
                .setParameter("acronimo", acronimo)
                .setParameter("anyo", anyo);        

        List<MonograficosEnCursos> monograficosEnCursos =(List<MonograficosEnCursos>)q.getResultList();
        return monograficosEnCursos;
    }

    @Override
    public Curso getCursoDeUnAnyo(String nombre, int anyo) {
        EntityManager em = this.entityManager; 
        Query q = em.createQuery("select object(c) from Curso as c where idcurso =:idcuso  and anyo :=anyo");
        q.setParameter("nombre", nombre);
        q.setParameter("anyo", anyo);
        

        return (Curso)q.getSingleResult();
    }
    
    
}
