package org.scoutsfev.cudu.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@IdClass(FichaId.class)
@Table(name = "ficha")
public class Ficha implements Serializable {

    @Id
    @Column(length = 3)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;


    @Id
    @Column(length = 3)
    private String lenguaje;

    @Column
    @NotNull
    @Size(min = 5, max = 200)
    private String nombre;

    @Column
    @NotNull
    @Size(min = 5, max = 200)
    private String plantilla;

    @Column(length = 1)
    private int tipo;

    protected Ficha() {
    }

    public Ficha(Integer id, String lenguaje, String nombre, String archivo, int tipo) {
        this.id = id;
        this.lenguaje = lenguaje;
        this.nombre = nombre;
        this.plantilla = archivo;
        this.tipo = tipo;
    }

    public int getId() {

        return id;
    }

    public void setId(Integer id) {

        this.id = id;
    }

    public String getLenguaje() {

        return this.lenguaje;
    }

    public void setLenguaje(String lenguaje) {

        this.lenguaje = lenguaje;
    }

    public String getNombre() {

        return this.nombre;
    }

    public void setNombre(String nombre) {

        this.nombre = nombre;
    }

    public String getArchivo() {

        return this.plantilla;
    }

    public void setArchivo(String archivo) {

        this.plantilla = archivo;
    }

    public int getTipo() {
        return this.tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
