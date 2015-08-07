package org.scoutsfev.cudu.pdfbuilder;

public class Columna {

    private String name;
    private String clave;
    private float width;

    public Columna(String name, String clave, float width) {
        this.name = name;
        this.width = width;
        this.clave = clave;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClave() {
        return this.clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }
}
