package org.scoutsfev.cudu.services;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scoutsfev.cudu.domain.liquidaciones.VistaResumenPorGrupo;
import org.scoutsfev.cudu.domain.liquidaciones.VistaResumen;
import org.scoutsfev.cudu.domain.liquidaciones.Resumen;

public class LiquidacionesServiceImpl implements LiquidacionesService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	@PersistenceContext
	protected EntityManager entityManager;
	
	@Override
	@SuppressWarnings("unchecked")
	public Collection<VistaResumen> obtener(int asociacion) {
		return this.entityManager
			.createNamedQuery("liquidacionesDeAsociacion")
			.setParameter("asociacion", asociacion)
			.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Resumen obtener(int ejercicio, String fecha, int asociacion) {
		VistaResumen parcial = (VistaResumen) this.entityManager
			.createQuery("select r from VistaResumen r where ejercicio = :ejercicio and fecha = :fecha and asociacion = :asociacion")
			.setParameter("ejercicio", ejercicio)
			.setParameter("fecha", java.sql.Date.valueOf(fecha))
			.setParameter("asociacion", asociacion)
			.getSingleResult();
		
		Collection<VistaResumenPorGrupo> detalle = this.entityManager
			.createQuery("select r from VistaResumenPorGrupo r where ejercicio = :ejercicio and fecha = :fecha and asociacion = :asociacion")
			.setParameter("ejercicio", ejercicio)
			.setParameter("fecha", java.sql.Date.valueOf(fecha))
			.setParameter("asociacion", asociacion)
			.getResultList();
		
		return new Resumen(parcial, detalle);
	}
	
	
}
