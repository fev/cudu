package org.scoutsfev.cudu.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.Grupo;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.services.AsociadoService;
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
@RequestMapping("/asociado")
@SessionAttributes(types = Asociado.class)
public class AsociadoController {

	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	protected AsociadoService storage;

	@RequestMapping(value = "/{idAsociado}", method = RequestMethod.GET)
	public String setupForm(@PathVariable("idAsociado") int idAsociado,  Model model) {
		logger.info("setupForm /asociado/" + idAsociado);

		Asociado asociado = storage.find(idAsociado);
		if (asociado == null)
			return "redirect:/404";
		
		model.addAttribute("asociado", asociado);
		return "asociado";
	}
	
	@RequestMapping(value = "/nuevo/{tipo}", method = RequestMethod.GET)
	public String setupForm(@PathVariable String tipo, Model model, HttpServletRequest request) throws Exception {
		logger.info("setupForm /nuevo/" + tipo);
				
		Asociado asociado = new Asociado();
		if (tipo.compareTo("joven") == 0)
			asociado.setTipo('J');
		else if (tipo.compareTo("kraal") == 0)
			asociado.setTipo('K');
		else if (tipo.compareTo("comite") == 0)
			asociado.setTipo('C');
		else
			return "redirect:/404";

		HttpSession session = request.getSession();
		Usuario usuarioActual = (Usuario)session.getAttribute("usuarioActual");		
		Grupo grupo = usuarioActual.getGrupo();
		asociado.setIdGrupo(grupo.getId());
		
		// DBG
		asociado.setProvincia("(desconocida)");
		asociado.setMunicipio("(desconocida)");

		model.addAttribute("asociado", asociado);
		return "asociado";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(@ModelAttribute("asociado") @Valid Asociado asociado, BindingResult result, SessionStatus status) {
		logger.info("processSubmit: " + asociado.getId());
		
		if (result.hasErrors()) {
			logger.info("Validation errors.");
			return "asociado";
		}
		
		Asociado persistedEntity = storage.merge(asociado);
		status.setComplete();
		return "redirect:/asociado/" + persistedEntity.getId() + "?ok";
	}
}
