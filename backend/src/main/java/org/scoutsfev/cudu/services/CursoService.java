package org.scoutsfev.cudu.services;

import org.scoutsfev.cudu.domain.CacheKeys;
import org.scoutsfev.cudu.domain.Curso;
import org.scoutsfev.cudu.domain.EstadoInscripcionEnCurso;
import org.scoutsfev.cudu.domain.dto.MiembroCursoDto;
import org.scoutsfev.cudu.storage.CursoRepository;
import org.scoutsfev.cudu.storage.CursoStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    private static final Logger logger = LoggerFactory.getLogger(CursoService.class);

    private final CursoRepository cursoRepository;
    private final CursoStorage cursoStorage;
    private final CacheManager cacheManager;

    @Autowired
    public CursoService(CursoRepository cursoRepository, CursoStorage cursoStorage, CacheManager cacheManager) {
        this.cursoRepository = cursoRepository;
        this.cursoStorage = cursoStorage;
        this.cacheManager = cacheManager;
    }

    public Page<Curso> listado(Pageable pageable) {
        List<Curso> listado = cursoStorage.listado(pageable, Optional.of(true));
        int cursosDisponibles = cursoStorage.numeroDeCursosDisponibles(Optional.of(true));
        return new PageImpl<>(listado, pageable, cursosDisponibles);
    }

    public Page<Curso> listadoCompleto(Pageable pageable) {
        return cursoRepository.findAll(pageable);
    }

    public Curso guardar(Curso curso) {
        Curso cursoGuardado = cursoRepository.save(curso);
        cacheManager.getCache(CacheKeys.NumeroDeCursosDisponibles).clear();
        return cursoGuardado;
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

    public EstadoInscripcionEnCurso añadirParticipante(Integer cursoId, Integer asociadoId) {
        Timestamp fechaInscripcion = Timestamp.valueOf(LocalDateTime.now());
        cursoRepository.añadirParticipante(cursoId, asociadoId, fechaInscripcion);
        return cursoStorage.estadoDeInscripcion(cursoId, asociadoId);
    }

    public EstadoInscripcionEnCurso quitarParticipante(Integer cursoId, Integer asociadoId) {
        cursoRepository.quitarParticipante(cursoId, asociadoId);
        return cursoStorage.estadoDeInscripcion(cursoId, asociadoId);
    }
}
