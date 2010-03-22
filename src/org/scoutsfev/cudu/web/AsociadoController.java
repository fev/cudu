package org.scoutsfev.cudu.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.services.AsociadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
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
	
	@Autowired
    private Validator validator;
	
	/** 
	 * Inicializa el enlace de datos entre el modelo y la vista.
	 * Entre otras cosas, aquí se usa para establecer el formato de fecha en el textbox de "aniversario",
	 * o inyectar la dependencia con el validador de Asociados (ver anotaciones sobre la entidad).
	 * @param dataBinder objeto que establece el databinding en el formulario.
	 */
	@InitBinder
    public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
		binder.setValidator(validator);
    }

	@RequestMapping(value="/{idAsociado}", method = RequestMethod.GET)
	public String setupForm(@PathVariable("idAsociado") int idAsociado,  Model model) {
		logger.info("setupForm /asociado/" + idAsociado);

		Asociado asociado = storage.find(idAsociado);
		if (asociado == null)
			return "redirect:/404";

		model.addAttribute("asociado", asociado);
		return "asociado";
	}
	
	@RequestMapping(value="/nuevo/{tipo}", method = RequestMethod.GET)
	public String setupForm(@PathVariable String tipo, Model model) throws Exception {
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

		model.addAttribute("asociado", asociado);
		return "asociado";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(@ModelAttribute("asociado") @Valid Asociado asociado, BindingResult result, SessionStatus status) {
		logger.info("processSubmit: " + asociado.getId());
		
		// DBG
		asociado.setSexo("F");
		
		if (result.hasErrors()) {
			logger.info("Validation errors.");
//			List<ObjectError> errors = result.getAllErrors();
//			for (ObjectError error : errors) {
//				logger.info(error.getDefaultMessage());
//				logger.info(error.getCode());
//			}
		}
		else {
			logger.info("Entity is valid.");
			storage.merge(asociado);
		}
		
		return "asociado";
	}
}
