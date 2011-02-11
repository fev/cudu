package org.scoutsfev.cudu.services;

import java.util.Collection;

import org.scoutsfev.cudu.domain.liquidaciones.VistaResumen;
import org.scoutsfev.cudu.domain.liquidaciones.Resumen;

public interface LiquidacionesService {
	Collection<VistaResumen> obtener(int asociacion);
	Resumen obtener(int ejercicio, String fecha, int asociacion);
	Resumen generar(int asociacion);

	boolean confirmar(int ejercicio, String fecha, int asociacion);
	boolean descartar(int asociacion);
}
