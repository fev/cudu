/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scoutsfev.cudu.domain;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author gaxp
 */
@Entity
@Table(name = "curso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Curso.findAll", query = "SELECT c FROM Curso c"),
    @NamedQuery(name = "Curso.findById", query = "SELECT c FROM Curso c WHERE c.id = :id"),
    @NamedQuery(name = "Curso.findByNombre", query = "SELECT c FROM Curso c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Curso.findByAcronimo", query = "SELECT c FROM Curso c WHERE c.acronimo = :acronimo"),
    @NamedQuery(name = "Curso.findByAnyo", query = "SELECT c FROM Curso c WHERE c.anyo = :anyo"),
    @NamedQuery(name = "Curso.findByPrecio", query = "SELECT c FROM Curso c WHERE c.precio = :precio"),
    @NamedQuery(name = "Curso.findByDescripcion", query = "SELECT c FROM Curso c WHERE c.descripcion = :descripcion")})
public class Curso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "acronimo")
    private String acronimo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "anyo")
    private int anyo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "precio")
    private Double precio;
    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "curso")
    private Collection<MonograficosEnCursos> monograficosEnCursosCollection;

    public Curso() {
    }

    public Curso(Integer id) {
        this.id = id;
    }

    public Curso(Integer id, String nombre, String acronimo, int anyo) {
        this.id = id;
        this.nombre = nombre;
        this.acronimo = acronimo;
        this.anyo = anyo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAcronimo() {
        return acronimo;
    }

    public void setAcronimo(String acronimo) {
        this.acronimo = acronimo;
    }

    public int getAnyo() {
        return anyo;
    }

    public void setAnyo(int anyo) {
        this.anyo = anyo;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public Collection<MonograficosEnCursos> getMonograficosEnCursosCollection() {
        return monograficosEnCursosCollection;
    }

    public void setMonograficosEnCursosCollection(Collection<MonograficosEnCursos> monograficosEnCursosCollection) {
        this.monograficosEnCursosCollection = monograficosEnCursosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Curso)) {
            return false;
        }
        Curso other = (Curso) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.scoutsfev.cudu.domain.Curso[ id=" + id + " ]";
    }
    
}
