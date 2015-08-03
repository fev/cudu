package org.scoutsfev.cudu.pdfbuilder;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.util.List;

public class BaseTable {

    // Page configuration
    protected static final PDRectangle PAGE_SIZE = PDPage.PAGE_SIZE_A4;
    protected static final float MARGIN = 50;
    protected static final boolean IS_LANDSCAPE = true;

    // Font configuration
    protected static final PDFont TEXT_FONT = PDType1Font.HELVETICA;
    protected static final PDFont TEXT_HEADER_FONT = PDType1Font.HELVETICA_BOLD;
    protected static final float FONT_SIZE = 12;

    // Table configuration
    protected static final float ROW_HEIGHT = 15;
    protected static final float CELL_MARGIN = 2;
    protected static final float DEFAULT_CELL_WIDTH = 250;

    protected float TABLE_HEIGHT = IS_LANDSCAPE ? PAGE_SIZE.getWidth() - (2 * MARGIN) : PAGE_SIZE.getHeight() - (2 * MARGIN);
    protected static final String EMPTY = "";

    public Table CreateTable(List<Columna> columns, String [] [] contents) {

        Table table = new TableBuilder()
                .setCellMargin(CELL_MARGIN)
                .setColumns(columns)
                .setContent(contents)
                .setHeight(TABLE_HEIGHT)
                .setNumberOfRows(contents.length)
                .setRowHeight(ROW_HEIGHT)
                .setMargin(MARGIN)
                .setPageSize(PAGE_SIZE)
                .setLandscape(IS_LANDSCAPE)
                .setTextFont(TEXT_FONT)
                .setHeaderTextFont(TEXT_HEADER_FONT)
                .setFontSize(FONT_SIZE)
                .build();

        return table;
    }
}
