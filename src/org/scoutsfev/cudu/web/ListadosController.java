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
		
		private int totalRecords = 4;
		private Collection<T> data;
		
		public void setData(Collection<T> data) {
			this.data = data;
		}
		public Collection<T> getData() {
			return data;
		}
		public void setTotalRecords(int totalRecords) {
			this.totalRecords = totalRecords;
		}
		public int getTotalRecords() {
			return totalRecords;
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(Model model) {
		return "listados";
	}

	@RequestMapping(value = "/asociados", method = RequestMethod.GET)
	public Result<Asociado> listaAsociados(@RequestParam String columnas, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Usuario usuarioActual = (Usuario)session.getAttribute("usuarioActual");		
		String idGrupo = usuarioActual.getGrupo().getId();
		Result<Asociado> result = new Result<Asociado>();
		result.setData(storage.findWhere(idGrupo, columnas));
		return result;
	}
}
