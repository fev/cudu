package org.scoutsfev.cudu.web;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.domain.validators.EdadValidator;
import org.scoutsfev.cudu.services.AsociadoService;
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

    @RequestMapping(value = {"/", "/dashboard"}, method = RequestMethod.GET)
    public String load(HttpServletRequest request, Model model) {
        logger.info("load");

        int edad = 100;
        int idAsociado = -1;
		model.addAttribute("usuarioActual", Usuario.obtenerActual());		

        String username =  Usuario.obtenerActual().getUsername();
        if(asociadoService!= null && asociadoService.getIdAsociado(username)!=null)
        {
            idAsociado = asociadoService.getIdAsociado(Usuario.obtenerActual().getUsername());
            if(idAsociado>-1)
            {
                Asociado a = asociadoService.find(idAsociado);

                if(a != null)
                    edad = EdadValidator.getAnyos(a);
            }
         
        }
       model.addAttribute("anyosAsociado", edad);
        model.addAttribute("idAsociado", idAsociado);
        return "dashboard";
    }
}
        
