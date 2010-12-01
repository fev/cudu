package org.scoutsfev.cudu.domain;

import java.io.Serializable;

public class Sugerencia implements Serializable  {
	
	private static final long serialVersionUID = -4890971528838542983L;
	private String pk;
	private String texto;
	private String fecha;
	private String votantes;
	private String votos;
	
	public Sugerencia(String pk, String texto, String fecha, String votantes, String votos) {
		this.setPk(pk);
		this.setTexto(texto);
		this.setVotantes(votantes);
		this.setVotos(votos);
		
		this.setFecha(fecha.substring(6,8) + '/' + fecha.substring(4,6)+ '/' + fecha.substring(0, 4)
				+ ' ' + fecha.substring(8,10) + ':' + fecha.substring(10,12) + ':' + fecha.substring(12,14));
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getTexto() {
		return texto;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getPk() {
		return pk;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getFecha() {
		return fecha;
	}
	
	public void setVotantes(String votantes) {
		this.votantes = votantes;
	}

	public String getVotantes() {
		return votantes;
	}

	public void setVotos(String votos) {
		this.votos = votos;
	}

	public String getVotos() {
		return votos;
	}
}
