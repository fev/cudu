/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scoutsfev.cudu.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author gaxp
 */
@Entity
@Table(name = "monografico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Monografico.findAll", query = "SELECT m FROM Monografico m"),
    @NamedQuery(name = "Monografico.findById", query = "SELECT m FROM Monografico m WHERE m.id = :id"),
    @NamedQuery(name = "Monografico.findByNombre", query = "SELECT m FROM Monografico m WHERE m.nombre = :nombre"),
    @NamedQuery(name = "Monografico.findByFechainicio", query = "SELECT m FROM Monografico m WHERE m.fechainicio = :fechainicio"),
    @NamedQuery(name = "Monografico.findByFechafin", query = "SELECT m FROM Monografico m WHERE m.fechafin = :fechafin"),
    @NamedQuery(name = "Monografico.findByPrecio", query = "SELECT m FROM Monografico m WHERE m.precio = :precio"),
    @NamedQuery(name = "Monografico.findByDescripcion", query = "SELECT m FROM Monografico m WHERE m.descripcion = :descripcion"),
    @NamedQuery(name = "Monografico.findByPlazasdisponibles", query = "SELECT m FROM Monografico m WHERE m.plazasdisponibles = :plazasdisponibles"),
    @NamedQuery(name = "Monografico.findByPlazastotales", query = "SELECT m FROM Monografico m WHERE m.plazastotales = :plazastotales"),
    @NamedQuery(name = "Monografico.findByListaespera", query = "SELECT m FROM Monografico m WHERE m.listaespera = :listaespera"),
    @NamedQuery(name = "Monografico.findByLugarprevisto", query = "SELECT m FROM Monografico m WHERE m.lugarprevisto = :lugarprevisto")})
public class Monografico implements Serializable {
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
    @Column(name = "fechainicio")
    @Temporal(TemporalType.DATE)
    private Date fechainicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechafin")
    @Temporal(TemporalType.DATE)
    private Date fechafin;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "precio")
    private Double precio;
    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "plazasdisponibles")
    private int plazasdisponibles;
    @Basic(optional = false)
    @NotNull
    @Column(name = "plazastotales")
    private int plazastotales;
    @Basic(optional = false)
    @NotNull
    @Column(name = "listaespera")
    private int listaespera;
    @Size(max = 100)
    @Column(name = "lugarprevisto")
    private String lugarprevisto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "monografico")
    private Collection<MonograficosEnCursos> monograficosEnCursosCollection;

    public Monografico() {
    }

    public Monografico(Integer id) {
        this.id = id;
    }

    public Monografico(Integer id, String nombre, Date fechainicio, Date fechafin, int plazasdisponibles, int plazastotales, int listaespera) {
        this.id = id;
        this.nombre = nombre;
        this.fechainicio = fechainicio;
        this.fechafin = fechafin;
        this.plazasdisponibles = plazasdisponibles;
        this.plazastotales = plazastotales;
        this.listaespera = listaespera;
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

    public Date getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }

    public Date getFechafin() {
        return fechafin;
    }

    public void setFechafin(Date fechafin) {
        this.fechafin = fechafin;
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

    public int getPlazasdisponibles() {
        return plazasdisponibles;
    }

    public void setPlazasdisponibles(int plazasdisponibles) {
        this.plazasdisponibles = plazasdisponibles;
    }

    public int getPlazastotales() {
        return plazastotales;
    }

    public void setPlazastotales(int plazastotales) {
        this.plazastotales = plazastotales;
    }

    public int getListaespera() {
        return listaespera;
    }

    public void setListaespera(int listaespera) {
        this.listaespera = listaespera;
    }

    public String getLugarprevisto() {
        return lugarprevisto;
    }

    public void setLugarprevisto(String lugarprevisto) {
        this.lugarprevisto = lugarprevisto;
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
        if (!(object instanceof Monografico)) {
            return false;
        }
        Monografico other = (Monografico) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.scoutsfev.cudu.domain.Monografico[ id=" + id + " ]";
    }
    
}
