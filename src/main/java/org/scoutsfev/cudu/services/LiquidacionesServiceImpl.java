package org.scoutsfev.cudu.services;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scoutsfev.cudu.domain.ResumenLiquidacion;

public class LiquidacionesServiceImpl implements LiquidacionesService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	@PersistenceContext
	protected EntityManager entityManager;
	
	@Override
	@SuppressWarnings("unchecked")
	public Collection<ResumenLiquidacion> obtener(int asociacion) {
		return this.entityManager
			.createNamedQuery("liquidacionesDeAsociacion")
			.setParameter("asociacion", asociacion)
			.getResultList();
	}

	@Override
	public ResumenLiquidacion obtener(int ejercicio, String fecha, int asociacion) {
		return (ResumenLiquidacion) this.entityManager
			.createQuery("select r from ResumenLiquidacion r where ejercicio = :ejercicio and fecha = :fecha and asociacion = :asociacion")
			.setParameter("ejercicio", ejercicio)
			.setParameter("fecha", java.sql.Date.valueOf(fecha))
			.setParameter("asociacion", asociacion)
			.getSingleResult();
	}
}
