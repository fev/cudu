package org.scoutsfev.cudu.domain;

import java.util.Collection;

public class Parcial {
	private Collection<String> votantes;
	private int votos;
	
	public Parcial(Collection<String> votantes, int votos) {
		this.votantes = votantes;
		this.votos = votos;
	}

	public void setVotantes(Collection<String> votantes) {
		this.votantes = votantes;
	}

	public Collection<String> getVotantes() {
		return votantes;
	}

	public void setVotos(int votos) {
		this.votos = votos;
	}

	public int getVotos() {
		return votos;
	}
}
