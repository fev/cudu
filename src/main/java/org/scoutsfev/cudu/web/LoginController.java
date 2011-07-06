/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scoutsfev.cudu.web;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scoutsfev.cudu.domain.AsociadosTrimestre;
import org.scoutsfev.cudu.domain.Estadistica;

import org.scoutsfev.cudu.services.EstadisticaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
@RequestMapping(value = {"/", "/login"})
public class LoginController {
 
    @Autowired
    protected EstadisticaService  estadisticaService;
    protected final Log logger = LogFactory.getLog(getClass());
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String load(HttpServletRequest request, Model model) {
        logger.info("load");
                
        Collection c  = estadisticaService.getNumGruposYGrupoMenosNumeroso();
        int numGrupos = 0 ;
        
        String nombreGrupoMenosNumeroso = "Ninguno";
        int valorGrupoMenosNumeroso = 0;

        Iterator i = c.iterator();
        while (i.hasNext())
        {
            Object []o = (Object []) i.next();

            Integer value = ((Long)o[0]).intValue();
            String lab = o[1].toString();
            
            if(lab.compareTo("Grupos") ==0)
            {
                numGrupos = value;
            } else //tendra que ser el del grupo menos numeroso
            {
                nombreGrupoMenosNumeroso = lab;
                valorGrupoMenosNumeroso = value;
            }                
        }
        
        AsociadosTrimestre estadistica  = estadisticaService.getInfoEstadisticasAsociados();
        
        estadistica.setAsociados();
        estadistica.setGrupos(numGrupos);
        
        estadistica.setGrupoMenosNumeroso(nombreGrupoMenosNumeroso);
        estadistica.setGrupoMenosNumerosoCantidad(valorGrupoMenosNumeroso);
        
        model.addAttribute("datosEstadistica",estadistica);
           
        model.addAttribute("nombreGrupoMenosNumeroso", nombreGrupoMenosNumeroso );
        model.addAttribute("valorGrupoMenosNumeroso", valorGrupoMenosNumeroso);
                            
        return "login";
                
	}
}
