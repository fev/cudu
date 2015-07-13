package org.scoutsfev.cudu.pdfbuilder;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.joda.time.DateTime;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
public class CreadorPdf {

    private PDDocument pdDocument;
    private PDAcroForm pdAcroForm;
    private String destino;

    public CreadorPdf(String plantilla, String destino) throws IOException {
        this.pdDocument = PDDocument.load(new File(plantilla), null);
        this.pdAcroForm = this.pdDocument.getDocumentCatalog().getAcroForm();
        this.destino = destino;
    }

    public void RellenarPdf(PdfFiller pdfFiller) throws IOException {
        if (pdAcroForm != null)
            pdfFiller.RellenarPdf(this.pdAcroForm);

        // Nombre Fichero
        PDField nombre = this.pdAcroForm.getField("#NombreFichero");
        if(nombre != null) nombre.setValue(Paths.get(destino).getFileName().toString());

        // Fecha Fichero
        PDField fecha = this.pdAcroForm.getField("#Fecha");
        if(fecha != null) fecha.setValue(DateTime.now().toString("dd/MM/yyyy"));
    }

    public void Cerrar() throws IOException, COSVisitorException {
        this.pdDocument.save(destino);
        this.pdDocument.close();
    }
}
