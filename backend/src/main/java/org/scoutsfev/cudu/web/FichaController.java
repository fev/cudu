package org.scoutsfev.cudu.web;


import org.scoutsfev.cudu.services.FichaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FichaController {

    private final FichaService reportingService;

    @Autowired
    public FichaController(FichaService reportingService) {
        this.reportingService = reportingService;
    }

    @RequestMapping(value = "/ficha/{idFicha}/asociado/{idAsociado}/generar", method = RequestMethod.GET)
    public boolean GenerarReport(@PathVariable Integer idAsociado, @PathVariable Integer idFicha) {

        try {

            reportingService.GenerarFicha(idAsociado, idFicha, "es"); //TODO: obtener lenguaje, del user?

        } catch (Exception ex) {

            return false;

        }

        return true;
    }
}
