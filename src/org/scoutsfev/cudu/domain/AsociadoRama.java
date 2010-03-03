package org.scoutsfev.cudu.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "asociado_rama")
public class AsociadoRama implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String rama;

	@ManyToOne
	@JoinColumn(name = "idasociado")
	private Asociado asociado;

	public String getRama() {
		return rama;
	}
	
	public void setRama(String rama) {
		this.rama = rama;
	}

	public Asociado getAsociado() {
		return this.asociado;
	}

	public void setAsociado(Asociado asociado) {
		this.asociado = asociado;
	}
}