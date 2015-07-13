package org.scoutsfev.cudu.services;

import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.scoutsfev.cudu.domain.Actividad;
import org.scoutsfev.cudu.pdfbuilder.PdfFiller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActividadPdfFiller extends PdfFiller<Actividad> {

    private static Logger logger = LoggerFactory.getLogger(ActividadPdfFiller.class);

    private Actividad actividad;

    public ActividadPdfFiller(Actividad actividad) {
        this.actividad = actividad;
    }

    @Override
    public void RellenarPdf(PDAcroForm form) {
        try {

            PDField nombreActividad = form.getField("Actividad#Nombre");
            if(nombreActividad != null) nombreActividad.setValue(this.actividad.getNombre());

        } catch (Exception ex) {

            logger.error("Error al rellenar la plantilla:");
            logger.error(ex.getMessage());
        }
    }

    @Override
    public Actividad Get() {
        return this.actividad;
    }
}
