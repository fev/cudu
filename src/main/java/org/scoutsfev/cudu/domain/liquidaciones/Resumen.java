package org.scoutsfev.cudu.domain.liquidaciones;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;

public class Resumen implements Serializable {

	private static final long serialVersionUID = 4058837751693148157L;
	
	protected Integer ejercicio;
	protected Date fecha;
	protected Long altas;
	protected Long bajas;
	protected Integer asociacion;
	protected Dictionary<String, DetalleGrupo> grupos = new Hashtable<String, DetalleGrupo>();
	// protected Collection<DetalleGrupo> grupos = new HashSet<DetalleGrupo>();
	
	public Resumen() { }
	
	public Resumen(VistaResumen resumen, Collection<VistaResumenPorGrupo> detalleGrupos) {
		this.ejercicio = resumen.getEjercicio();
		this.fecha = resumen.getFecha();
		this.altas = resumen.getAltas();
		this.bajas = resumen.getBajas();
		this.asociacion = resumen.getAsociacion();
		
		for (VistaResumenPorGrupo detalle : detalleGrupos) {
			DetalleGrupo grupo = grupos.get(detalle.getGrupoId());
			
			if (grupo == null) {
				grupo = new DetalleGrupo(detalle.getGrupoId(), detalle.getGrupoNombre(), 0, 0);
				grupos.put(detalle.getGrupoId(), grupo);
			}
			
			if (detalle.getCaracter() == 'A')
				grupo.setAltas(detalle.getCantidad());
			else
				grupo.setBajas(detalle.getCantidad());
		}
	}

	public Integer getEjercicio() {
		return ejercicio;
	}

	public void setEjercicio(Integer ejercicio) {
		this.ejercicio = ejercicio;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Long getAltas() {
		return altas;
	}

	public void setAltas(Long altas) {
		this.altas = altas;
	}

	public Long getBajas() {
		return bajas;
	}

	public void setBajas(Long bajas) {
		this.bajas = bajas;
	}

	public Integer getAsociacion() {
		return asociacion;
	}

	public void setAsociacion(Integer asociacion) {
		this.asociacion = asociacion;
	}

//	public Collection<DetalleGrupo> getGrupos() {
//		return grupos;
//	}
//
//	public void setGrupos(Collection<DetalleGrupo> grupos) {
//		this.grupos = grupos;
//	}

	public Dictionary<String, DetalleGrupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(Dictionary<String, DetalleGrupo> grupos) {
		this.grupos = grupos;
	}
}
