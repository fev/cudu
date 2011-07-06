/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scoutsfev.cudu.web;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.services.AsociadoService;
import org.scoutsfev.cudu.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;


@Controller
@RequestMapping("/cambiarPassword")
@SessionAttributes(types = Asociado.class)
public class CambiadorPasswordController {
    
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    protected AsociadoService asociadoService;
    @Autowired
    protected UsuarioService usuarioService;



    @RequestMapping(value = "/{idAsociado}", method = RequestMethod.GET)
    public String setupForm(@PathVariable("idAsociado") int idAsociado, Model model, HttpServletRequest request) {
            logger.info("setupForm /asociado/" + idAsociado);

            Asociado asociado = asociadoService.find(idAsociado);
            
            
            if (asociado == null)
                    return "redirect:/404 asociado nulo!";
            
            Usuario usuario = usuarioService.obtenerUsuarioActual();

            model.addAttribute("asociado", asociado);
            model.addAttribute("usuario", usuario);
            return "cambiarPassword";
    }

    
    
    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(@ModelAttribute("usuario") @Valid Usuario usuario,
    BindingResult result, SessionStatus status) {
            logger.info("processSubmit: " + usuario.getUsername());

            if (result.hasErrors()) 
            {
                    logger.info("Validation errors.");
                    //	for(ObjectError error: result.getAllErrors()) logger.info(error.getCode());
                    return "usuario";
            }

            Usuario userCheck = usuarioService.obtenerUsuarioActual();
            if(userCheck.getPassword().equals(usuario.getAnteriorPassword()))
            {
                if(usuario.getPassword().equals(usuario.getConfirmarPassword()))
                {
                    Usuario persistedEntity = usuarioService.merge(usuario);
                    status.setComplete();
                    return "redirect:/cambiarpassword/" + persistedEntity.getUsername() + "?ok";
                }
            }
            return "redirect:/cambiarpassword/" + "no!!!";
            

            
    }
    
}
