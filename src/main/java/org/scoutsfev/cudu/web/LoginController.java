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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
@RequestMapping(value = {"/", "/login"})
public class LoginController {
 
    protected final Log logger = LogFactory.getLog(getClass());
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String load(HttpServletRequest request, Model model) {
        logger.info("load");

        int numGrupos = 0 ;
        

                            
        return "login";
                
	}
}
