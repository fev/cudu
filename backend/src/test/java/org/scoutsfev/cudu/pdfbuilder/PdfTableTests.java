package org.scoutsfev.cudu.pdfbuilder;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.scoutsfev.cudu.domain.AmbitoEdicion;
import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.TipoAsociado;
import org.scoutsfev.cudu.web.DatosGraficasController;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class PdfTableTests {

    @Before
    public void SetUp() {

    }

    @Test
    public void puede_generar_contenidos() {
        List<Asociado> asociados = new ArrayList();
        Asociado a1 = new Asociado("grupo1", TipoAsociado.Joven, AmbitoEdicion.Asociacion, "Ragnar", "Lordbrok", LocalDate.now());
        a1.setDireccion("Escandinavia");
        asociados.add(a1);
        Asociado a2 = new Asociado("grupo1", TipoAsociado.Joven, AmbitoEdicion.Asociacion, "Rollo", "Lordbrok", LocalDate.now());
        a2.setDireccion("Escandinavia");
        asociados.add(a2);

        PdfTable<Asociado> tabla = new PdfTable(asociados);
        String[][] contenido = tabla.GetContent(new Columna[]{new Columna("nombre", "nombre", 0), new Columna("direccion", "direccion", 0)});

        String[][] esperado = new String[][]{
                new String[]{"Ragnar Lordbrok", "Escandinavia"},
                new String[]{"Rollo Lordbrok", "Escandinavia"}
        };

        Assert.assertArrayEquals(contenido, esperado);
    }

    @Test
    public void si_columna_no_existe_devuelve_valor_vacio() {
        List<Asociado> asociados = new ArrayList();
        Asociado a1 = new Asociado("grupo1", TipoAsociado.Joven, AmbitoEdicion.Asociacion, "Ragnar", "Lordbrok", LocalDate.now());
        a1.setDireccion("Escandinavia");
        asociados.add(a1);
        Asociado a2 = new Asociado("grupo1", TipoAsociado.Joven, AmbitoEdicion.Asociacion, "Rollo", "Lordbrok", LocalDate.now());
        a2.setDireccion("Escandinavia");
        asociados.add(a2);

        PdfTable<Asociado> tabla = new PdfTable(asociados);
        String[][] contenido = tabla.GetContent(new Columna[]{new Columna("nombre", "nombre", 0), new Columna("noexiste", "", 0), new Columna("direccion", "direccion", 0)});

        String[][] esperado = new String[][]{
                new String[]{"Ragnar Lordbrok", "", "Escandinavia"},
                new String[]{"Rollo Lordbrok", "", "Escandinavia"}
        };

        Assert.assertArrayEquals(contenido, esperado);
    }

    @Test
    public void puede_crear_pdf() throws IOException, COSVisitorException {
        List<Asociado> asociados = new ArrayList();
        Asociado a1 = new Asociado("grupo1", TipoAsociado.Joven, AmbitoEdicion.Asociacion, "Ragnar", "Lordbrok", LocalDate.now());
        a1.setDireccion("Escandinavia");
        asociados.add(a1);
        Asociado a2 = new Asociado("grupo1", TipoAsociado.Joven, AmbitoEdicion.Asociacion, "Rollo", "Lordbrok", LocalDate.now());
        a2.setDireccion("Escandinavia");
        asociados.add(a2);

        PdfTable<Asociado> tabla = new PdfTable(asociados);
        String archivo = tabla.CreatePdfTable(new Columna[]{new Columna("nombre", "nombre", 0), new Columna("direccion", "direccion", 0)});

        Assert.assertTrue(new File(archivo).exists());
    }
}
