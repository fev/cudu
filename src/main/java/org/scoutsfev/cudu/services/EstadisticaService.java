/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scoutsfev.cudu.services;

import java.util.Collection;
import java.util.List;
import org.scoutsfev.cudu.domain.AsociadosTrimestre;

/**
 *
 * @author gaxp
 */
public interface EstadisticaService
{

    public Collection getInfoEstadisticasVoluntarios();
    public Collection getInfoEstadisticasRamasJoven();   
    public AsociadosTrimestre getInfoEstadisticasAsociados();
    
    public Collection getNumGruposYGrupoMenosNumeroso();
    public Object getGrupoMenosActualizado();
    public List getInfoEstadisticasTrimestresGrupo(String idGrupo);
    public AsociadosTrimestre getInfoEstadisticasAsociadosPorGrupo(String idGrupo);
    public List getAsociadoPorTrimestreYGrupo(String idGrupo);

}

