package org.scoutsfev.cudu.services;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;

import java.io.File;
import java.io.IOException;

public abstract class PdfFiller<T> {
    abstract void RellenarPdf(PDAcroForm form);

    abstract T GetEntity();

    abstract void SetUp(T entity);

    public String CrearPdf(String plantilla, String destino) throws IOException, COSVisitorException {
        PDDocument pdfDocument = PDDocument.load(new File(plantilla), null);
        PDAcroForm acroForm = pdfDocument.getDocumentCatalog().getAcroForm();

        if (acroForm != null) {
            RellenarPdf(acroForm);
        }

        pdfDocument.save(destino);
        pdfDocument.close();

        return destino;
    }
}

