package org.scoutsfev.cudu.pdfbuilder;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;

public class PDFTableGenerator {

    private String fileName = "";
    private String title="";

    // Generates document from Table object
    public void generatePDF(Table table, String fileName, String title) throws IOException, COSVisitorException {
        PDDocument doc = null;
        if (title!=null)this.title=title;
        if(fileName != null)
            this.fileName = Paths.get(fileName).getFileName().toString();
        try {
            doc = new PDDocument();
            drawTable(doc, table);
            doc.save(fileName);
        } finally {
            if (doc != null) {
                doc.close();
            }
        }
    }

    // Configures basic setup for the table and draws it page by page
    public void drawTable(PDDocument doc, Table table) throws IOException {
        // Calculate pagination
        // Teniendo en cuenta que hay filas que tendrán doble altura si los datos no caben en la celda.
        // Les files de doble altura estan calculades en Table.getRowsHeights().

        // lastEntitiePerPAge : size=num de páginas. el contenido es el índice de la última entidad de cada pag.
        List<Integer> lastEntitiePerPage = new ArrayList<Integer>();

        // rowsTotalHeightPerPage : el numero de líneas máximas en una pag.
        Integer rowsTotalHeightPerPage = new Double(Math.floor(table.getHeight() / table.getRowHeight())).intValue() - 1; // subtract

        // rowsHeights : size=num de entidades. el contenido es 1 o 2 líneas que ocupa la entidad con ese índice.
        List<Integer> rowsHeights = table.getRowsHeights();
        // entitiesHeight : numero de línias acumuladas en esa pag mientras se recorren las entidades.
        Integer entitiesHeight=0;
        for(int i=0; i<rowsHeights.size(); i++){
          if( (entitiesHeight + rowsHeights.get(i)) <= rowsTotalHeightPerPage ){
            entitiesHeight+=rowsHeights.get(i);
          }
          else{
              lastEntitiePerPage.add(i-1);
              entitiesHeight=rowsHeights.get(i);
          }
        }
        lastEntitiePerPage.add(rowsHeights.size()-1);
        Integer numPages=lastEntitiePerPage.size();

        // Generate each page, get the content and draw it
        for (int pageCount = 0; pageCount < numPages; pageCount++) {
            Integer firstEntitie;
            if (pageCount==0) firstEntitie=0;
            else firstEntitie= lastEntitiePerPage.get(pageCount-1) +1;
            PDPage page = generatePage(doc, table);
            PDPageContentStream contentStream = generateContentStream(doc, page, table);
            String[][] currentPageContent = getContentForCurrentPage(table, firstEntitie, lastEntitiePerPage.get(pageCount));
            //String footer = String.format("%s - %s de %s", this.fileName, pageCount + 1, numPages);
            String footer = String.format("%s de %s",pageCount + 1, numPages);
            drawCurrentPage(table, footer, currentPageContent, contentStream, firstEntitie, lastEntitiePerPage.get(pageCount), rowsHeights.subList(firstEntitie, lastEntitiePerPage.get(pageCount)+1));
        }
    }

    private void drawHeader(Table table, String content, PDPageContentStream contentStream) throws IOException {
        float y = table.isLandscape() ? table.getPageSize().getWidth() - table.getMargin() / 2 : table.getPageSize().getHeight() - table.getMargin() / 2;
        freeDrawing(y, table, content, contentStream);
    }

    private void drawFooter(Table table, String content, PDPageContentStream contentStream) throws IOException {
        float y = table.getMargin() / 2;
        freeDrawing(y, table, content, contentStream);
    }

    private void freeDrawing(float y, Table table, String content, PDPageContentStream contentStream) throws IOException {
        float x = table.getMargin() + table.getCellMargin();
        contentStream.beginText();
        contentStream.moveTextPositionByAmount(x, y);
        contentStream.drawString(content != null ? content : "");
        contentStream.endText();
    }

