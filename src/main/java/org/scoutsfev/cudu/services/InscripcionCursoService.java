 package org.scoutsfev.cudu.services;

import org.scoutsfev.cudu.domain.InscripcionCurso;

public interface  InscripcionCursoService extends StorageService<InscripcionCurso> 
{
    public InscripcionCurso merge(InscripcionCurso entity);
}
