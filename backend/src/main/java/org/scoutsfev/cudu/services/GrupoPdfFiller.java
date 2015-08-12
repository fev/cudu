package org.scoutsfev.cudu.services;

import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.scoutsfev.cudu.domain.Grupo;
import org.scoutsfev.cudu.pdfbuilder.PdfFiller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class GrupoPdfFiller extends PdfFiller<Grupo> {

    private static final Logger logger = LoggerFactory.getLogger(GrupoPdfFiller.class);

    private Grupo grupo;

    public GrupoPdfFiller(Grupo grupo) {
        this.grupo = grupo;
    }

    @Override
    public void RellenarPdf(PDAcroForm form) throws IOException {

        // Nombre del grupo
        PDField nombreGrupo = form.getField("Grupo#Nombre");
        if (nombreGrupo != null) nombreGrupo.setValue(grupo.getNombre());
    }

    @Override
    public Grupo Get() {
        return this.grupo;
    }
}
