package org.scoutsfev.cudu.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.scoutsfev.cudu.domain.Parcial;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.services.SugerenciasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sugerencias")
public class SugerenciasController {
	
	@Autowired
	protected SugerenciasService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(HttpServletRequest request, Model model) {		
		model.addAttribute("username", Usuario.obtenerLoginActual());
		model.addAttribute("sugerencias", service.obtenerLista());
		return "sugerencias";
	}

	// TODO SuprHack here! Utilización de GET para insertar datos
	// Resultado de las horas previas al Zoom 2010
	// @RequestMapping(method = RequestMethod.POST)
	// public @ResponseBody String processSubmit(@RequestBody String sugerencia) throws RedisException {
	@RequestMapping(value = "/nueva", method = RequestMethod.GET)
	public @ResponseBody String processSubmit(@RequestParam("s") String sugerencia, HttpServletRequest request) {
		return Long.toString(service.crear(sugerencia, Usuario.obtenerLoginActual()));
	}
	
	@RequestMapping(value = "/{pk}", method = RequestMethod.POST)
	public @ResponseBody String processVote(@PathVariable String pk) {
		Parcial parcial = service.votar(pk, Usuario.obtenerLoginActual());
		return "{\"v\": \"" + parcial.getVotos() + "\", \"u\": \"" + StringUtils.join(parcial.getVotantes(), ", ") + "\"}";
	}
}
