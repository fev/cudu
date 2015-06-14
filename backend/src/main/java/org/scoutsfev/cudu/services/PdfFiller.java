package org.scoutsfev.cudu.services;

import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;

import java.io.IOException;

public abstract class PdfFiller<T> {
    abstract void RellenarPdf(PDAcroForm form) throws IOException;
    abstract T Get();
}