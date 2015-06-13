package org.scoutsfev.cudu.services;

import org.apache.pdfbox.exceptions.COSVisitorException;

import java.io.IOException;
import java.util.List;

public interface FichaService {
    List<String> GenerarFicha(List<Integer> asociados, Integer actividad, String[] datos, int fichaId, String lenguaje) throws IOException, COSVisitorException;
}