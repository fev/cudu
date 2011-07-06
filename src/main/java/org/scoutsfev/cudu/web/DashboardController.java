package org.scoutsfev.cudu.web;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.AsociadosTrimestre;
import org.scoutsfev.cudu.domain.Grupo;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.domain.validators.EdadValidator;
import org.scoutsfev.cudu.services.AsociadoService;
import org.scoutsfev.cudu.services.EstadisticaService;
import org.scoutsfev.cudu.services.GrupoService;
import org.scoutsfev.cudu.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping(value = {"/", "/dashboard"})
@SessionAttributes(types = Usuario.class)
public class DashboardController {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    protected UsuarioService usuarioService;


    @Autowired
    protected AsociadoService asociadoService;

    @Autowired
    protected EstadisticaService  estadisticaService;
    
    @Autowired
    protected GrupoService  grupoService;
    

    @RequestMapping(value = {"/", "/dashboard"}, method = RequestMethod.GET)
    public String load(HttpServletRequest request, Model model) {
        logger.info("load");

        int edad = 100;
        int idAsociado = -1;
        String grupoAsociado = "";
        model.addAttribute("usuarioActual", Usuario.obtenerActual());


        String username =  Usuario.obtenerActual().getUsername();
        if(asociadoService!= null && asociadoService.getIdAsociado(username)!=null)
        {
            idAsociado = asociadoService.getIdAsociado(Usuario.obtenerActual().getUsername());
            if(idAsociado>-1)
            {
                Asociado a = asociadoService.find(idAsociado);
                grupoAsociado = a.getGrupo().getId();
                if(a != null)
                    edad = EdadValidator.getAnyos(a);
            }
        }
        model.addAttribute("anyosAsociado", edad);
        model.addAttribute("idAsociado", idAsociado);
        model.addAttribute("grupoAsociado", grupoAsociado);

        ///Referente a las estadísticas
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
        
        Object o = estadisticaService.getGrupoMenosActualizado();
        String nombreGrupoMenosActualizado = ((Object [])o)[1].toString();
        String fechaGrupoMenosActualizado = ((Object [])o)[0].toString();
        fechaGrupoMenosActualizado = fechaGrupoMenosActualizado.split(" ")[0];
         model.addAttribute("nombreGrupoMenosActualizado", nombreGrupoMenosActualizado );
        model.addAttribute("fechaGrupoMenosActualizado", fechaGrupoMenosActualizado);
        
        AsociadosTrimestre estadistica  = estadisticaService.getInfoEstadisticasAsociados();
        
        estadistica.setAsociados();
        estadistica.setGrupos(numGrupos);
        
        estadistica.setGrupoMenosNumeroso(nombreGrupoMenosNumeroso);
        estadistica.setGrupoMenosNumerosoCantidad(valorGrupoMenosNumeroso);
        
        model.addAttribute("datosEstadistica",estadistica);
           
        model.addAttribute("nombreGrupoMenosNumeroso", nombreGrupoMenosNumeroso );
        model.addAttribute("valorGrupoMenosNumeroso", valorGrupoMenosNumeroso);

                
        String columnas = "id,nombre";
        String campoOrden="id";
        String orden = "desc";
        int numTotal = (int)grupoService.count();
        Collection<Grupo> grupos = grupoService.findWhere(columnas,campoOrden,orden, 0, numTotal, false, -1);
        
        
        model.addAttribute("grupos", grupos);
        
        //model.addAttribute("isAsociado",usuarioService.isAsociado(Usuario.obtenerActual().getUsername()));
        return "dashboard";
                
    }
}
