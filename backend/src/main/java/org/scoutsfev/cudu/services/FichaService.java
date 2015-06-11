package org.scoutsfev.cudu.services;

import org.apache.pdfbox.exceptions.COSVisitorException;

import java.io.IOException;
import java.util.List;

public interface FichaService {

    // Fichas donde el objecto principal es el asociado, especificando los datos
    List<String> GenerarFichaAsociados(List<Integer> asociados, String[] datos, int fichaId, String lenguaje) throws IOException, COSVisitorException;

    // Fichas donde los objetos principales son una actividad y un asociado
    List<String> GenerarFichaActividad(List<Integer> asociados, int actividadId, int fichaId, String lenguaje) throws IOException, COSVisitorException;

    // Fichas de actividades para todos los asociados asistentes
    String[] GenerarFichaActividad(int actividadId, int fichaId, String lenguaje) throws IOException, COSVisitorException;
}