package org.scoutsfev.cudu.services;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.scoutsfev.cudu.domain.*;
import org.scoutsfev.cudu.pdfbuilder.CreadorPdf;
import org.scoutsfev.cudu.storage.ActividadRepository;
import org.scoutsfev.cudu.storage.AsociadoRepository;
import org.scoutsfev.cudu.storage.FichaRepository;
import org.scoutsfev.cudu.storage.RegistroImpresionRepository;
import org.scoutsfev.cudu.web.FichaProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

@Service
@EnableConfigurationProperties
public class FichaServiceImpl implements FichaService {

    private String PDF = ".pdf";
    private String ZIP = ".zip";

    @Autowired
    private FichaProperties _fichaProperties;

    private AsociadoRepository _asociadoRepository;
    private FichaRepository _fichaRepository;
    private ActividadRepository _actividadRepository;
    private RegistroImpresionRepository _registroImpresionRepository;

    @Autowired
    public FichaServiceImpl(AsociadoRepository asociadoRepository,
                            FichaRepository fichaRepository,
                            ActividadRepository actividadRepository,
                            RegistroImpresionRepository registroImpresionRepository) {
        _asociadoRepository = asociadoRepository;
        _fichaRepository = fichaRepository;
        _actividadRepository = actividadRepository;
        _registroImpresionRepository = registroImpresionRepository;
    }

    @Override
    public String GenerarFicha(List<Integer> asociados, Integer actividadId, String[] datos, int fichaId, Usuario usuario) throws IOException, COSVisitorException {

        Ficha ficha = _fichaRepository.obtenerFicha(fichaId, usuario.getLenguaje());
        Actividad actividad = null;
        if (actividadId != null)
            actividad = _actividadRepository.findOne(actividadId);

        List<String> resultado = new ArrayList();
        for (Integer id : asociados) {
            String nombreFicha = UUID.randomUUID().toString().concat(PDF);
            String plantilla = Paths.get(_fichaProperties.getCarpetaPlantilla(), ficha.getArchivo()).toString();
            String destino = Paths.get(_fichaProperties.getCarpetaFichas(), nombreFicha).toString();

            CreadorPdf creadorPdf = new CreadorPdf(plantilla, destino);
            if (actividad != null)
                creadorPdf.RellenarPdf(new ActividadPdfFiller(actividad));

            Asociado asociado = _asociadoRepository.findByIdAndFetchCargosEagerly(id);
            creadorPdf.RellenarPdf(new AsociadoPdfFiller(asociado));

            creadorPdf.Cerrar();
            resultado.add(destino);
        }

        String path = resultado.get(0);
        if (resultado.size() > 1)
            path = ComprimirFichas(resultado, _fichaProperties.getCarpetaFichas());

        // Registrar la impresion
        String nombreFichero = Paths.get(path).getFileName().toString();
        RegistroImpresion registroImpresion = new RegistroImpresion(usuario.getId(), nombreFichero);
        _registroImpresionRepository.save(registroImpresion);

        return path;
    }

    @Override
    public List<Ficha> ObtenerFichas(String lenguaje) {
        return _fichaRepository.obtenerFichas(lenguaje);
    }

    private String ComprimirFichas(List<String> fichas, String destino) throws IOException {

        String pathFolder = Paths.get(destino, UUID.randomUUID().toString()).toString();
        File folder = new File(pathFolder);
        folder.mkdir();

        for (String ficha : fichas) {
            File fichaFile = new File(ficha);
            fichaFile.renameTo(new File(Paths.get(pathFolder, fichaFile.getName()).toString()));
        }

        String pathZip = pathFolder.concat(ZIP);
        ZipUtil.pack(folder, new File(pathZip));

        FileUtils.deleteDirectory(folder);

        return pathZip;
    }
}