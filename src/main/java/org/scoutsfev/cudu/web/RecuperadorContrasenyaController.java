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
import org.scoutsfev.cudu.services.AsociadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

@RequestMapping("/recuperarpassword")
public class RecuperadorContrasenyaController {
    
    
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    protected AsociadoService asociadoService;

    
    
    @RequestMapping(value = "/recuperarpassword", method = RequestMethod.GET)
    public String load(HttpServletRequest request, Model model) {
        logger.info("setupForm /recuperarpassword/");

        return "recuperadorContrasenya";
                
	}


    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(@ModelAttribute("email") @Valid String email,
    BindingResult result, SessionStatus status) 
    {
            logger.info("processSubmit: " + email);

            if (result.hasErrors()) {
                    logger.info("Validation errors.");
                    //	for(ObjectError error: result.getAllErrors()) logger.info(error.getCode());
                    return "usuario";
            }

            Asociado asociado = asociadoService.findemail(email);

            if(asociado!=null)
            {
                System.out.println("toca enviar email");
            }
            return "redirect:/recuperarpassword/" + email + "?ok";
    }
    
    
    
}
