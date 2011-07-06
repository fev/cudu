/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scoutsfev.cudu.services;

import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.scoutsfev.cudu.domain.AsociadosTrimestre;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author gaxp
 */
public class AsociadosTrimestreServiceImpl  extends StorageServiceImpl<AsociadosTrimestre> 
implements AsociadosTrimestreService {
     

    @Override
    public Collection getAsociadoTrimestre() {
        EntityManager em = this.entityManager;
        
        Query q = em.createQuery("select object(c) from AsociadoTrimestre as c order by anyo, trimestre");
        return q.getResultList();  
    }
    
}
