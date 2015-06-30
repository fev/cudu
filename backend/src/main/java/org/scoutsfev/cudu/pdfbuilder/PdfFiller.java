package org.scoutsfev.cudu.pdfbuilder;

import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;

import java.io.IOException;

public abstract class PdfFiller<T> {
    public abstract void RellenarPdf(PDAcroForm form) throws IOException;
    public abstract T Get();
}