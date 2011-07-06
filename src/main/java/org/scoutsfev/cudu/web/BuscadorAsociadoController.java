/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scoutsfev.cudu.web;

import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.Grupo;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.services.AsociadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/buscarAsociado")
@SessionAttributes(types = Asociado.class)
public class BuscadorAsociadoController  {
    
	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	protected AsociadoService asociadoService;

        
        
        private static final int MAXRESULTS = 200;
	
	@Autowired
	protected AsociadoService storage;
	
	public class Result<T> implements Serializable {
		private static final long serialVersionUID = 1765112359692608857L;
		
		private long totalRecords;
		private Collection<T> data;
		
		public void setData(Collection<T> data) {
			this.data = data;
		}

		public Collection<T> getData() {
			return data;
		}

		public void setTotalRecords(long totalRecords) {
			this.totalRecords = totalRecords;
		}

		public long getTotalRecords() {
			return totalRecords;
		}
	}

	@RequestMapping(value = "/buscare", method = RequestMethod.GET)
	public Result<Asociado> setupform(
			@RequestParam("c") String columnas,
			@RequestParam("s") String ordenadoPor,
			@RequestParam(value = "d", defaultValue = "asc") String sentido,
			@RequestParam(value = "i", defaultValue = "0") int inicio,
			@RequestParam(value = "r", defaultValue = "10") int resultadosPorPágina,
			@RequestParam(value = "f_tipo", required = false) String filtroTipo,
			@RequestParam(value = "f_rama", required = false) String filtroRama,
			HttpServletRequest request) {
		
		Usuario usuarioActual = Usuario.obtenerActual();

		Grupo grupo = usuarioActual.getGrupo();
		String idGrupo = (grupo == null ? null : grupo.getId());
		
		int asociacion = -1; // No filtrar
		Collection<GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		for (GrantedAuthority authority : authorities) {
			if (authority.getAuthority().equals("SdA")) { asociacion = 0; break; }
			if (authority.getAuthority().equals("SdC")) { asociacion = 1; break; }
			if (authority.getAuthority().equals("MEV")) { asociacion = 2; break; }
		}
		
		Result<Asociado> result = new Result<Asociado>();
		result.setTotalRecords(storage.count(idGrupo, filtroTipo, filtroRama, false, asociacion));
		result.setData(storage.findWhere(idGrupo, columnas, ordenadoPor, sentido, 
				inicio, resultadosPorPágina, filtroTipo, filtroRama, false, asociacion));

		return result;
	}
	
	@RequestMapping(value = "/imprimir", method = RequestMethod.GET)
	public String imprimir(Model model, @RequestParam("c") String columnas,
			@RequestParam("s") String ordenadoPor,
			@RequestParam(value = "d", defaultValue = "asc") String sentido,
			@RequestParam(value = "f_tipo", required = false) String filtroTipo,
			@RequestParam(value = "f_rama", required = false) String filtroRama,
			HttpServletRequest request) {

		Result<Asociado> result = setupform(columnas, ordenadoPor, sentido, 0, MAXRESULTS, filtroTipo, filtroRama, request);
		
		Usuario usuarioActual = Usuario.obtenerActual();
		String userStamp = usuarioActual.getNombreCompleto();
		model.addAttribute("userStamp", userStamp);
		
		String[] lstColumnas = columnas.split(",");
		model.addAttribute("columnas", lstColumnas);
		model.addAttribute("numeroColumnas", lstColumnas.length);

		model.addAttribute("asociados", result.data);
		model.addAttribute("total", result.data.size());

		Date timestamp = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:MM:SS");
		model.addAttribute("timestamp", dateFormat.format(timestamp));		

		return "imprimir";
	}


        
        @RequestMapping(value = "/buscar", method = RequestMethod.GET)
	public String setupForm(Model model) throws Exception {
		logger.info("setupForm /buscar");
                model.addAttribute("asociado", new Asociado() );
		return "buscadorAsociado";
	}
	@RequestMapping(value = "/resultadoBusqueda", method = RequestMethod.GET)
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
		return "buscadorAsociado";
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
    
}
