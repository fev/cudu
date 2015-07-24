package org.scoutsfev.cudu.services;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.scoutsfev.cudu.domain.Ficha;
import org.scoutsfev.cudu.domain.Usuario;

import java.io.IOException;
import java.util.List;

public interface FichaService {
    String GenerarFicha(List<Integer> asociados, Integer actividad, String[] datos, int fichaId, Usuario usuario) throws IOException, COSVisitorException;

    String GenerarListado(Integer[] asociados, String[] columnas, Usuario usuario) throws IOException, COSVisitorException;

    List<Ficha> ObtenerFichas(String lenguaje);
}