package org.scoutsfev.cudu.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.Grupo;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.domain.validators.EdadValidator;
import org.scoutsfev.cudu.services.AsociadoService;
import org.scoutsfev.cudu.services.GrupoService;
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
	protected AsociadoService asociadoService;
        
        @Autowired
        protected GrupoService  grupoService;
    

	@RequestMapping(value = "/{idAsociado}", method = RequestMethod.GET)
	public String setupForm(@PathVariable("idAsociado") int idAsociado, Model model, HttpServletRequest request) {
		logger.info("setupForm /asociado/" + idAsociado);

		Asociado asociado = asociadoService.find(idAsociado);
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
                
                
        
                List resultadoRecorrido = asociadoService.getRecorridoAsociado(5);
                
                List<String> recorrido = new ArrayList<String>();
                
                for(int i = 0; i < resultadoRecorrido.size();i++)
                {
                    recorrido.add(((Integer)((Object[])resultadoRecorrido.get(i))[0]).toString());
                    recorrido.add(((Character)((Object[])resultadoRecorrido.get(i))[1]).toString());
                    recorrido.add((String)((Object[])resultadoRecorrido.get(i))[2]);
                    recorrido.add((String)((Object[])resultadoRecorrido.get(i))[3]);
                    //recorrido.add(((Timestamp)((Object[])resultadoRecorrido.get(i))[4]).toString());
                    
                }
		// END Comprobaciones de seguridad
                model.addAttribute("recorrido", resultadoRecorrido);
		model.addAttribute("asociado", asociado);
                int edad = EdadValidator.getAnyos(asociado);
                model.addAttribute("edadAsociado", edad);
                
                
                
                
                
        String columnas = "id,nombre";
        String campoOrden="id";
        String orden = "desc";
        int numTotal = (int)grupoService.count();
        Collection<Grupo> grupos = grupoService.findWhere(columnas,campoOrden,orden, 0, numTotal, false, -1);
        
        
        model.addAttribute("grupos", grupos);
        
        
		return "asociado";
	}

	@RequestMapping(value = "/nuevo/{tipo}/{nombreGrupo}", method = RequestMethod.GET)
	public String setupForm(@PathVariable String tipo, @PathVariable String nombreGrupo, Model model, HttpServletRequest request) throws Exception {
		logger.info("setupForm /nuevo/" + tipo+"/"+nombreGrupo);

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
		Grupo grupo = grupoService.find(nombreGrupo);
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
		asociado.setIdGrupo(grupo.getId());
                asociado.setGrupo(grupo);
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

                //if(asociado.getRamas())
                //comprobación de las ramas
                Calendar today = Calendar.getInstance();
                Calendar bornDay = Calendar.getInstance();
                bornDay.setTime(asociado.getFechanacimiento());

                int auxToday = today.get(Calendar.YEAR)  *1000  + today.get(Calendar.MONTH)   *100 + today.get(Calendar.DAY_OF_MONTH);
                int auxBorn  = bornDay.get(Calendar.YEAR)*1000  + bornDay.get(Calendar.MONTH) *100 +bornDay.get(Calendar.DAY_OF_MONTH);
                int anyosAsociado = (auxToday-auxBorn)/1000;
                System.out.println(anyosAsociado);

                if(asociado.getTipo()=='J')
                {

                } else if(asociado.getTipo()=='K')
                {
                    if (anyosAsociado < 18)
                    {
                        logger.info("Validation errors.");
			//	for(ObjectError error: result.getAllErrors()) logger.info(error.getCode());
			return "asociado";
                    }
                }



		Asociado persistedEntity = asociadoService.merge(asociado);
		status.setComplete();

		return "redirect:/asociado/" + persistedEntity.getId() + "?ok";
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public String eliminarAsociado(@PathVariable int idAsociado) {
		logger.info("eliminarAsociado: " + idAsociado);
		asociadoService.delete(idAsociado);
		return "redirect:/listados";
	}
        
        
	@RequestMapping(method = RequestMethod.DELETE)
	public String eliminarAsociadoDefinitivamente(@PathVariable int idAsociado) {
		logger.info("eliminarAsociado: " + idAsociado);
		asociadoService.deleteFromDB(idAsociado);
		return "redirect:/listados";
	}
        
        
}
