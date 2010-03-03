package org.scoutsfev.cudu.web;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.services.AsociadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/asociado.mvc")
@SessionAttributes("asociado")
public class AsociadoController {

	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	protected AsociadoService storage;
	
	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(@RequestParam(value = "id", required = false) String idAsociado, @RequestParam(value = "t") char tipo,  Model model) {
		logger.info("setupForm (" + tipo + "): " + idAsociado);
		model.addAttribute("idAsociado", idAsociado);
		
		if (StringUtils.isBlank(idAsociado) || !StringUtils.isNumeric(idAsociado)) {
			Asociado a = new Asociado();
			a.setId(0);
			// char t = Character.toUpperCase(tipo);
//			if ((t != 'J') || (t != 'K') || (t != 'C'))	
				
			a.setTipo(tipo);
			model.addAttribute("asociado", a);
		}
		else {
			Integer parsedId = Integer.parseInt(idAsociado);
			Asociado a = storage.find(parsedId);
			model.addAttribute("asociado", a);
		}

		return "asociado";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(@ModelAttribute("asociado") Asociado asociado, BindingResult result, SessionStatus status) {
		logger.info("processSubmit: " + asociado.getId());		
		storage.merge(asociado);
		return "asociado";
	}
}
