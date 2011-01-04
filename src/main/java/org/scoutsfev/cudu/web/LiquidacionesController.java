package org.scoutsfev.cudu.web;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scoutsfev.cudu.domain.liquidaciones.Resumen;
import org.scoutsfev.cudu.domain.liquidaciones.VistaResumen;
import org.scoutsfev.cudu.services.LiquidacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

// TODO Comprobaciones de seguridad

@Controller
@RequestMapping(value = "/liquidaciones")
public class LiquidacionesController {

	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	protected LiquidacionesService service;

	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(Model model) {
		Collection<VistaResumen> liquidaciones = service.obtener(1);
		logger.warn(liquidaciones.size());
		model.addAttribute("liquidaciones", liquidaciones);
		return "liquidaciones";
	}
	
	@RequestMapping(value = "obtener", method = RequestMethod.GET)
	public @ResponseBody Resumen obtener(@RequestParam int ejercicio, @RequestParam String fecha, @RequestParam int asociacion) {
		// TODO Eliminar parámetro asociación, sacar de los roles
		
//		DetalleLiquidacion detalle = new DetalleLiquidacion();
//		detalle.setEjercicio(2011);
//		detalle.setFecha(new Date(2011, 11, 24));
//		grupos.put("AK", new DetalleGrupoI("AK", "Ain-Karen", 118, 21));
//		grupos.put("PAU", new DetalleGrupoI("PAU", "GS-Pau", 43, 76));
		
//		Resumen resumenParcial = service.obtener(ejercicio, fecha, asociacion);
//		ResumenCompleto resumen = new ResumenCompleto(resumenParcial, detalle);
		
		return service.obtener(ejercicio, fecha, asociacion);
	}
	
	@RequestMapping(value = "generar", method = RequestMethod.GET)
	public @ResponseBody String generar() {
		return "hi!";
	}
}
