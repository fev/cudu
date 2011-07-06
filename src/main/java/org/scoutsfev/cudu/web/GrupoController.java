package org.scoutsfev.cudu.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scoutsfev.cudu.domain.AsociadosTrimestre;
import org.scoutsfev.cudu.domain.Grupo;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.services.EstadisticaService;
import org.scoutsfev.cudu.services.GrupoService;
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
@RequestMapping("/grupo")
@SessionAttributes(types = Grupo.class)
public class GrupoController {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	/** Servicio de datos para el controlador */
	@Autowired
	protected GrupoService grupoService;
        
        @Autowired
        protected EstadisticaService  estadisticaService;
	
	/**
	 * Prepara el formulario para el alta o la edición del grupo del usuario.
	 * @param model Modelo asociado al formulario.
	 * @return Nombre de la vista que se renderizará.
	 */
	@RequestMapping(value = "/{grupoAsociado}", method = RequestMethod.GET)
	public String setupForm(@PathVariable("grupoAsociado") String idGrupo,HttpServletRequest request, Model model) {
		String username = Usuario.obtenerLoginActual();
		Grupo grupo = grupoService.find(idGrupo);
		if (grupo == null)
			return "redirect:/403";
		
		logger.info("setupForm: " + grupo.getId());               
                
		model.addAttribute("grupo", grupo);
                
                
                
  ///Referente a las estadísticas
        List totalAsociadosPorTrimestre  = estadisticaService.getAsociadoPorTrimestreYGrupo(idGrupo);
        int numGrupos = 0 ;
        
        String nombreGrupoMenosNumeroso = "Ninguno";
        int valorGrupoMenosNumeroso = 0;
        List<Integer> asociadosPorTrimestreYGrupo = new ArrayList<Integer>();
        Iterator i = totalAsociadosPorTrimestre.iterator();
        while (i.hasNext())
        {
            Object []o = (Object []) i.next();
            asociadosPorTrimestreYGrupo.add((Integer)o[2]);
        }
        
        AsociadosTrimestre estadisticaGrupoActual  = estadisticaService.getInfoEstadisticasAsociadosPorGrupo(idGrupo);
        
            
        List estadisticaGrupoTrimestres=  estadisticaService.getInfoEstadisticasTrimestresGrupo(idGrupo);
        List<Integer> castoresPorTrimestreYGrupo = new ArrayList<Integer>();
        List<Integer> lobatosPorTrimestreYGrupo = new ArrayList<Integer>();
        List<Integer> exploradoresPorTrimestreYGrupo = new ArrayList<Integer>();
        List<Integer> pionerosPorTrimestreYGrupo = new ArrayList<Integer>();
        List<Integer> companysPorTrimestreYGrupo = new ArrayList<Integer>();
        List<Integer> scoutersPorTrimestreYGrupo = new ArrayList<Integer>();
        List<Integer> comitePorTrimestreYGrupo = new ArrayList<Integer>();
        List<Integer> ejeX = new ArrayList<Integer>();
        //anyo,mes, castores,lobatos,exploradores,pioneros,companys,scouters,comite 
        i = estadisticaGrupoTrimestres.iterator();
        int cont = 1;
        while (i.hasNext())
        {
            Object []o = (Object []) i.next();

            
            Integer[] itAux = new Integer[3];
            itAux[0] = (Integer)o[0];
            
                //ejeX.add(itAux[2] = (Integer)o[1]);
            ejeX.add(cont);
            castoresPorTrimestreYGrupo.add((Integer)o[2]);
            itAux[2] = (Integer)o[3];
            lobatosPorTrimestreYGrupo.add(itAux[2]);
            itAux[2] = (Integer)o[4];
            exploradoresPorTrimestreYGrupo.add(itAux[2]);
            itAux[2] = (Integer)o[5];
            pionerosPorTrimestreYGrupo.add(itAux[2]);
            itAux[2] = (Integer)o[6];
            companysPorTrimestreYGrupo.add(itAux[2]);
            itAux[2] = (Integer)o[7];
            scoutersPorTrimestreYGrupo.add(itAux[2]);
            itAux[2] = (Integer)o[8];
            comitePorTrimestreYGrupo.add(itAux[2]);
            
            cont  = cont +1;
        }
                
        if(estadisticaGrupoActual!=null)
        {
            estadisticaGrupoActual.setAsociados();
            estadisticaGrupoActual.setGrupos(numGrupos);
        }

        model.addAttribute("datosEstadisticaActual",estadisticaGrupoActual);
        model.addAttribute("asociadosPorTrimestreYGrupo",asociadosPorTrimestreYGrupo);
        model.addAttribute("castoresPorTrimestreYGrupo",castoresPorTrimestreYGrupo);
        
        model.addAttribute("lobatosPorTrimestreYGrupo",lobatosPorTrimestreYGrupo);
        model.addAttribute("exploradoresPorTrimestreYGrupo",exploradoresPorTrimestreYGrupo);
        model.addAttribute("pionerosPorTrimestreYGrupo",pionerosPorTrimestreYGrupo);
        
        model.addAttribute("companysPorTrimestreYGrupo",companysPorTrimestreYGrupo);
        model.addAttribute("scoutersPorTrimestreYGrupo",scoutersPorTrimestreYGrupo);
        model.addAttribute("comitePorTrimestreYGrupo",comitePorTrimestreYGrupo);
        model.addAttribute("ejeX",ejeX);
        
                
                               
        return "grupo";
	}
        
