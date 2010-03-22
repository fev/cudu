package org.scoutsfev.cudu.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scoutsfev.cudu.domain.Grupo;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.domain.Grupo.UiStates;
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
@SessionAttributes("grupo")
public class GrupoController {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	/** Servicio de datos para el controlador */
	@Autowired
	protected GrupoService grupoService;
	
	/**
	 * Prepara el formulario para el alta o la edición de un nuevo grupo.
	 * @param idGrupo Identificador del grupo, si no se especifica se asume se intenta crear uno nuevo.
	 * @param model Modelo asociado al formulario.
	 * @return Nombre de la vista que se renderizará.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(HttpServletRequest request, Model model) {
		/* Se asume que el usuario ha pasado antes por el dashboard y que por
		 * tanto en la sesión existe el usuario actual con el grupo al que
		 * pertenece cargado en memoria. Considerar cargar una copia fresca de
		 * la BBDD: Grupo grupo = grupoService.find(idGrupo);
		 * 
		 * El proceso de carga que se hace en el dashboard debería ser movido a
		 * una acción posterior a la de login, para permitir la entrada desde
		 * cualquier URL posible.
		 */
		HttpSession session = request.getSession();
		Usuario usuarioActual = (Usuario)session.getAttribute("usuarioActual");		
		Grupo grupo = usuarioActual.getGrupo();
		grupo.setUiState(Grupo.UiStates.Init);
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
		
		if (!result.hasErrors()) {
			grupoService.merge(grupo);
			grupo.setUiState(UiStates.Saved);
		} else {
			grupo.setUiState(UiStates.Error);
		}

		/* setComplete elimina los datos establecidos por SessionAttributes
		 * considerar si es posible eliminar esos datos al presionar el botón volver
		 * o al redirigir a otra página 
		 */
		// status.setComplete();
		
		return "grupo";
	}
}
