package org.scoutsfev.cudu.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.Version;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Formula;

import org.hibernate.validator.constraints.Email;

@Entity
public class Grupo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;

	@NotNull
	@Size(min = 3, max = 50)
	private String nombre;
	
	@NotNull
	@Size(min=3, max=300)
	private String direccion;
	
	@NotNull
	@Min(1)
	private Integer codigopostal;

	private Integer idMunicipio;
	
	@NotNull
	@Size(min = 3, max = 100)
	private String municipio;
	
	private Integer idProvincia;
	
	@NotNull
	@Size(min = 3, max = 100)
	private String provincia;
	
	@Past
        @Temporal(javax.persistence.TemporalType.DATE)
	private Date aniversario;
        
        @Formula("direccion|| ', ' || municipio|| ' - ' || codigopostal || ' ('  || provincia || ')'")
	private String direccionCompleta;

	@NotNull
	@Size(min = 3, max = 15)
	private String telefono1;
	
	@Size(max = 15)
	private String telefono2;
	
	@Email
	@NotNull
	@Size(min = 6, max = 100)
	private String email;
	
	@Size(max = 300)
	private String web;
	
	private Integer asociacion;
	
        private String imagen;
        
	@Size(max = 100)
	private String entidadpatrocinadora;
	
	@Column(name = "jpa_version")
        @Version
        private int version;
        
        private String colAdicional0;
    private String colAdicional1;
    private String colAdicional2;
    private String colAdicional3;
    private String colAdicional4;
    private String colAdicional5;
    private String colAdicional6;
    private String colAdicional7;
    private String colAdicional8;
    private String colAdicional9;
    
    @Column(name = "orden")
    private String ordenColumnas;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getCodigopostal() {
		return codigopostal;
	}

	public void setCodigopostal(Integer codigopostal) {
		this.codigopostal = codigopostal;
	}

	public Integer getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdmunicipio(Integer idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public Integer getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(Integer idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public Date getAniversario() {
		return aniversario;
	}

	public void setAniversario(Date aniversario) {
		this.aniversario = aniversario;
	}

	public String getTelefono1() {
		return telefono1;
	}

	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}

	public String getTelefono2() {
		return telefono2;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public Integer getAsociacion() {
		return asociacion;
	}

	public void setAsociacion(Integer asociacion) {
		this.asociacion = asociacion;
	}

	public String getEntidadpatrocinadora() {
		return entidadpatrocinadora;
	}

	public void setEntidadpatrocinadora(String entidadpatrocinadora) {
		this.entidadpatrocinadora = entidadpatrocinadora;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getVersion() {
		return version;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getDireccion() {
		return direccion;
	}

    /**
     * @return the imagen
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * @param imagen the imagen to set
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    /**
     * @return the colAdicional0
     */
    public String getColAdicional0() {
        return colAdicional0;
    }

    /**
     * @param colAdicional0 the colAdicional0 to set
     */
    public void setColAdicional0(String colAdicional0) {
        this.colAdicional0 = colAdicional0;
    }

    /**
     * @return the colAdicional1
     */
    public String getColAdicional1() {
        return colAdicional1;
    }

    /**
     * @param colAdicional1 the colAdicional1 to set
     */
    public void setColAdicional1(String colAdicional1) {
        this.colAdicional1 = colAdicional1;
    }

    /**
     * @return the colAdicional2
     */
    public String getColAdicional2() {
        return colAdicional2;
    }

    /**
     * @param colAdicional2 the colAdicional2 to set
     */
    public void setColAdicional2(String colAdicional2) {
        this.colAdicional2 = colAdicional2;
    }

    /**
     * @return the colAdicional3
     */
    public String getColAdicional3() {
        return colAdicional3;
    }

    /**
     * @param colAdicional3 the colAdicional3 to set
     */
    public void setColAdicional3(String colAdicional3) {
        this.colAdicional3 = colAdicional3;
    }

    /**
     * @return the colAdicional4
     */
    public String getColAdicional4() {
        return colAdicional4;
    }

    /**
     * @param colAdicional4 the colAdicional4 to set
     */
    public void setColAdicional4(String colAdicional4) {
        this.colAdicional4 = colAdicional4;
    }

    /**
     * @return the colAdicional5
     */
    public String getColAdicional5() {
        return colAdicional5;
    }

    /**
     * @param colAdicional5 the colAdicional5 to set
     */
    public void setColAdicional5(String colAdicional5) {
        this.colAdicional5 = colAdicional5;
    }

    /**
     * @return the colAdicional6
     */
    public String getColAdicional6() {
        return colAdicional6;
    }

    /**
     * @param colAdicional6 the colAdicional6 to set
     */
    public void setColAdicional6(String colAdicional6) {
        this.colAdicional6 = colAdicional6;
    }

    /**
     * @return the colAdicional7
     */
    public String getColAdicional7() {
        return colAdicional7;
    }

    /**
     * @param colAdicional7 the colAdicional7 to set
     */
    public void setColAdicional7(String colAdicional7) {
        this.colAdicional7 = colAdicional7;
    }

    /**
     * @return the colAdicional8
     */
    public String getColAdicional8() {
        return colAdicional8;
    }

    /**
     * @param colAdicional8 the colAdicional8 to set
     */
    public void setColAdicional8(String colAdicional8) {
        this.colAdicional8 = colAdicional8;
    }

    /**
     * @return the colAdicional9
     */
    public String getColAdicional9() {
        return colAdicional9;
    }

    /**
     * @param colAdicional9 the colAdicional9 to set
     */
    public void setColAdicional9(String colAdicional9) {
        this.colAdicional9 = colAdicional9;
    }

    /**
     * @return the ordenColumnas
     */
    public String getOrdenColumnas() {
        return ordenColumnas;
    }

    /**
     * @param ordenColumnas the ordenColumnas to set
     */
    public void setOrdenColumnas(String ordenColumnas) {
        this.ordenColumnas = ordenColumnas;
    }
}

