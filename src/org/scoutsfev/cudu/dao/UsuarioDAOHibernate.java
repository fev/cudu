package org.scoutsfev.cudu.dao;

import org.hibernate.SessionFactory;
import org.scoutsfev.cudu.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("usuarioDAO")
public class UsuarioDAOHibernate extends GenericDAOHibernateObject<Usuario, String> implements UsuarioDAO {

	@Autowired
	public UsuarioDAOHibernate(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	protected Class<Usuario> getDomainClass() {
		return Usuario.class;
	}

	public Usuario loadWithAuthorities(String username) {

		Usuario usuario = find(username);
		/* SuperHack!
		 * Se marca el método como transaccional y se llama a size()
		 * para que la colección se rellene sin producir una excepción
		 * posteriormente, ya que el contexto se pierde al saltar a
		 * j_spring_security_check.
		 */
		if( usuario != null)
			usuario.getAuthorities().size();
		
		return usuario;
	}
	
}
