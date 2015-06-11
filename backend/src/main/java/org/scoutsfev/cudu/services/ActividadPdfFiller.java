package org.scoutsfev.cudu.services;

import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.scoutsfev.cudu.domain.Actividad;
import org.scoutsfev.cudu.domain.Asociado;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActividadPdfFiller extends PdfFiller {

    @Override
    void RellenarPdf(PDAcroForm form) {

    }

    @Override
    Object GetEntity() {
        return null;
    }

    @Override
    void SetUp(Object entity) {

    }
}
