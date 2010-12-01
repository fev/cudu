package org.scoutsfev.cudu.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.Grupo;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.services.AsociadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
	protected AsociadoService service;

	@RequestMapping(value = "/{idAsociado}", method = RequestMethod.GET)
	public String setupForm(@PathVariable("idAsociado") int idAsociado, Model model, HttpServletRequest request) {
		logger.info("setupForm /asociado/" + idAsociado);

		Asociado asociado = service.find(idAsociado);
		if (asociado == null)
			return "redirect:/404";
		
		// Comprobaciones de Seguridad
		// TODO Mover comprobación a otro sitio
		// Inicialmente, si puede acceder al asociado puede modificarlo
		boolean esAdmin = false;
		int asociacion = -1;
		for (GrantedAuthority authority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
			String role = authority.getAuthority();
			if (role.equals("ROLE_ADMIN")) { esAdmin = true; }
			if (role.equals("SdA")) { asociacion = 0; } 
			if (role.equals("SdC")) { asociacion = 1; }
			if (role.equals("MEV")) { asociacion = 2; }
		}
		
		if (!esAdmin) {
			Grupo grupoAsociado = asociado.getGrupo();
			if (asociacion == -1) {
				Usuario usuarioActual = Usuario.obtenerActual();
				Grupo grupoUsuario = usuarioActual.getGrupo();
				if ((grupoAsociado == null) || (grupoUsuario == null) || (!grupoAsociado.getId().equals(grupoUsuario.getId()))) {
					return "redirect:/403";
				}
			} else if (asociacion != grupoAsociado.getAsociacion()) {
				return "redirect:/403";
			}
		}
		// END Comprobaciones de seguridad
		
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
		
		Usuario usuarioActual = Usuario.obtenerActual();
		Grupo grupo = usuarioActual.getGrupo();
		asociado.setIdGrupo(grupo.getId());
		
		// Agilizar proceso de edición
		// Provincia y municipio son los del grupo
		asociado.setProvincia(grupo.getProvincia());
		asociado.setMunicipio(grupo.getMunicipio());
		
		// Hasta que se complete la migración de los datos y se enlacen
		// correctamente los municipios y provincias, los campos siguen
		// siendo meramente textuales (0 es "no comprobada").
		asociado.setIdProvincia(0);
		asociado.setIdMunicipio(0);

		model.addAttribute("asociado", asociado);
		return "asociado";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(@ModelAttribute("asociado") @Valid Asociado asociado, BindingResult result, SessionStatus status) {
		logger.info("processSubmit: " + asociado.getId());
		
		if (result.hasErrors()) {
			logger.info("Validation errors.");
			//	for(ObjectError error: result.getAllErrors()) logger.info(error.getCode());
			return "asociado";
		}

		Asociado persistedEntity = service.merge(asociado);
		status.setComplete();
		
		return "redirect:/asociado/" + persistedEntity.getId() + "?ok";
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public String eliminarAsociado(@PathVariable int idAsociado) {
		logger.info("eliminarAsociado: " + idAsociado);
		service.delete(idAsociado);		
		return "redirect:/listados";
	}
}
