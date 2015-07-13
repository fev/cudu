package org.scoutsfev.cudu.services;

import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.joda.time.DateTime;
import org.joda.time.Years;
import org.scoutsfev.cudu.domain.Actividad;
import org.scoutsfev.cudu.pdfbuilder.PdfFiller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZoneId;
import java.util.Date;

public class ActividadPdfFiller extends PdfFiller<Actividad> {

    private static Logger logger = LoggerFactory.getLogger(ActividadPdfFiller.class);

    private Actividad actividad;

    public ActividadPdfFiller(Actividad actividad) {
        this.actividad = actividad;
    }

    @Override
    public void RellenarPdf(PDAcroForm form) {
        try {
            //Nombre
            PDField nombreActividad = form.getField("Actividad#Nombre");
            if (nombreActividad != null) nombreActividad.setValue(this.actividad.getNombre());

            // Notas
            PDField notasActividad = form.getField("Actividad#Notas");
            if (notasActividad != null) notasActividad.setValue(this.actividad.getNotas());

            // Creador
            PDField creadorActividad = form.getField("Actividad#Creador");
            if (creadorActividad != null) creadorActividad.setValue(this.actividad.getCreadaPor());

            // Lugar
            PDField lugarActividad = form.getField("Actividad#Lugar");
            if (lugarActividad != null) lugarActividad.setValue(this.actividad.getLugar());

            // Responsable
            PDField responsableActividad = form.getField("Actividad#Responsable");
            if (responsableActividad != null) responsableActividad.setValue(this.actividad.getResponsable());

            // Precio
            PDField precioActividad = form.getField("Actividad#Precio");
            if (precioActividad != null) precioActividad.setValue(this.actividad.getPrecio());

            // Fecha Inicio
            PDField fechaInicio = form.getField("Actividad#FechaInicio");
            if (fechaInicio != null) fechaInicio.setValue(actividad.getFechaInicio().toString());

            // Fecha Fin
            PDField fechaFin = form.getField("Actividad#FechaFin");
            if (fechaFin != null) fechaFin.setValue(actividad.getFechaFin().toString());
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
