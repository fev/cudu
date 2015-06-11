package org.scoutsfev.cudu.web;


import org.scoutsfev.cudu.services.FichaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FichaController {

    private static final Logger logger = LoggerFactory.getLogger(FichaController.class);
    private static final List<String> NO_FILES = new ArrayList<>();

    private final FichaService reportingService;

    @Autowired
    public FichaController(FichaService reportingService) {
        this.reportingService = reportingService;
    }

    @RequestMapping(value = "/ficha/{idFicha}/generar", method = RequestMethod.POST)
    public List<String> GenerarReport(@PathVariable Integer idFicha, @RequestBody List<Integer> asociados) {

        try {

            return reportingService.GenerarFichaAsociados(asociados, null, idFicha, "es"); //TODO: obtener lenguaje, del user?

        } catch (Exception ex) {

            logger.error("Error generando fichas");
            return NO_FILES;
        }
    }
}
