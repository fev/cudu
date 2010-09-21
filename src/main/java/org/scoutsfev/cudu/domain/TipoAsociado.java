package org.scoutsfev.cudu.domain;

public enum TipoAsociado {
	Joven('J'),
	Kraal('K'),
	Comité('C');
	
	@SuppressWarnings("unused")
	private char tipo;

	TipoAsociado(char tipo) {
		this.tipo = tipo;
	}
}
