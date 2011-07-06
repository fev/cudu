/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scoutsfev.cudu.services;

import java.util.List;
import org.scoutsfev.cudu.domain.MisCursos;

/**
 *
 * @author gaxp
 */
public interface MiCursoService {
    public List<MisCursos> getMisCursosRealizados(int idAsociado);
    public List<MisCursos> getMisCursosActuales(int idAsociado);
    
}
