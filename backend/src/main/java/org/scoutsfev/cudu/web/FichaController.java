package org.scoutsfev.cudu.web;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.scoutsfev.cudu.domain.Ficha;
import org.scoutsfev.cudu.domain.RespuestaFichero;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.services.FichaService;
import org.scoutsfev.cudu.web.properties.FichaProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.zeroturnaround.zip.commons.FileUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@EnableConfigurationProperties
@RestController
public class FichaController {

    private static final Logger logger = LoggerFactory.getLogger(FichaController.class);
    private static final String NO_FILE = "";

    private final FichaService reportingService;

    @Autowired
    private FichaProperties _fichaProperties;

    @Autowired
    public FichaController(FichaService reportingService) {
        this.reportingService = reportingService;
    }

    @RequestMapping(value = "/ficha/{nombreArchivo}/descargar", method = RequestMethod.GET)
    public void DescargarFicha(@PathVariable String nombreArchivo, HttpServletResponse response) throws IOException, COSVisitorException {
        String path = Paths.get(_fichaProperties.getCarpetaFichas(), nombreArchivo).toString();
        DevolverArchivo(path, response);

    }

    @RequestMapping(value = "/ficha/{idFicha}/generar", method = RequestMethod.POST)
    @ResponseBody
    public RespuestaFichero GenerarReport(@PathVariable Integer idFicha,
                                          @RequestBody List<Integer> asociados,
                                          @AuthenticationPrincipal Usuario usuario) throws IOException, COSVisitorException {
        try {
            Path path = Paths.get(reportingService.GenerarFicha(asociados, null, null, idFicha, usuario));
            RespuestaFichero ficha = new RespuestaFichero();
            ficha.setNombre(path.getFileName().toString());
            return ficha;
        } catch (Exception ex) {
            logger.error("Error generando fichas");
            throw ex;
        }
    }

    @RequestMapping(value = "/ficha/{idFicha}/actividad/{actividadId}/generar", method = RequestMethod.POST)
    @ResponseBody
    public RespuestaFichero GenerarReport(@PathVariable Integer idFicha,
                                          @PathVariable Integer actividadId,
                                          @RequestBody List<Integer> asociados,
                                          @AuthenticationPrincipal Usuario usuario) throws IOException, COSVisitorException {
        try {
            Path path = Paths.get(reportingService.GenerarFicha(asociados, actividadId, null, idFicha, usuario));
            RespuestaFichero ficha = new RespuestaFichero();
            ficha.setNombre(path.getFileName().toString());
            return ficha;
        } catch (Exception ex) {
            logger.error("Error generando fichas: {0}", ex.getMessage());
            throw ex;
        }
    }

    @RequestMapping(value = "/fichas/entidad/{tipoEntidad}", method = RequestMethod.GET)
    public List<Ficha> ObtenerFichas(@PathVariable int tipoEntidad, @AuthenticationPrincipal Usuario usuario) {
        return reportingService.ObtenerFichas(usuario.getLenguaje());
    }

    private void DevolverArchivo(String pathArchivo, HttpServletResponse response) throws IOException {
        File archivo = new File(pathArchivo);

        response.setContentType(URLConnection.guessContentTypeFromName(pathArchivo));
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", archivo.getName()));

        FileUtils.copy(archivo, response.getOutputStream());
        response.flushBuffer();
    }
}
