package org.scoutsfev.cudu.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.services.AsociadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
	
	/** 
	 * Inicializa el enlace de datos entre el modelo y la vista.
	 * Entre otras cosas, aquí se usa para establecer el formato de fecha en el textbox de "aniversario".
	 * @param dataBinder objeto que establece el databinding en el formulario.
	 */
	@InitBinder
    public void initBinder(WebDataBinder dataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dataBinder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
    }

	@RequestMapping(value="/{idAsociado}", method = RequestMethod.GET)
	public String setupForm(@PathVariable("idAsociado") int idAsociado,  Model model) {
		logger.info("setupForm /asociado/" + idAsociado);

		Asociado asociado = storage.find(idAsociado);
		if (asociado == null)
			return "redirect:/404";
		
		model.addAttribute("idAsociado", idAsociado);
		model.addAttribute("asociado", asociado);
		return "asociado";
	}
	
	@RequestMapping(value="/nuevo/{tipo}", method = RequestMethod.GET)
	public String setupForm(@PathVariable String tipo, Model model) {
		logger.info("setupForm /nuevo/" + tipo);
		Asociado asociado = new Asociado();
		model.addAttribute("asociado", asociado);
		return "asociado";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(@ModelAttribute("asociado") Asociado asociado, BindingResult result, SessionStatus status) {
		logger.info("processSubmit: " + asociado.getId());
		storage.merge(asociado);
		return "asociado";
	}
}
