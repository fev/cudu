/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scoutsfev.cudu.web;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.scoutsfev.cudu.domain.Grupo;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.services.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/listados_grupos")
public class ListadosGruposController {
    
    private static final int MAXRESULTS = 200;
	
	@Autowired
	protected GrupoService storage;
	
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
	
	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(Model model) {
		return "listados_grupos";
	}

	@RequestMapping(value = "/grupos", method = RequestMethod.GET)
	public Result<Grupo> listaGrupos(
			@RequestParam("c") String columnas,
			@RequestParam("s") String ordenadoPor,
			@RequestParam(value = "d", defaultValue = "asc") String sentido,
			@RequestParam(value = "i", defaultValue = "0") int inicio,
			@RequestParam(value = "r", defaultValue = "10") int resultadosPorPágina,
			@RequestParam(value = "f_asociacion", required = false) String filtroAsociacion,
			HttpServletRequest request) {
				
            if(filtroAsociacion==null)
                filtroAsociacion="";

            int asociacion = -1; // No filtrar
            Collection<GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            for (GrantedAuthority authority : authorities) {
                    if (authority.getAuthority().equals("SdA")) { asociacion = 0; break; }
                    if (authority.getAuthority().equals("SdC")) { asociacion = 1; break; }
                    if (authority.getAuthority().equals("MEV")) { asociacion = 2; break; }
            }
            /*
            if(filtroAsociacion.equals("SdA")) { asociacion = 0; }
            if (filtroAsociacion.equals("SdC")) { asociacion = 1;}
            if (filtroAsociacion.equals("MEV")) { asociacion = 2;}
            */
            
            Result<Grupo> result = new Result<Grupo>();
            result.setTotalRecords(storage.count());
            
            
            String asociacionTest = "";
            String[] asociaciones = filtroAsociacion.split(",");
            
            for(int i = 0; i < asociaciones.length;i++)
            {
                if(asociaciones[i].equals("SdA")) { asociaciones[i] = "0"; }
                if (asociaciones[i].equals("SdC")) { asociaciones[i] = "1";}
                if (asociaciones[i].equals("MEV")) { asociaciones[i] = "2";}

            }

            //para poner que solo vea lo de athorities (se supone que no habrá filtro para llegar aqui... tendremos que debatir
            if(asociaciones.length<1)
            {
                asociaciones = new String[1];
                asociaciones[0]=asociacion+"";
            }
            
            result.setData(storage.findWhere(columnas, ordenadoPor, sentido, 
                            inicio, resultadosPorPágina, false, asociaciones));
            /*
            result.setData(storage.findWhere(columnas, ordenadoPor, sentido, 
                            inicio, resultadosPorPágina, false, asociacion));
             */
            
            

            return result;
	}
	
	@RequestMapping(value = "/imprimir_grupos", method = RequestMethod.GET)
	public String imprimir(Model model, @RequestParam("c") String columnas,
			@RequestParam("s") String ordenadoPor,
			@RequestParam(value = "d", defaultValue = "asc") String sentido,
			@RequestParam(value = "f_asociacion", required = false) String filtroAsociacion,
			HttpServletRequest request) {

		Result<Grupo> result = listaGrupos(columnas, ordenadoPor, sentido, 0, MAXRESULTS, filtroAsociacion, request);
		
		Usuario usuarioActual = Usuario.obtenerActual();
		String userStamp = usuarioActual.getNombreCompleto();
		model.addAttribute("userStamp", userStamp);
		
		String[] lstColumnas = columnas.split(",");
		model.addAttribute("columnas", lstColumnas);
		model.addAttribute("numeroColumnas", lstColumnas.length);

		model.addAttribute("grupos", result.data);
		model.addAttribute("total", result.data.size());

		Date timestamp = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:MM:SS");
		model.addAttribute("timestamp", dateFormat.format(timestamp));		

		return "imprimir_grupos";
	}


        @RequestMapping(value = "/pdf_grupos", method = RequestMethod.GET)
	public String pdf(Model model, @RequestParam("c") String columnas,
			@RequestParam("s") String ordenadoPor,
			@RequestParam(value = "d", defaultValue = "asc") String sentido,
			@RequestParam(value = "f_asociacion", required = false) String filtroAsociacion,
			HttpServletRequest request) {

		Result<Grupo> result = listaGrupos(columnas, ordenadoPor, sentido, 0, MAXRESULTS, filtroAsociacion, request);

		Usuario usuarioActual = Usuario.obtenerActual();
		String userStamp = usuarioActual.getNombreCompleto();
		model.addAttribute("userStamp", userStamp);

		String[] lstColumnas = columnas.split(",");
		model.addAttribute("columnas", lstColumnas);
		model.addAttribute("numeroColumnas", lstColumnas.length);

		model.addAttribute("grupos", result.data);
		model.addAttribute("total", result.data.size());

		Date timestamp = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:MM:SS");
		model.addAttribute("timestamp", dateFormat.format(timestamp));

		return "pdf_grupos";
	}
    
}
