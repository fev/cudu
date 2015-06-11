package org.scoutsfev.cudu.services;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.Ficha;
import org.scoutsfev.cudu.storage.AsociadoRepository;
import org.scoutsfev.cudu.storage.FichaRepository;
import org.scoutsfev.cudu.web.FichaProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

@Service
@EnableConfigurationProperties
public class FichaServiceImpl implements FichaService {

    @Autowired
    private FichaProperties _fichaProperties;

    private AsociadoRepository _asociadoRepository;
    private FichaRepository _fichaRepository;

    @Autowired
    public FichaServiceImpl(AsociadoRepository asociadoRepository, FichaRepository fichaRepository) {
        _asociadoRepository = asociadoRepository;
        _fichaRepository = fichaRepository;
    }

    @Override
    public List<String> GenerarFichaAsociados(List<Integer> asociados, String[] datos, int fichaId, String lenguaje) throws IOException, COSVisitorException {
        Ficha ficha = _fichaRepository.obtenerFicha(fichaId, lenguaje);
        String nombreFicha = UUID.randomUUID().toString().concat(".pdf");
        String plantilla = Paths.get(_fichaProperties.getCarpetaPlantilla(), ficha.getArchivo()).toString();
        String destino = Paths.get(_fichaProperties.getCarpetaFichas(), nombreFicha).toString();

        List<String> resultado = new ArrayList();
        PdfFiller rellenador = new AsociadoPdfFiller();
        for (Integer id : asociados) {
            Asociado asociado = _asociadoRepository.findByIdAndFetchCargosEagerly(id);
            rellenador.SetUp(asociado);
            resultado.add(rellenador.CrearPdf(plantilla, destino));
        }

        return resultado;
    }

    @Override
    public List<String> GenerarFichaActividad(List<Integer> asociados, int actividadId, int fichaId, String lenguaje) throws IOException, COSVisitorException {
        List<String> resultado = new ArrayList<String>();

        return resultado;
    }

    @Override
    public String[] GenerarFichaActividad(int actividadId, int fichaId, String lenguaje) throws IOException, COSVisitorException {
        return new String[0];
    }
}