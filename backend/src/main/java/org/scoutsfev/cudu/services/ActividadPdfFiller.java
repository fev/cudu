package org.scoutsfev.cudu.services;

import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.scoutsfev.cudu.domain.Actividad;
import org.scoutsfev.cudu.domain.Asociado;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ActividadPdfFiller extends PdfFiller<Actividad> {

    private static Logger logger = LoggerFactory.getLogger(ActividadPdfFiller.class);

    private Actividad actividad;

    public ActividadPdfFiller(Actividad actividad) {
        this.actividad = actividad;
    }

    @Override
    void RellenarPdf(PDAcroForm form) {
        try {

            PDField nombreActividad = form.getField("NombreActividad");
            nombreActividad.setValue(this.actividad.getNombre());

        } catch (Exception ex) {

            logger.error("Error al rellenar la plantilla:");
            logger.error(ex.getMessage());
        }
    }

    @Override
    Actividad Get() {
        return this.actividad;
    }
}
