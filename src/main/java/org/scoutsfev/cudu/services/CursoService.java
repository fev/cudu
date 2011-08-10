/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scoutsfev.cudu.services;

import java.util.List;
import org.scoutsfev.cudu.domain.Curso;
import org.scoutsfev.cudu.domain.Monografico;
import org.scoutsfev.cudu.domain.MonograficosEnCursos;

/**
 *
 * @author gaxp
 */
public interface CursoService {
    
    public List<Monografico> getMonograficosCurso(Curso curso);
    public List<String> getBloquesCurso(Curso curso);
    public List<Monografico> getMonograficosBloque(Curso curso,String bloque);
    public List<Curso> getCursos();
    public List<MonograficosEnCursos> getMonograficosEnCursoActual(String nombre);
    public Curso getCursoDeUnAnyo(String nombre,int anyo);
 
    
    
    
}
