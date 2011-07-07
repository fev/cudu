/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scoutsfev.cudu.web;


import org.scoutsfev.cudu.services.models.PasswordUsuario;
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



    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String setupForm(Model model, HttpServletRequest request) {
            logger.info("setupForm /cambiarPassword/");

            Usuario usuario = usuarioService.obtenerUsuarioActual();
            int idAsociado = asociadoService.getIdAsociado(usuario.getUsername());
            Asociado asociado = asociadoService.find(idAsociado);
            
            
            if (asociado == null)
                    return "redirect:/404 asociado nulo!";
            
            

            model.addAttribute("asociado", asociado);
            model.addAttribute("usuario", usuario);
            model.addAttribute("passwordusuario", new PasswordUsuario(usuario.getUsername()));
            return "cambiarPassword";
    }

    
    
    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(@ModelAttribute("passwordusuario") @Valid PasswordUsuario passUsuario,    BindingResult result, SessionStatus status) {
            logger.info("processSubmit: " + passUsuario.getUsername());
            if (result.hasErrors()) 
            {
                    logger.info("Validation errors.");
                    //	for(ObjectError error: result.getAllErrors()) logger.info(error.getCode());
                    return "usuario";
            }
//<form:input path="username" >${usuario.username}</form:input>
            Usuario userCheck = usuarioService.obtenerUsuarioActual();
            if(userCheck.getPassword().equals(passUsuario.getAnteriorPassword()))
            {
                if(passUsuario.getPassword().equals(passUsuario.getConfirmarPassword()))
                {
                    
                    userCheck.setPassword(passUsuario.getPassword());
                    Usuario us = new Usuario();
                    us.setUsername(userCheck.getUsername());
                    us.setPassword(userCheck.getPassword());
                    us.setGrupo(userCheck.getGrupo());
                    us.setEnabled(userCheck.isEnabled());
                    us.setNombreCompleto(userCheck.getNombreCompleto());

                    Usuario persistedEntity = usuarioService.merge(us);
                    status.setComplete();
                    return "redirect:/cambiarpassword?ok/";
                }
            }
            return "redirect:/cambiarpassword/" + "no!!!";
            

            
    }
    
}
