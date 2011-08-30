/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.scoutsfev.cudu.web;
import org.scoutsfev.cudu.domain.InscripcionesCurso;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.Grupo;
import org.scoutsfev.cudu.domain.MisCursos;
import org.scoutsfev.cudu.domain.MonograficosEnCursos;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.services.AsociadoService;
import org.scoutsfev.cudu.services.CursoService;
import org.scoutsfev.cudu.services.InscripcionCursoService;
import org.scoutsfev.cudu.services.MiCursoService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;


@Controller
@RequestMapping("/curso")
@SessionAttributes(types = Asociado.class)
public class CursoController {
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Autowired
    protected InscripcionCursoService inscripcionService;
    
    @Autowired
    protected MiCursoService miCursoService;
    @Autowired
    protected CursoService cursoService;
    @Autowired
    protected AsociadoService asociadoService;
    
    @RequestMapping(value = "/{idAsociado}/{tipoCurso}", method = RequestMethod.GET)
    public String setupForm(@PathVariable("idAsociado") int idAsociado, @PathVariable("tipoCurso") String tipoCurso, Model model, HttpServletRequest request) {
        logger.info("setupForm /" + idAsociado);

        Asociado asociado = asociadoService.find(idAsociado);

        //comprobar que el usuario que entra es el mismo del cual se ven los datos
        Usuario usuarioActual = Usuario.obtenerActual();
        if(usuarioActual==null)
        	return "redirect:/403";
        if(asociado.getUsuario()==null|| usuarioActual.getUsername().compareTo(asociado.getUsuario())!=0)
            return "redirect:/403";

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

        //caso especial entre AJ Y FC. -> si me matriculo en AJ, FC tiene que tener en cuenta esto. No pasa 
        //lo mismo en el caso contrario
        String opuesto =null;
        if(tipoCurso.compareTo("FC")==0)
            opuesto = "AJ";

        List<MisCursos> misMonograficosCursoAJ=null;

        if(opuesto != null)
            misMonograficosCursoAJ = miCursoService.getMiCursoAJActual(idAsociado);

        List<List<MonograficosEnCursos>> monograficosPorBloque = new ArrayList<List<MonograficosEnCursos>>();
        List<String> bloques = new ArrayList<String>();

        List<MonograficosEnCursos> listMonograficoAux = new ArrayList<MonograficosEnCursos>();

        InscripcionesCurso inscripciones = new InscripcionesCurso();
        inscripciones.setIdAsociado(asociado.getId());
        
        
        
        if(monograficosEnCursos.size()>0)
        {
            String bloque = monograficosEnCursos.get(0).getBloque();
            inscripciones.setIdCurso(monograficosEnCursos.get(0).getMonograficosEnCursosPK().getIdcurso());
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
        model.addAttribute("monograficosCursoAJ",misMonograficosCursoAJ);
        model.addAttribute("monograficosEnCursos",monograficosPorBloque);
        model.addAttribute("inscripciones",inscripciones);

        
        return "curso";
    }



    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(
            @ModelAttribute("inscripciones") List<InscripcionesCurso> inscripciones,
            @RequestParam(value="insc", required=true) List li,
            BindingResult result, SessionStatus status) {
        int idAsociado = inscripciones.get(0).getIdAsociado();
        int idCurso = inscripciones.get(0).getIdCurso();

        for (int i = 0; i < inscripciones.size();i++)
        {
            //InscripcionCurso inscripcion = inscripciones.get(i);
            //logger.info("processSubmit: " + inscripcion.getInscripcionCursoPK().getIdasociado()+" "+inscripcion.getInscripcionCursoPK().getIdcurso());

            if (result.hasErrors()) {
                    logger.info("Validation errors.");
                    //	for(ObjectError error: result.getAllErrors()) logger.info(error.getCode());
                    return "asociado";
            }

            //comprobar que el usuario que entra es el mismo del cual se ven los datos
            Usuario usuarioActual = Usuario.obtenerActual();
            //Asociado asociado = inscripcion.getAsociado();
            if(usuarioActual==null)
                    return "redirect:/403";
            //if(asociado.getUsuario()==null|| usuarioActual.getUsername().compareTo(asociado.getUsuario())!=0)
             //    return "redirect:/403";
            
            //inscripcion.setAsociado(asociado);
           
            //InscripcionCurso persistedEntity = inscripcionService.merge(inscripcion);
            status.setComplete();
        }
            return "redirect:/curso/" + idAsociado + "/" + idCurso + "?ok";
    }

}
