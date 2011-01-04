package org.scoutsfev.cudu.services;

import java.util.Collection;

import org.scoutsfev.cudu.domain.ResumenLiquidacion;

public interface LiquidacionesService {
	Collection<ResumenLiquidacion> obtener(int asociacion);
	ResumenLiquidacion obtener(int ejercicio, String fecha, int asociacion);
}
