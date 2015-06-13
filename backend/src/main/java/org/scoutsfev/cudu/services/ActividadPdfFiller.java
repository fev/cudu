package org.scoutsfev.cudu.services;

import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.scoutsfev.cudu.domain.Actividad;
import org.scoutsfev.cudu.domain.Asociado;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActividadPdfFiller extends PdfFiller<Actividad> {

    private Actividad actividad;

    public ActividadPdfFiller(Actividad actividad) {
        this.actividad = actividad;
    }

    @Override
    void RellenarPdf(PDAcroForm form) {

    }

    @Override
    Actividad Get() {
        return this.actividad;
    }
}
