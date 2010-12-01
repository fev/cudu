package org.scoutsfev.cudu.services;

import java.util.Collection;

import org.scoutsfev.cudu.domain.Parcial;
import org.scoutsfev.cudu.domain.Sugerencia;

public interface SugerenciasService {
	Collection<Sugerencia> obtenerLista();
	long crear(String sugerencia, String usuario); 	// TODO long crear(Sugerencia sugerencia, String usuario);
	Parcial votar(String pk, String usuario);
}
