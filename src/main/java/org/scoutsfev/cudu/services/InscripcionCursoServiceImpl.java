
package org.scoutsfev.cudu.services;

import org.scoutsfev.cudu.domain.InscripcionCurso;
import org.springframework.transaction.annotation.Transactional;

public class InscripcionCursoServiceImpl 
    extends StorageServiceImpl<InscripcionCurso> 
    implements InscripcionCursoService {
    
    @Override
    @Transactional
    public InscripcionCurso merge(InscripcionCurso entity)
    {
            InscripcionCurso persistedEntity = super.merge(entity);
            auditoria.registrar(AuditoriaService.Operacion.Almacenar, AuditoriaService.Entidad.InscripcionCurso, 
                    persistedEntity.getAsociado().getId().toString() + " - "+
                    persistedEntity.getCurso().getId().toString() + " - "+
                    persistedEntity.getMonografico().getId().toString());
            return persistedEntity;
    }
}
