package org.scoutsfev.cudu.services;

import org.scoutsfev.cudu.domain.Curso;
import org.scoutsfev.cudu.domain.dto.MiembroCursoDto;
import org.scoutsfev.cudu.storage.CursoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CursoService {

    private static final Logger logger = LoggerFactory.getLogger(CursoService.class);

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
        Curso curso = cursoRepository.findOne(cursoId);
        if (curso == null)
            return null;

        // Añadir la lista de formadores y participantes en una consulta separada
        List<MiembroCursoDto> formadores = new ArrayList<>();
        List<MiembroCursoDto> participantes = new ArrayList<>();
        List<MiembroCursoDto> miembros = cursoRepository.obtenerMiembros(curso.getId());
        for (MiembroCursoDto miembro : miembros) {
            if (miembro.getTipoMiembro().equals("F"))
                formadores.add(miembro);
            else if (miembro.getTipoMiembro().equals("P"))
                participantes.add(miembro);
            else
                logger.error("Tipo de miembro no encontrado. CursoId: " + cursoId + ", Tipo: " + miembro.getTipoMiembro());
        }
        curso.setFormadores(formadores);
        curso.setParticipantes(participantes);
        return curso;
    }

    public void añadirFormador(Integer cursoId, Integer asociadoId) {
        cursoRepository.añadirFormador(cursoId, asociadoId);
    }

    public void quitarFormador(Integer cursoId, Integer asociadoId) {
        cursoRepository.quitarFormador(cursoId, asociadoId);
    }

    public void añadirParticipante(Integer cursoId, Integer asociadoId) {
        cursoRepository.añadirParticipante(cursoId, asociadoId);
    }

    public void quitarParticipante(Integer cursoId, Integer asociadoId) {
        cursoRepository.quitarParticipante(cursoId, asociadoId);
    }
}
