package org.scoutsfev.cudu.pdfbuilder;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.scoutsfev.cudu.domain.AmbitoEdicion;
import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.TipoAsociado;
import org.scoutsfev.cudu.support.TestIntegracion;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

@Category(TestIntegracion.class)
public class PdfTableTests {

    private File tempFolder;

    @Before
    public void SetUp() {
        tempFolder = new File("temp");
        assertTrue(tempFolder.mkdir());
    }

    @After
    public void tearDown() throws Exception {
        FileSystemUtils.deleteRecursively(tempFolder);
    }

    @Test
    public void puede_generar_contenidos() {
        List<Asociado> asociados = new ArrayList<>();
        Asociado a1 = new Asociado("grupo1", TipoAsociado.Joven, AmbitoEdicion.Asociacion, "Ragnar", "Lordbrok", LocalDate.now());
        a1.setDireccion("Escandinavia");
        asociados.add(a1);
        Asociado a2 = new Asociado("grupo1", TipoAsociado.Joven, AmbitoEdicion.Asociacion, "Rollo", "Lordbrok", LocalDate.now());
        a2.setDireccion("Escandinavia");
        asociados.add(a2);

        PdfTable<Asociado> tabla = new PdfTable<>(asociados);
        String[][] contenido = tabla.GetContent(new Columna[]{new Columna("nombre", "nombre", 0), new Columna("direccion", "direccion", 0)});

        String[][] esperado = new String[][]{
                new String[]{"Ragnar Lordbrok", "Escandinavia"},
                new String[]{"Rollo Lordbrok", "Escandinavia"}
        };

        Assert.assertArrayEquals(contenido, esperado);
    }

    @Test
    public void si_columna_no_existe_devuelve_valor_vacio() {
        List<Asociado> asociados = new ArrayList<>();
        Asociado a1 = new Asociado("grupo1", TipoAsociado.Joven, AmbitoEdicion.Asociacion, "Ragnar", "Lordbrok", LocalDate.now());
        a1.setDireccion("Escandinavia");
        asociados.add(a1);
        Asociado a2 = new Asociado("grupo1", TipoAsociado.Joven, AmbitoEdicion.Asociacion, "Rollo", "Lordbrok", LocalDate.now());
        a2.setDireccion("Escandinavia");
        asociados.add(a2);

        PdfTable<Asociado> tabla = new PdfTable<>(asociados);
        String[][] contenido = tabla.GetContent(new Columna[]{new Columna("nombre", "nombre", 0), new Columna("noexiste", "", 0), new Columna("direccion", "direccion", 0)});

        String[][] esperado = new String[][]{
                new String[]{"Ragnar Lordbrok", "", "Escandinavia"},
                new String[]{"Rollo Lordbrok", "", "Escandinavia"}
        };

        Assert.assertArrayEquals(contenido, esperado);
    }

    @Test
    public void puede_crear_pdf() throws IOException, COSVisitorException {
        List<Asociado> asociados = new ArrayList<>();
        Asociado a1 = new Asociado("grupo1", TipoAsociado.Joven, AmbitoEdicion.Asociacion, "Ragnar", "Lordbrok", LocalDate.now());
        a1.setDireccion("Escandinavia");
        asociados.add(a1);
        Asociado a2 = new Asociado("grupo1", TipoAsociado.Joven, AmbitoEdicion.Asociacion, "Rollo", "Lordbrok", LocalDate.now());
        a2.setDireccion("Escandinavia");
        asociados.add(a2);

        PdfTable<Asociado> tabla = new PdfTable<>(asociados);
        String archivo = tabla.CreatePdfTable(new Columna[]{new Columna("nombre", "nombre", 0), new Columna("direccion", "direccion", 0)},"");

        assertTrue(new File(archivo).exists());
    }
}
