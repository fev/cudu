package org.scoutsfev.cudu.services;

import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.scoutsfev.cudu.domain.Asociado;

public class AsociadoPdfFiller extends PdfFiller<Asociado> {

    private static final Logger logger = LoggerFactory.getLogger(AsociadoPdfFiller.class);

    private Asociado asociado;

    public AsociadoPdfFiller(Asociado asociado)
    {
        this.asociado = asociado;
    }

    @Override
    public void RellenarPdf(PDAcroForm form) {
        try {

            PDField nombre = form.getField("Nombre");
            nombre.setValue(asociado.getNombre());

            PDField apellidos = form.getField("Apellidos");
            apellidos.setValue(asociado.getApellidos());

        } catch (Exception ex) {

            logger.error("Error al rellenar la plantilla:");
            logger.error(ex.getMessage());
        }
    }

    @Override
    Asociado Get() {
        return this.asociado;
    }
}