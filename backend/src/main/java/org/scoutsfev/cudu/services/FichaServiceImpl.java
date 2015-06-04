package org.scoutsfev.cudu.services;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.Ficha;
import org.scoutsfev.cudu.storage.AsociadoRepository;
import org.scoutsfev.cudu.storage.FichaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class FichaServiceImpl implements FichaService {

    private AsociadoRepository _asociadoRepository;
    private FichaRepository _fichaRepository;

    @Autowired
    public FichaServiceImpl(AsociadoRepository asociadoRepository, FichaRepository fichaRepository)
    {
        _asociadoRepository = asociadoRepository;
        _fichaRepository = fichaRepository;
    }

    @Override
    public void GenerarFicha(int asociadoId, int fichaId, String lenguaje) throws IOException, COSVisitorException {

        Asociado asociado = _asociadoRepository.findByIdAndFetchCargosEagerly(asociadoId);
        Ficha ficha = _fichaRepository.obtenerFicha(fichaId, lenguaje);

        //TODO: from storage
        String formTemplate = "plantillas/" + ficha.getArchivo();
        String filledForm = "formularios/output.pdf";

        PDDocument pdfDocument = PDDocument.load(new File(formTemplate), null);
        PDAcroForm acroForm = pdfDocument.getDocumentCatalog().getAcroForm();

        //TODO: Inject this, refactor
        if (acroForm != null) {
            PDField nombre = acroForm.getField("Nombre");
            nombre.setValue(asociado.getNombre());

            PDField apellidos = acroForm.getField("Apellidos");
            apellidos.setValue(asociado.getApellidos());
        }

        pdfDocument.save(filledForm);
        pdfDocument.close();
    }
}
