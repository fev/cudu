package org.scoutsfev.cudu.services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scoutsfev.cudu.domain.liquidaciones.Resumen;
import org.scoutsfev.cudu.domain.liquidaciones.VistaResumen;
import org.scoutsfev.cudu.domain.liquidaciones.VistaResumenPorGrupo;
import org.springframework.transaction.annotation.Transactional;

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

	@Override
	@Transactional
	public Resumen generar(int asociacion) {
		Calendar calendar = Calendar.getInstance();
		int ejercicio = calendar.get(GregorianCalendar.YEAR);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String fecha = dateFormat.format(new Date());
		
		this.entityManager.createNativeQuery(
			String.format("insert into liquidacion (ejercicio, fecha, idasociado, caracter, asociacion) " +
					"select %d, '%s', id, 'A', %d from asociado where asociacion = %d and activo = true and id not in (" +
					"select idasociado from liquidacion where ejercicio = %d and caracter = 'A');",
					ejercicio, fecha, asociacion, asociacion, ejercicio)).executeUpdate();
		
		this.entityManager.createNativeQuery(
				String.format("insert into liquidacion (ejercicio, fecha, idasociado, caracter, asociacion) " +
						"select %d, '%s', id, 'B', %d from asociado where asociacion = %d and activo = false " +
						"and id in (select idasociado from liquidacion where ejercicio = %d and caracter = 'A') " +
						"and id not in (select idasociado from liquidacion where ejercicio = %d and caracter = 'B');",
						ejercicio, fecha, asociacion, asociacion, ejercicio, ejercicio)).executeUpdate();

		return this.obtener(ejercicio, fecha, asociacion);
	}

	@Override
	@Transactional
	public boolean confirmar(int ejercicio, String fecha, int asociacion) {
		int nreg = this.entityManager.createNativeQuery("update liquidacion set borrador = false where ejercicio = :ejercicio and fecha = :fecha and asociacion = :asociacion")
			.setParameter("ejercicio", ejercicio)
			.setParameter("fecha", java.sql.Date.valueOf(fecha))
			.setParameter("asociacion", asociacion)
			.executeUpdate();
		return (nreg > 0);
	}

	@Override
	@Transactional
	public boolean descartar(int asociacion) {
		this.entityManager.createNativeQuery("delete from liquidacion where borrador = true and asociacion = :asociacion")
			.setParameter("asociacion", asociacion)
			.executeUpdate();
		
		return true;
	}
}





