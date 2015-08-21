package org.scoutsfev.cudu.services;

import org.scoutsfev.cudu.domain.Curso;
import org.scoutsfev.cudu.storage.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    @Autowired
    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public Page<Curso> listado(Pageable pageable) {
        return cursoRepository.findAll(pageable);
    }

    public Curso guardar(Curso curso) {
        return cursoRepository.save(curso);
    }

    public Curso obtener(Integer cursoId) {
        return cursoRepository.findOne(cursoId);
    }
}
