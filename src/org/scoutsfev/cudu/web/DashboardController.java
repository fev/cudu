package org.scoutsfev.cudu.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scoutsfev.cudu.domain.Usuario;
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
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String load(HttpServletRequest request, Model model) {
		logger.info("load");
		model.addAttribute("usuarioActual", Usuario.obtenerActual());		
		return "dashboard";
	}
}