    // Draws current page table grid and border lines and content
    private void drawCurrentPage(Table table, String footer, String[][] currentPageContent, PDPageContentStream contentStream, Integer startRange, Integer endRange, List<Integer> currentRowsHeights)
            throws IOException {
        float tableTopY = table.isLandscape() ? table.getPageSize().getWidth() - table.getMargin() : table.getPageSize().getHeight() - table.getMargin();

        // Draws grid and borders
        drawTableGrid(table, currentPageContent, contentStream, tableTopY, startRange, endRange);

        // Position cursor to start drawing content
        float nextTextX = table.getMargin() + table.getCellMargin();
        // Calculate center alignment for text in cell considering font height
        float nextTextY = tableTopY - (table.getRowHeight() / 2)
                - ((table.getTextFont().getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * table.getFontSize()) / 4);

        // Write column headers
        contentStream.setFont(table.getHeaderTextFont(), table.getFontSize());
        nextTextY = writeContentLine(table.getColumnsNamesAsArray(true), contentStream, nextTextX, nextTextY, table, 1);

        nextTextX = table.getMargin() + table.getCellMargin();

        // Write content
        contentStream.setFont(table.getTextFont(), table.getFontSize());
        for (int i = 0; i < currentPageContent.length; i++) {
            int alturaRow=  currentRowsHeights.get(i);
            nextTextY = writeContentLine(currentPageContent[i], contentStream, nextTextX, nextTextY, table, alturaRow);
            nextTextX = table.getMargin() + table.getCellMargin();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        drawHeader(table, LocalDate.now().format(formatter) + " - "+ this.title, contentStream);
        drawFooter(table, footer, contentStream);

        contentStream.close();
    }

    // Writes the content for one line
    private float writeContentLine(String[] lineContent, PDPageContentStream contentStream, float nextTextX, float nextTextY,
                                  Table table, int alturaRow) throws IOException {
        float nextY = nextTextY - table.getRowHeight();
        for (int i = 0; i < table.getNumberOfColumns(); i++) {
            String text = lineContent[i];
            contentStream.beginText();
            contentStream.moveTextPositionByAmount(nextTextX, nextTextY);
            // *0.19 para adaptarlo al ancho de los caracteres.
            float maxWidth = table.getColumns().get(i).getWidth();
            // Contamos quue máximo cada fila ocupará doble espacio
            if (text != null  ){
              if(alturaRow==2){
                int nmax=(int)(maxWidth*0.19);
                if (nmax>text.length()) {
                  nmax=text.length();
                }
                contentStream.drawString(text.substring(0,nmax));
                contentStream.moveTextPositionByAmount(0, 0 - table.getRowHeight());
                if(nmax<text.length())
                  contentStream.drawString(text.substring(nmax));
              }
              else {
                contentStream.drawString(text);
              }
            }
            else {contentStream.drawString("");}
            contentStream.endText();
            nextTextX += maxWidth;
        }
        nextY-=(alturaRow-1)*table.getRowHeight();
        return nextY;
    }

    private void drawTableGrid(Table table, String[][] currentPageContent, PDPageContentStream contentStream, float tableTopY, Integer startRange, Integer endRange)
            throws IOException {
        // Draw row lines
        float nextY = tableTopY;
        contentStream.drawLine(table.getMargin(), nextY, table.getMargin() + table.getWidth(), nextY);
        nextY -=table.getRowHeight();
        contentStream.drawLine(table.getMargin(), nextY, table.getMargin() + table.getWidth(), nextY);
        for (int i = 0; i < currentPageContent.length ; i++) {
            nextY -= (table.getRowsHeights().get(startRange + i) * table.getRowHeight());
            contentStream.drawLine(table.getMargin(), nextY, table.getMargin() + table.getWidth(), nextY);
        }
        
        // Draw column lines
        int rowsYContent=0;
        for (int i=startRange; i<=endRange;i++ ){
          rowsYContent+=table.getRowsHeights().get(i);
        }
        final float tableYLength = (table.getRowHeight() * (rowsYContent+1));
        final float tableBottomY = tableTopY - tableYLength;
        float nextX = table.getMargin();
        for (int i = 0; i < table.getNumberOfColumns(); i++) {
            contentStream.drawLine(nextX, tableTopY, nextX, tableBottomY);
            nextX += table.getColumns().get(i).getWidth();
        }
        contentStream.drawLine(nextX, tableTopY, nextX, tableBottomY);
    }

    private String[][] getContentForCurrentPage(Table table, Integer startRange, Integer endRange) {
        //endRange es exclusivo. Por eso +1.
        return Arrays.copyOfRange(table.getContent(), startRange, endRange+1);
    }

    private PDPage generatePage(PDDocument doc, Table table) {
        PDPage page = new PDPage();
        page.setMediaBox(table.getPageSize());
        page.setRotation(table.isLandscape() ? 90 : 0);
        doc.addPage(page);
        return page;
    }

    private PDPageContentStream
    generateContentStream(PDDocument doc, PDPage page, Table table) throws IOException {
        PDPageContentStream contentStream = new PDPageContentStream(doc, page, false, false);
        // User transformation matrix to change the reference when drawing.
        // This is necessary for the landscape position to draw correctly
        if (table.isLandscape()) {
            contentStream.concatenate2CTM(0, 1, -1, 0, table.getPageSize().getWidth(), 0);
        }

        return contentStream;
    }
}
