package org.scoutsfev.cudu.services;

import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.joda.time.DateTime;
import org.joda.time.Years;
import org.scoutsfev.cudu.pdfbuilder.PdfFiller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.scoutsfev.cudu.domain.Asociado;

import java.time.ZoneId;
import java.util.Date;

public class AsociadoPdfFiller extends PdfFiller<Asociado> {

    private static final Logger logger = LoggerFactory.getLogger(AsociadoPdfFiller.class);

    private Asociado asociado;

    public AsociadoPdfFiller(Asociado asociado) {
        this.asociado = asociado;
    }

    @Override
    public void RellenarPdf(PDAcroForm form) {
        try {

            // Nombre
            PDField nombre = form.getField("Asociado#Nombre");
            if (nombre != null) nombre.setValue(asociado.getNombre());

            // Apellidos
            PDField apellidos = form.getField("Asociado#Apellidos");
            if (apellidos != null) apellidos.setValue(asociado.getApellidos());

            // DNI
            PDField dni = form.getField("Asociado#DNI");
            if (dni != null) dni.setValue(asociado.getDni());

            // Fecha de nacimiento
            PDField fechaNacimiento = form.getField("Asociado#FechaNacimiento");
            if (fechaNacimiento != null) fechaNacimiento.setValue(asociado.getFechaNacimiento().toString());

            // Edad
            PDField edad = form.getField("Asociado#Edad");
            if (edad != null) {
                Date nacimiento = Date.from(asociado.getFechaNacimiento().atStartOfDay(ZoneId.systemDefault()).toInstant());
                DateTime dtNacimiento = new DateTime(nacimiento.getTime());
                DateTime dtAhora = DateTime.now();

                Integer años = Years.yearsBetween(dtNacimiento, dtAhora).getYears();
                edad.setValue(años.toString());
            }

            // Nombre del padre o tutor
            PDField padreTutor = form.getField("Asociado#PadreTutor");
            if (padreTutor != null) {
                padreTutor.setValue(asociado.getNombrePadre());
                if (asociado.getNombrePadre().isEmpty() && asociado.getTieneTutorLegal()) {
                    //TODO: Storage Tutor
                }
            }

            // Nombre de la madre o tutor
            PDField madreTutor = form.getField("Asociado#MadreTutor");
            if (madreTutor != null) {
                madreTutor.setValue(asociado.getNombreMadre());
                if (asociado.getNombreMadre().isEmpty() && asociado.getTieneTutorLegal()) {
                    //TODO: Storage Tutor
                }
            }

            // Domicilio
            PDField domicilio = form.getField("Asociado#Domicilio");
            if (domicilio != null) domicilio.setValue(asociado.getDireccion().toString());

            // Municipio
            PDField municipio = form.getField("Asociado#Municipio");
            if (municipio != null) municipio.setValue(asociado.getMunicipio().toString());

            // Codigo Postal
            PDField cp = form.getField("Asociado#CodigoPostal");
            if (cp != null) cp.setValue(asociado.getCodigoPostal().toString());

            // Telefono 1
            PDField telefono1 = form.getField("Asociado#Telefono1");
            if (telefono1 != null) telefono1.setValue(asociado.getTelefonoMovil().toString());

            // Telefono 2
            PDField telefono2 = form.getField("Asociado#Telefono2");
            if (telefono2 != null) {
                if (!asociado.getTelefonoCasa().isEmpty())
                    telefono2.setValue(asociado.getTelefonoCasa().toString());
                if (!asociado.getTelefonoPadre().isEmpty())
                    telefono2.setValue(asociado.getTelefonoPadre().toString());
                if (!asociado.getTelefonoMadre().isEmpty())
                    telefono2.setValue(asociado.getTelefonoMadre().toString());
            }

            // Numero de la Seguridad Social
            PDField nss = form.getField("Asociado#NumeroSeguridadSocial");
            if (nss != null) nss.setValue(asociado.getSeguridadSocial().toString());

            // Email
            PDField email = form.getField("Asociado#Email");
            if (email != null) email.setValue(asociado.getEmail().toString());

            // Notas
            PDField notas = form.getField("Asociado#Notas");
            if (notas != null) notas.setValue(asociado.getNotas().toString());

            // Profesion
            PDField profesion = form.getField("Asociado#Profesion");
            if (profesion != null) profesion.setValue(asociado.getProfesion().toString());

            // Estudios
            PDField estudios = form.getField("Asociado#Estudios");
            if (estudios != null) estudios.setValue(asociado.getEstudios().toString());

        } catch (Exception ex) {

            logger.error("Error al rellenar la plantilla:");
            logger.error(ex.getMessage());
        }
    }

    @Override
    public Asociado Get() {
        return this.asociado;
    }
}