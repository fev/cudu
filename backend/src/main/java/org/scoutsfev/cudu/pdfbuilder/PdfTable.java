package org.scoutsfev.cudu.pdfbuilder;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
//import org.scoutsfev.cudu.web.properties.FichaProperties;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;

//@EnableConfigurationProperties
public class PdfTable<T extends IPrintable> extends BaseTable {

    private static final Logger logger = LoggerFactory.getLogger(PdfTable.class);

    private List<T> list;
    private List<Integer> rowsHeight;
//    @Autowired
//    private FichaProperties _fichaProperties;

    public PdfTable(List<T> list) {
        this.list = list;
        this.rowsHeight= new ArrayList<Integer>();
    }

    public String CreatePdfTable(Columna[] columns, String title) throws IOException, COSVisitorException {
        if (list.size() == 0) {
            logger.warn("Impossible to create a PDF file from an empty list");
            return EMPTY;
        }

        //Aquí añado el cálculo de la lista de alturas de cada fila.
        Table table = super.CreateTable(Arrays.asList(columns), GetContent(columns), rowsHeight);
        String archivo = Paths.get("temp", UUID.randomUUID().toString() + ".pdf").toString();
//        String archivo = Paths.get(_fichaProperties.getCarpetaFichas(), UUID.randomUUID().toString() + ".pdf").toString();
        new PDFTableGenerator().generatePDF(table, archivo, title);

        return archivo;
    }

    public String[][] GetContent(Columna[] columns) {
        List<String[]> contents = new ArrayList<>();
        //Para cada fila (asociado).
        for (T entity : list) {
            List<String> fila = new ArrayList<>();
            //Se recuperan los datos de un asociado.
            Map<String, String> map = entity.ToPrintableRow();
            //Para cada campo de la fila asociado.
            int altoFila=1;
            for (Columna c : columns) {
                try {
                    //Si hay un valor para ese campo de la fila
                    if (map.containsKey(c.getClave()) && map.get(c.getClave())!=null) {
                      // Aquí se controla si un contenido se pasa de ancho y por tanto necesita dos alturas.
                      String clave=map.get(c.getClave());
                      if (clave.length() >= (int)(c.getWidth()*0.2)){
                        altoFila=2;
                      }
                      fila.add(clave);

                    }
                    else
                        fila.add(EMPTY);
                } catch (Exception ex) {
                    logger.error("Error al generar tabla en pdf de tipo '{0}, columna '{1}'", list.get(0).getClass(), c.getName());
                    throw ex;
                }
            }

            contents.add(fila.toArray(new String[fila.size()]));
            rowsHeight.add(altoFila);
        }

        return contents.toArray(new String[contents.size()][]);
    }
}
