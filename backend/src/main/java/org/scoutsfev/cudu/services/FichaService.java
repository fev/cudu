package org.scoutsfev.cudu.services;

import org.apache.pdfbox.exceptions.COSVisitorException;

import java.io.IOException;

public interface FichaService {
    void GenerarFicha(int asociadoId, int fichaId, String lenguaje) throws IOException, COSVisitorException;
}