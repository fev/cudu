package org.scoutsfev.cudu.services;

import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;

public abstract class PdfFiller<T> {
    abstract void RellenarPdf(PDAcroForm form);
    abstract T Get();
}