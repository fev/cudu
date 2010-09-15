package org.scoutsfev.cudu.services;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

import org.scoutsfev.cudu.dao.AsociadoDAO;
import org.scoutsfev.cudu.domain.Asociado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("asociadoService")
public class AsociadoServiceImpl implements AsociadoService {
	
	@Autowired
	private AsociadoDAO asociadoDAO;
	
	@Autowired
	protected AuditoriaService auditoria;

	public Collection<Asociado> findWhere(String idGrupo, String columnas,
			String campoOrden, String sentidoOrden, int inicio,
			int resultadosPorPagina, String tipos, String ramas) {
		
		return asociadoDAO.findWhere(idGrupo, columnas, campoOrden, sentidoOrden, 
				inicio, resultadosPorPagina, tipos, ramas);
	}

	public long count() {
		// TODO Substituir por tabla donde se guarde el recuento para acelerar el COUNT de PostgreSQL.
		return asociadoDAO.count();
	}
	
	public long count(String idGrupo, String tipos, String ramas) {
		if (idGrupo == null)
			return count();
		
		return asociadoDAO.count(idGrupo, tipos, ramas);
	}
	
	public Asociado merge(Asociado entity) {
		Asociado persistedEntity = asociadoDAO.merge(entity);
		auditoria.registrar(AuditoriaService.Operacion.Almacenar, AuditoriaService.Entidad.Asociado, persistedEntity.getId().toString());
		return persistedEntity;
	}

	public Asociado find(Integer id) {
		Asociado asociado = asociadoDAO.find(id);
		auditoria.registrar(AuditoriaService.Operacion.Acceder, AuditoriaService.Entidad.Asociado, ""+id);
		return asociado;
	}

	public Collection<Asociado> findWhere(String idGrupo, String columnas,
			String campoOrden, String sentidoOrden, int inicio,
			int resultadosPorPagina, String tipos, String ramas,
			boolean eliminados) {
		// TODO Auto-generated method stub
		return null;
	}

	public long count(String idGrupo, String tipos, String ramas,
			boolean eliminados) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean delete(int id) {
		
		Asociado asociado = asociadoDAO.find(id);
		int n=0;
		if( asociado != null ){
			n=1;
		}
		asociado.setFechabaja(new Timestamp(new Date().getTime()));
		auditoria.registrar(AuditoriaService.Operacion.Descartar, AuditoriaService.Entidad.Asociado, asociado.getId()+":"+n);
		asociadoDAO.save(asociado);
		
		return n==1;
	}
}
