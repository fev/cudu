package org.scoutsfev.cudu.pdfbuilder;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class PdfTable<T extends IPrintable> extends BaseTable {

    private static final Logger logger = LoggerFactory.getLogger(PdfTable.class);

    private List<T> list;

    public PdfTable(List<T> list) {
        this.list = list;
    }

    public String CreatePdfTable(Columna[] columns) throws IOException, COSVisitorException {
        if (list.size() == 0) {
            logger.warn("Impossible to create a PDF file from an empty list");
            return EMPTY;
        }

        Table table = super.CreateTable(Arrays.asList(columns), GetContent(columns));
        String archivo = Paths.get("temp", UUID.randomUUID().toString() + ".pdf").toString();
        new PDFTableGenerator().generatePDF(table, archivo);

        return archivo;
    }

    public String[][] GetContent(Columna[] columns) {
        List<String[]> contents = new ArrayList();
        for (T entity : list) {
            List<String> fila = new ArrayList();
            Map<String, String> map = entity.ToPrintableRow();
            for (Columna c : columns) {
                try {
                    if (map.containsKey(c.getClave()))
                        fila.add(map.get(c.getClave()));
                    else
                        fila.add(EMPTY);
                } catch (Exception ex) {
                    logger.error("Error al generar tabla en pdf de tipo '{0}, columna '{1}'", list.get(0).getClass(), c.getName());
                    throw ex;
                }
            }

            contents.add(fila.toArray(new String[fila.size()]));
        }

        return contents.toArray(new String[contents.size()][]);
    }
}
