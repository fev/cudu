package org.scoutsfev.cudu.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.scoutsfev.cudu.domain.Grupo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("grupoDAO")
public class GrupoDAOHibernate extends GenericDAOHibernateObject<Grupo, String> implements GrupoDAO {

	@Autowired
	public GrupoDAOHibernate(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	protected Class<Grupo> getDomainClass() {
		return Grupo.class;
	}

	@SuppressWarnings("unchecked")
	public Grupo findByUser(String username) {
		template.setFetchSize(1);
		List<Grupo> grupos = template.find("SELECT u.grupo FROM Usuario u WHERE u.username = :username", username);
		if( grupos.size() != 0 )
			return grupos.get(0);

		return null;
	}
	
}
