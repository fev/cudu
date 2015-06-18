package org.scoutsfev.cudu.web;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.scoutsfev.cudu.domain.Ficha;
import org.scoutsfev.cudu.services.FichaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zeroturnaround.zip.commons.FileUtils;
import org.zeroturnaround.zip.commons.FilenameUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.List;

@RestController
public class FichaController {

    private static final Logger logger = LoggerFactory.getLogger(FichaController.class);
    private static final String NO_FILE = "";

    private final FichaService reportingService;

    @Autowired
    public FichaController(FichaService reportingService) {
        this.reportingService = reportingService;
    }

    @RequestMapping(value = "/ficha/{idFicha}/generar", method = RequestMethod.POST)
    public void GenerarReport(@PathVariable Integer idFicha, @RequestBody List<Integer> asociados,
                              HttpServletResponse response) throws IOException, COSVisitorException {
        try {

            String pathArchivo = reportingService.GenerarFicha(asociados, null, null, idFicha, "es"); //TODO: obtener lenguaje, del user?
            DevolverArchivo(pathArchivo, response);

        } catch (Exception ex) {

            logger.error("Error generando fichas");
            throw ex;
        }
    }

    @RequestMapping(value = "/ficha/{idFicha}/actividad/{actividadId}/generar", method = RequestMethod.POST)
    public void GenerarReport(@PathVariable Integer idFicha, @PathVariable Integer actividadId, @RequestBody List<Integer> asociados,
                              HttpServletResponse response) throws IOException, COSVisitorException {
        try {

            String pathArchivo = reportingService.GenerarFicha(asociados, actividadId, null, idFicha, "es"); //TODO: obtener lenguaje, del user
            DevolverArchivo(pathArchivo, response);

        } catch (Exception ex) {

            logger.error("Error generando fichas: {0}", ex.getMessage());
            throw ex;
        }
    }

    @RequestMapping(value = "/fichas/lenguaje/{lenguajeId}/tipo/{tipoFicha}", method = RequestMethod.GET)
    public List<Ficha> ObtenerFichas(@PathVariable String lenguajeId, @PathVariable int tipoFicha) {
        return reportingService.ObtenerFichas(lenguajeId, tipoFicha);
    }

    private void DevolverArchivo(String pathArchivo, HttpServletResponse response) throws IOException {

        File archivo = new File(pathArchivo);

        response.setContentType(URLConnection.guessContentTypeFromName(pathArchivo));
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", archivo.getName()));

        FileUtils.copy(archivo, response.getOutputStream());
        response.flushBuffer();
    }
}