        /**
	 * Prepara el formulario para el alta o la edición del grupo del usuario.
	 * @param model Modelo asociado al formulario.
	 * @return Nombre de la vista que se renderizará.
	 */
	@RequestMapping(value = "/nuevo", method = RequestMethod.GET)
	public String setupForm(HttpServletRequest request, Model model) {
		
		Grupo grupo = new Grupo();
		if (grupo == null)
			return "redirect:/403";
		
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
		
		if (result.hasErrors()) {
			logger.info("Validation errors.");
			return "grupo";
		}

		grupo.setIdProvincia(0);
		grupo.setIdmunicipio(0);
		Grupo persistedEntity = grupoService.merge(grupo);
		status.setComplete();
		
		//Usuario usuarioActual = Usuario.obtenerActual();
		//usuarioActual.setGrupo(persistedEntity);
		
		return "redirect:/grupo?ok";
	}
}
/*
 * 
 * 
 * 
 * 
 * 
        
        AsociadosTrimestre estadisticaGrupoActual  = estadisticaService.getInfoEstadisticasAsociadosPorGrupo("UP");
        List estadisticaGrupoTrimestres=  estadisticaService.getInfoEstadisticasTrimestresGrupo("UP");
        int tam = estadisticaGrupoTrimestres.size();
        Integer[][] castoresPorTrimestreYGrupo = new Integer[tam][3];
        Integer[][] lobatosPorTrimestreYGrupo = new Integer[tam][3];
        Integer[][] exploradoresPorTrimestreYGrupo = new Integer[tam][3];
        Integer[][] pionerosPorTrimestreYGrupo = new Integer[tam][3];
        Integer[][] companysPorTrimestreYGrupo = new Integer[tam][3];
        Integer[][] scoutersPorTrimestreYGrupo = new Integer[tam][3];
        Integer[][] comitePorTrimestreYGrupo = new Integer[tam][3];
        //anyo,mes, castores,lobatos,exploradores,pioneros,companys,scouters,comite 
        
        int cont = 0;
        i = estadisticaGrupoTrimestres.iterator();
        while (i.hasNext())
        {
            Object []o = (Object []) i.next();

            
            Integer[] itAux = new Integer[3];
            itAux[0] = (Integer)o[0];
            itAux[1] = (Integer)o[1];
            itAux[2] = (Integer)o[2];
            castoresPorTrimestreYGrupo[cont] = itAux;
            
            itAux[2] = (Integer)o[3];
            lobatosPorTrimestreYGrupo[cont] = itAux;
            itAux[2] = (Integer)o[4];
            exploradoresPorTrimestreYGrupo[cont] = itAux;
            itAux[2] = (Integer)o[5];
            pionerosPorTrimestreYGrupo[cont] = itAux;
            itAux[2] = (Integer)o[6];
            companysPorTrimestreYGrupo[cont] = itAux;
            itAux[2] = (Integer)o[7];
            scoutersPorTrimestreYGrupo[cont] = itAux;
            itAux[2] = (Integer)o[8];
            comitePorTrimestreYGrupo[cont] = itAux;
            cont++;
        }
                
      
 */