package org.scoutsfev.cudu.services;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.scoutsfev.cudu.domain.Ficha;

import java.io.IOException;
import java.util.List;

public interface FichaService {
    String GenerarFicha(List<Integer> asociados, Integer actividad, String[] datos, int fichaId, String lenguaje) throws IOException, COSVisitorException;

    List<Ficha> ObtenerFichas(String lenguaje, int tipo);
}