package org.scoutsfev.cudu.services;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.scoutsfev.cudu.domain.*;
import org.scoutsfev.cudu.pdfbuilder.Columna;
import org.scoutsfev.cudu.pdfbuilder.CreadorPdf;
import org.scoutsfev.cudu.pdfbuilder.PdfTable;
import org.scoutsfev.cudu.storage.*;
import org.scoutsfev.cudu.web.properties.FichaProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
@EnableConfigurationProperties
public class FichaServiceImpl implements FichaService {

    private String PDF = ".pdf";
    private String ZIP = ".zip";

    @Autowired
    private FichaProperties _fichaProperties;

    private AsociadoRepository _asociadoRepository;
    private GrupoRepository _grupoRepository;
    private FichaRepository _fichaRepository;
    private ActividadRepository _actividadRepository;
    private RegistroImpresionRepository _registroImpresionRepository;
    private MessageSource _messageSource;
    private Environment _environment;

    @Autowired
    public FichaServiceImpl(AsociadoRepository asociadoRepository,
                            GrupoRepository grupoRepository,
                            FichaRepository fichaRepository,
                            ActividadRepository actividadRepository,
                            RegistroImpresionRepository registroImpresionRepository,
                            MessageSource messageSource,
                            Environment environment) {
        _asociadoRepository = asociadoRepository;
        _grupoRepository = grupoRepository;
        _fichaRepository = fichaRepository;
        _actividadRepository = actividadRepository;
        _registroImpresionRepository = registroImpresionRepository;
        _messageSource = messageSource;
        _environment = environment;
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

            if (asociado.getGrupoId() != null && asociado.getGrupoId() != "") {
                Grupo grupo = _grupoRepository.findOne(asociado.getGrupoId());
                if (grupo != null) {
                    creadorPdf.RellenarPdf(new GrupoPdfFiller(grupo));
                }
            }

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
    public String GenerarListado(Integer[] asociados, String[] columnas, Usuario usuario) throws IOException, COSVisitorException {
        Iterable<Asociado> objectosAsociado = _asociadoRepository.findAll(Arrays.asList(asociados));

        // Obtener datos de la rama por cada asociado
        //Locale locale = Locale.forLanguageTag(usuario.getLenguaje());
        //--- si el tag lenguage es null...
        Locale locale;
        if (usuario.getLenguaje() == null)
            locale = Locale.forLanguageTag("es");
        else
            locale = Locale.forLanguageTag(usuario.getLenguaje());
        
        if (Arrays.asList(columnas).contains("rama")) {
            for (Asociado a : objectosAsociado) {
                List<String> ramas = new ArrayList();
                if (a.isRamaColonia()) {
                    ramas.add(_messageSource.getMessage("rama.colonia", null, locale));
                }
                if (a.isRamaManada()) {
                    ramas.add(_messageSource.getMessage("rama.manada", null, locale));
                }
                if (a.isRamaExploradores()) {
                    ramas.add(_messageSource.getMessage("rama.exploradores", null, locale));
                }
                if (a.isRamaExpedicion()) {
                    ramas.add(_messageSource.getMessage("rama.expedicion", null, locale));
                }
                if (a.isRamaRuta()) {
                    ramas.add(_messageSource.getMessage("rama.ruta", null, locale));
                }
                if (ramas.size() > 0) {
                    String rama = ramas.stream().map(Object::toString).collect(Collectors.joining(","));
                    a.setRama(rama);
                }
            }
        }

        // Obtener ancho de columnas y generar tabla
        PdfTable<Asociado> tabla = new PdfTable((List) objectosAsociado);
        List<Columna> cols = new ArrayList();
        for (String col : columnas) {
            String anchoClave = String.format("listado.columnas.ancho.%s", col);
            String anchoValor = _environment.getRequiredProperty(anchoClave);

            String traduccionClave = String.format("impresion.columna.%s", col);
            String traduccionValor = _messageSource.getMessage(traduccionClave, null, locale);

            cols.add(new Columna(traduccionValor, col, Float.valueOf(anchoValor)));
        }
        Columna[] array = new Columna[cols.size()];
        String archivo = tabla.CreatePdfTable(cols.toArray(array));

        return archivo;
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
