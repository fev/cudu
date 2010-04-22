package org.scoutsfev.cudu.web;

import java.io.Serializable;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.scoutsfev.cudu.domain.Asociado;
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
			HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		Usuario usuarioActual = (Usuario)session.getAttribute("usuarioActual");		
		String idGrupo = usuarioActual.getGrupo().getId();
		
		Result<Asociado> result = new Result<Asociado>();
		result.setTotalRecords(storage.count(idGrupo));
		result.setData(storage.findWhere(idGrupo, columnas, ordenadoPor, sentido, inicio, resultadosPorPágina));
		
		return result;
	}
}
