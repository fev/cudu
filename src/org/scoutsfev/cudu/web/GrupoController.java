package org.scoutsfev.cudu.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scoutsfev.cudu.domain.Grupo;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.services.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/grupo")
@SessionAttributes(types = Grupo.class)
public class GrupoController {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	/** Servicio de datos para el controlador */
	@Autowired
	protected GrupoService grupoService;
	
	/**
	 * Prepara el formulario para el alta o la edición del grupo del usuario.
	 * @param model Modelo asociado al formulario.
	 * @return Nombre de la vista que se renderizará.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(HttpServletRequest request, Model model) {
		String username = Usuario.obtenerLoginActual();
		Grupo grupo = grupoService.findByUser(username);
		if (grupo == null)
			return "redirect:/403";
		
		logger.info("setupForm: " + grupo.getId());

		model.addAttribute("grupo", grupo);
		return "grupo";
	}
	
	/**
	 * Procesa el formulario para guardar los datos
	 * @param grupo
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(@ModelAttribute("grupo") @Valid Grupo grupo, BindingResult result, SessionStatus status) {
		logger.info("processSubmit: " + grupo.getId());
		
		if (result.hasErrors()) {
			logger.info("Validation errors.");
			return "grupo";
		}

		grupo.setIdProvincia(0);
		grupo.setIdmunicipio(0);
		Grupo persistedEntity = grupoService.merge(grupo);
		status.setComplete();
		
		Usuario usuarioActual = Usuario.obtenerActual();
		usuarioActual.setGrupo(persistedEntity);
		
		return "redirect:/grupo?ok";
	}
}
