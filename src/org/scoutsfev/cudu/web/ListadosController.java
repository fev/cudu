package org.scoutsfev.cudu.web;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.Grupo;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.services.AsociadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/listados")
public class ListadosController {
	
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
	
	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(Model model) {
		return "listados";
	}

	@RequestMapping(value = "/asociados", method = RequestMethod.GET)
	public Result<Asociado> listaAsociados(
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
		
		Result<Asociado> result = new Result<Asociado>();
		result.setTotalRecords(storage.count(idGrupo, filtroTipo, filtroRama));
		result.setData(storage.findWhere(idGrupo, columnas, ordenadoPor, sentido, 
				inicio, resultadosPorPágina, filtroTipo, filtroRama));
		
		return result;
	}
	
	@RequestMapping(value = "/imprimir", method = RequestMethod.GET)
	public String imprimir(Model model, @RequestParam("c") String columnas,
			@RequestParam("s") String ordenadoPor,
			@RequestParam(value = "d", defaultValue = "asc") String sentido,
			@RequestParam(value = "f_tipo", required = false) String filtroTipo,
			@RequestParam(value = "f_rama", required = false) String filtroRama,
			HttpServletRequest request) {

		Result<Asociado> result = listaAsociados(columnas, ordenadoPor, sentido, 0, MAXRESULTS, filtroTipo, filtroRama, request);
		
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
}
