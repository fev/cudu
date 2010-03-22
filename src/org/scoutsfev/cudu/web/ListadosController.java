package org.scoutsfev.cudu.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/listados")
public class ListadosController {

	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(Model model) {
		return "listados";
	}
}
