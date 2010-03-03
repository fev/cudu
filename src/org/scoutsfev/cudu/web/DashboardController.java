package org.scoutsfev.cudu.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/dashboard.mvc")
public class DashboardController {

	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	protected UsuarioService usuarioService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String load(@RequestParam(value = "id", required = false) String idGrupo, HttpServletRequest request, Model model) {
		logger.debug(idGrupo);
		model.addAttribute("idGrupo", idGrupo);

		HttpSession session = request.getSession();		
		Usuario usuarioActual = (Usuario)session.getAttribute("usuarioActual");

		if (usuarioActual == null) {
			logger.info("Obteniendo información de sesión");			
			UsernamePasswordAuthenticationToken auth = 
	        	  (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
			String userName = auth.getName();
			logger.info("Usuario: " + userName);
			usuarioActual = usuarioService.find(userName);			
			session.setAttribute("usuarioActual", usuarioActual);
		}

		model.addAttribute("usuarioActual", usuarioActual);
		
		
		/*
        UsernamePasswordAuthenticationToken auth = 
        	  (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
          
		WebAuthenticationDetails details = (WebAuthenticationDetails) auth.getDetails();
		String ip = details.getRemoteAddress();

		org.springframework.security.userdetails.User user = 
			(org.springframework.security.userdetails.User) auth.getPrincipal();

		String name = user.getUsername();

		model.addAttribute("user_ip", ip);
		model.addAttribute("user_name", name);
		model.addAttribute("user_authorities", StringUtils.join(user.getAuthorities(), ','));
		*/
		
		
		
		return "dashboard";
	}
}
