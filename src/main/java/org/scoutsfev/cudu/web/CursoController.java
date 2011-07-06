/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.scoutsfev.cudu.web;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.Curso;
import org.scoutsfev.cudu.domain.Grupo;
import org.scoutsfev.cudu.domain.Monografico;
import org.scoutsfev.cudu.domain.MonograficosEnCursos;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.services.AsociadoService;
import org.scoutsfev.cudu.services.CursoService;
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
@RequestMapping("/curso")
@SessionAttributes(types = Asociado.class)
public class CursoController {
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Autowired
    protected CursoService cursoService;
    @Autowired
    protected AsociadoService asociadoService;
    
    @RequestMapping(value = "/{idAsociado}/{tipoCurso}", method = RequestMethod.GET)
    public String setupForm(@PathVariable("idAsociado") int idAsociado, @PathVariable("tipoCurso") String tipoCurso, Model model, HttpServletRequest request) {
        logger.info("setupForm /" + idAsociado);

        Asociado asociado = asociadoService.find(idAsociado);

        if (asociado == null)
            return "redirect:/404";

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

		model.addAttribute("asociado", asociado);
                model.addAttribute("tipoCurso",tipoCurso);
                
                
                List<MonograficosEnCursos> monograficosEnCursos = cursoService.getMonograficosEnCursoActual(tipoCurso);
                List<List<MonograficosEnCursos>> monograficosPorBloque = new ArrayList<List<MonograficosEnCursos>>();
                List<String> bloques = new ArrayList<String>();
                
                List<MonograficosEnCursos> listMonograficoAux = new ArrayList<MonograficosEnCursos>();
                
                if(monograficosEnCursos.size()>0)
                {
                    String bloque = monograficosEnCursos.get(0).getBloque();
                    bloques.add(bloque);
                    
                    for(int i = 0;i< monograficosEnCursos.size();i++)
                    {
                        if(bloque.compareTo(monograficosEnCursos.get(i).getBloque())!=0)
                        {
                            //actualizo e inserto el nuevo bloque
                            bloque = monograficosEnCursos.get(i).getBloque();
                            bloques.add(bloque);
                            
                            //guardo la lista de un bloque
                            monograficosPorBloque.add(listMonograficoAux);
                            
                            //comienzo a crear la nueva lista.
                            listMonograficoAux = new ArrayList<MonograficosEnCursos>();    
                        }                            
                        listMonograficoAux.add(monograficosEnCursos.get(i));
                    }
                    
                    //actualizo e inserto el nuevo bloque
                    bloque = monograficosEnCursos.get(monograficosEnCursos.size()-1).getBloque();
                    bloques.add(bloque);

                    //guardo la lista de un bloque
                    monograficosPorBloque.add(listMonograficoAux);
                            
                }
                /*
                List<String> bloques = cursoService.getBloquesCurso(cAux);
                
                
                for(int i = 0 ; i < bloques.size(); i++)
                {
                    monograficosPorBloque.add(cursoService.getMonograficosBloque(cAux, bloques.get(i)));
                }*/
                model.addAttribute("monograficosEnCursos",monograficosPorBloque);
       /*
                for(int i = 0 ; i < monograficosPorBloque.size(); i++)
                {
                    List<Monografico> monograficsDeBloque = monograficosPorBloque.get(i);
                    System.out.println("----Bloque:  "+monograficsDeBloque.get(0).getBloque());
                    for(int j = 0 ; j < monograficsDeBloque.size(); j++)
                    {
                        Monografico monografic = monograficsDeBloque.get(j);
                        System.out.println("            monografic::  "+monografic.getNombre());
                    }
                }       
        * */
                //ahora toca manejar el tipo de curso
		return "curso";

                
        
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
			return "curso";
                    }
                }



		Asociado persistedEntity = asociadoService.merge(asociado);
		status.setComplete();

		return "redirect:/asociado/" + persistedEntity.getId() + "?ok";
	}

}
