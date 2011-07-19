package org.scoutsfev.cudu.view;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.view.document.AbstractPdfView;
import org.springframework.ui.Model;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.util.Collection;
import java.util.Iterator;
import javax.swing.GroupLayout.Alignment;


public class PdfReportView extends AbstractPdfView{

    private  Font fuenteNegra10 = new Font(Font.TIMES_ROMAN, 12, Font.BOLD, Color.BLACK);
    private  Font fuente10 = new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.BLACK);
    private  Font fuente8 = new Font(Font.TIMES_ROMAN, 8, Font.NORMAL, Color.BLACK);
    private  Font fuenteNegra25 = new Font(Font.TIMES_ROMAN, 25, Font.BOLD, Color.BLACK);

    Color grisClaro = new Color( 230,230,230);
    Color azulClaro = new Color( 124,195,255 );
    @Override
    protected void buildPdfDocument(Map model1, Document document,
                    PdfWriter writer, HttpServletRequest request,
                    HttpServletResponse response) throws Exception {
        
        //EXTRAEMOS LA INF DEL MODELO
	Model model2 = (Model) model1.get("model");
            
        Map<String,Object> model = model2.asMap();
        ///MI VERSION
        String[] lstColumnas = (String[]) model.get("columnas");
        String userStamp = (String) model.get("userStamp");

        Collection<Object[]> asociados = (Collection<Object[]>) model.get("objetos");
        String dateFormat = (String) model.get("timestamp");
        

        //CONF INICIAL DEL DOCU. podemos modificar el pagesize según el nº de columnas
        if(lstColumnas.length>15)
            document.setPageSize( PageSize.A2.rotate() );
        if(lstColumnas.length>10)
            document.setPageSize( PageSize.A3.rotate() );
        else
            document.setPageSize( PageSize.A4.rotate() );
        document.open();
        
        //////////////EMPEZAMOS A PINTAR EL DOCU///////////////////////////////
        
        
        Paragraph ParrafoHoja = new Paragraph();
        Image im;
        
           try
        {   
            im = Image.getInstance("/home/gaxp/git/cudu/target/cudu-1.0.0.RC5/theme");
            im.setAlignment(Image.ALIGN_CENTER | Image.TEXTWRAP );
            im.setWidthPercentage (50);
            ParrafoHoja.add(im);
            
        }
        catch(Exception e)
        {
            e.printStackTrace ();
        }
                
        // Agregamos una linea en blanco al principio del documento
        agregarLineasEnBlanco(ParrafoHoja, 1);
        // Colocar un encabezado (en mayusculas)
        Paragraph p = new Paragraph("ESTO ES EL LISTADO DE CUDU!", fuenteNegra25);
        ParrafoHoja.add(p);
        p = new Paragraph(userStamp+", a "+dateFormat+ " TotalAsociados: "+asociados.size(),fuenteNegra10);
        p.setAlignment(Alignment.CENTER.name());
        ParrafoHoja.add(p);
        agregarLineasEnBlanco(ParrafoHoja, 1);
        // 1.- AGREGAMOS LA TABLA

        //Agregar 2 lineas en blanco
        agregarLineasEnBlanco(ParrafoHoja, 2);
        document.add(ParrafoHoja);


        //añadimos la inf de los asociados
        PdfPCell cell;
        com.lowagie.text.pdf.PdfPTable  table2 = new com.lowagie.text.pdf.PdfPTable (lstColumnas.length);
        table2.setWidthPercentage(100);
        table2.setHorizontalAlignment(Element.ALIGN_CENTER);
        for(int i = 0; i < lstColumnas.length; i ++)
        {
            cell = new PdfPCell(new Paragraph(lstColumnas[i],fuenteNegra10));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor (grisClaro);
            cell.setUseVariableBorders(true);
            table2.addCell(cell);
        }

        Iterator itAsociados = asociados.iterator();
        boolean rowChangeColor = true;
        Color color;
        while(itAsociados.hasNext())
        {
            
            Object[] asociado = (Object[]) itAsociados.next();
            if(asociado!=null)
            {
                if(rowChangeColor)
                    color= grisClaro;
                else
                    color=Color.WHITE;
                rowChangeColor=!rowChangeColor;


                for(int i = 0; i < lstColumnas.length;i++)
                {

                    
                    if(asociado[i]!=null)
                    {
                        cell = new PdfPCell(new Paragraph(asociado[i].toString(),fuente10));
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (color);
                        cell.setUseVariableBorders(true);
                    }
                    else
                    {
                        cell = new PdfPCell(new Paragraph("",fuente10));
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (color);
                        cell.setUseVariableBorders(true);
                    }
                    table2.addCell(cell);
                }
                
            }
            
        }
        
        document.add(table2);
        
        
        }
        
            //Agrega las lineas en blanco  especificadas a un parrafo especificado
	private static void agregarLineasEnBlanco(Paragraph parrafo, int nLineas)
	{
		for (int i = 0; i < nLineas; i++)
			parrafo.add(new Paragraph(" "));
	}
}