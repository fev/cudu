package org.scoutsfev.cudu.services;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;

import java.io.File;
import java.io.IOException;

public class CreadorPdf {

    private PDDocument pdDocument;
    private PDAcroForm pdAcroForm;
    private String destino;

    public CreadorPdf(String plantilla, String destino) throws IOException {
        this.pdDocument = PDDocument.load(new File(plantilla), null);
        this.pdAcroForm = this.pdDocument.getDocumentCatalog().getAcroForm();
        this.destino = destino;
    }

    public void RellenarPdf(PdfFiller pdfFiller) {
        if (pdAcroForm != null)
            pdfFiller.RellenarPdf(this.pdAcroForm);
    }

    public void Cerrar() throws IOException, COSVisitorException {
        this.pdDocument.save(destino);
        this.pdDocument.close();
    }
}
